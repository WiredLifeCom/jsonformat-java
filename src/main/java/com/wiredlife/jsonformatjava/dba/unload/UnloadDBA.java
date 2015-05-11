package com.wiredlife.jsonformatjava.dba.unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;
import com.wiredlife.jsonformatjava.model.unload.Zone;
import com.wiredlife.jsonformatjava.utility.OSValidator;
import com.wiredlife.jsonformatjava.utility.OSValidator.OS;

public class UnloadDBA {

	private Connection connection;

	private UnloadDBA() {
		try {
			if (OSValidator.getOS().equals(OS.ANDROID)) {
				DriverManager.registerDriver(new org.sqldroid.SQLDroidDriver());
			} else {
				DriverManager.registerDriver(new org.sqlite.JDBC());
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public UnloadDBA(String database) {
		this();
		try {
			this.connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", database));

			Statement statement = this.connection.createStatement();
			statement.executeUpdate("PRAGMA foreign_keys=ON");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (UserID integer, Username string unique, primary key (UserID))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unloads (UnloadID integer, UserID integer, Date date, primary key (UnloadID) foreign key (UserID) references users(UserID))");
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS unloadszones (UnloadID integer, Latitude double, Longitude double, Radius integer, Material string, Arrival date, Departure date, foreign key (UnloadID) references unloads(UnloadID) ON UPDATE CASCADE ON DELETE CASCADE)");
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS unloadsmaterials (UnloadID integer, Material string, foreign key (UnloadID) references unloads(UnloadID) ON UPDATE CASCADE ON DELETE CASCADE)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUnload(Unload unload) {
		try {
			PreparedStatement stmtSelectUser = this.connection.prepareStatement("SELECT UserID, Username FROM users WHERE Username=? LIMIT 1");
			stmtSelectUser.setString(1, unload.getUser().getUsername());
			ResultSet rsSelectUser = stmtSelectUser.executeQuery();

			if (!rsSelectUser.next()) {
				PreparedStatement stmtInsertUser = this.connection.prepareStatement("INSERT INTO users (Username) VALUES (?)");
				stmtInsertUser.setString(1, unload.getUser().getUsername());
				stmtInsertUser.executeUpdate();
			}

			int latestUserID = getLatestUserID();

			PreparedStatement stmtInsertUnload = this.connection.prepareStatement("INSERT INTO unloads (UserID, Date) VALUES (?, ?)");
			stmtInsertUnload.setInt(1, latestUserID);
			stmtInsertUnload.setString(2, unload.getUnload().toString());
			stmtInsertUnload.executeUpdate();

			int latestUnloadID = getLatestUnloadID();

			PreparedStatement stmtInsertUnloadsZones = this.connection
					.prepareStatement("INSERT INTO unloadszones (UnloadID, Latitude, Longitude, Radius, Material, Arrival, Departure) VALUES (?, ?, ?, ?, ?, ?, ?)");

			for (Zone zone : unload.getZones()) {
				stmtInsertUnloadsZones.setInt(1, latestUnloadID);
				stmtInsertUnloadsZones.setDouble(2, zone.getLatitude());
				stmtInsertUnloadsZones.setDouble(3, zone.getLongitude());
				stmtInsertUnloadsZones.setInt(4, zone.getRadius());
				stmtInsertUnloadsZones.setString(5, zone.getMaterial());
				stmtInsertUnloadsZones.setString(6, zone.getArrival().toString());
				stmtInsertUnloadsZones.setString(7, zone.getDeparture().toString());
				stmtInsertUnloadsZones.executeUpdate();
			}

			PreparedStatement stmtInsertUnloadsMaterials = this.connection.prepareStatement("INSERT INTO unloadsmaterials (UnloadID, Material) VALUES (?, ?)");

			for (String material : unload.getMaterials()) {
				stmtInsertUnloadsMaterials.setInt(1, latestUnloadID);
				stmtInsertUnloadsMaterials.setString(2, material);
				stmtInsertUnloadsMaterials.executeUpdate();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Unload> getUnloads(String username) {
		try {
			List<Unload> unloads = new ArrayList<Unload>();

			PreparedStatement stmtGetUnloadIds = this.connection.prepareStatement("SELECT UnloadID, Date FROM unloads INNER JOIN users ON unloads.UserID = users.UserID WHERE Username=?");
			stmtGetUnloadIds.setString(1, username);
			ResultSet rsGetUnloadIds = stmtGetUnloadIds.executeQuery();

			while (rsGetUnloadIds.next()) {
				Unload unload = new Unload();

				User user = new User();
				user.setUsername(username);

				PreparedStatement stmtGetUnloadsZones = this.connection.prepareStatement("SELECT Latitude, Longitude, Radius, Material, Arrival, Departure FROM unloadszones WHERE UnloadID=?");
				stmtGetUnloadsZones.setInt(1, rsGetUnloadIds.getInt("UnloadID"));
				ResultSet rsGetUnloadsZones = stmtGetUnloadsZones.executeQuery();

				while (rsGetUnloadsZones.next()) {
					Zone zone = new Zone();
					zone.setLatitude(rsGetUnloadsZones.getDouble("Latitude"));
					zone.setLongitude(rsGetUnloadsZones.getDouble("Longitude"));
					zone.setRadius(rsGetUnloadsZones.getInt("Radius"));
					zone.setMaterial(rsGetUnloadsZones.getString("Material"));
					zone.setArrival(DateTime.parse(rsGetUnloadsZones.getString("Arrival")));
					zone.setDeparture(DateTime.parse(rsGetUnloadsZones.getString("Departure")));

					unload.addZone(zone);
				}

				PreparedStatement stmtGetUnloadsMaterials = this.connection.prepareStatement("SELECT Material FROM unloadsmaterials WHERE UnloadID=?");
				stmtGetUnloadsMaterials.setInt(1, rsGetUnloadIds.getInt("UnloadID"));
				ResultSet rsGetUnloadsMaterials = stmtGetUnloadsMaterials.executeQuery();

				while (rsGetUnloadsMaterials.next()) {
					unload.addMaterial(rsGetUnloadsMaterials.getString("Material"));
				}

				unload.setUser(user);
				unload.setUnload(DateTime.parse(rsGetUnloadIds.getString("Date")));

				unloads.add(unload);
			}
			return unloads;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUnloads(String username) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("DELETE FROM unloads WHERE UserID=(SELECT UserID FROM users WHERE Username=?)");
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void deleteUnload(Unload unload) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("DELETE FROM unloads WHERE UserID=(SELECT UserID FROM users WHERE Username=?) AND Date=?");
			statement.setString(1, unload.getUser().getUsername());
			statement.setString(2, unload.getUnload().toString());
			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private int getLatestUserID() {
		try {
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT UserID FROM users ORDER BY UserID DESC LIMIT 1");
			while (rs.next()) {
				return rs.getInt("UserID");
			}
			return 1;
		} catch (SQLException e) {
			// Swallow exception
		}
		return 1;
	}

	private int getLatestUnloadID() {
		try {
			Statement statement = this.connection.createStatement();
			ResultSet rs = statement.executeQuery("SELECT UnloadID FROM unloads ORDER BY UnloadID DESC LIMIT 1");
			while (rs.next()) {
				return rs.getInt("UnloadID");
			}
			return 1;
		} catch (SQLException e) {
			// Swallow exception
		}
		return 1;
	}

}
