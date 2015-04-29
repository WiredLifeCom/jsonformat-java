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

public class UnloadDBA {

	private Connection connection;

	private UnloadDBA() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public UnloadDBA(String database) {
		this();
		try {
			this.connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", database));

			Statement statement = this.connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (UserID integer, Username string unique, primary key (UserID))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS usersunloads (UserID integer, UnloadID integer, UnloadDate string, primary key (UserID, UnloadID) "
					+ "foreign key (UserID) references users(UserID), foreign key (UnloadID) references unloads(UnloadID))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unloadszones (UnloadID integer, Arrival string, Departure string, Latitude double, Longitude double, "
					+ "foreign key (UnloadID) references unloads(UnloadID))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unloads (UnloadID integer, Resource string)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getUsers() {
		try {
			PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM users");
			ResultSet rs = statement.executeQuery();

			List<User> users = new ArrayList<User>();
			while (rs.next()) {
				User user = new User(rs.getString("Username"), getZones(rs.getString("Username")), null);
				users.add(user);
			}
			return users;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public List<Zone> getZones(String username) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("SELECT * FROM unloadszones INNER JOIN usersunloads ON unloadszones.UnloadID = usersunloads.UnloadID "
					+ "INNER JOIN users ON usersunloads.UserID = users.UserID");

			ResultSet rs = statement.executeQuery();

			List<Zone> zones = new ArrayList<Zone>();
			while (rs.next()) {
				Zone zone = new Zone(DateTime.parse(rs.getString("Arrival")), DateTime.parse(rs.getString("Departure")), rs.getDouble("Latitude"), rs.getDouble("Longitude"));
				zones.add(zone);
			}
			return zones;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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

			int userId = getLatestUserID();
			System.out.println("UserID: " + userId);

			int unloadId = getLatestUnloadID();
			if (unloadId == -1) {
				unloadId = 1;
			} else {
				unloadId++;
			}
			System.out.println("UnloadID: " + unloadId);

			PreparedStatement stmtInsertUnload = this.connection.prepareStatement("INSERT INTO unloads (UnloadID, Resource) VALUES (?, ?)");
			for (String material : unload.getUser().getMaterials()) {
				stmtInsertUnload.setInt(1, unloadId);
				stmtInsertUnload.setString(2, material);
				stmtInsertUnload.executeUpdate();
			}

			PreparedStatement stmtInsertUnloadsZones = this.connection.prepareStatement("INSERT INTO unloadszones (UnloadID, Arrival, Departure, Latitude, Longitude) VALUES (?, ?, ?, ?, ?)");
			for (Zone zone : unload.getUser().getZones()) {
				stmtInsertUnloadsZones.setInt(1, unloadId);
				stmtInsertUnloadsZones.setString(2, zone.getArrival().toString());
				stmtInsertUnloadsZones.setString(3, zone.getDeparture().toString());
				stmtInsertUnloadsZones.setDouble(4, zone.getLatitude());
				stmtInsertUnloadsZones.setDouble(5, zone.getLongitude());
				stmtInsertUnloadsZones.executeUpdate();
			}

			PreparedStatement stmtInsertUsersUnloads = this.connection.prepareStatement("INSERT INTO usersunloads (UserID, UnloadID, UnloadDate) VALUES (?, ?, ?)");
			stmtInsertUsersUnloads.setInt(1, userId);
			stmtInsertUsersUnloads.setInt(2, unloadId);
			stmtInsertUsersUnloads.setString(3, unload.getUnload().toString());
			stmtInsertUsersUnloads.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<Unload> getUnloads(String username) {
		try {
			PreparedStatement stmtGetUnloads = this.connection
					.prepareStatement("SELECT users.UserID, usersunloads.UnloadID, unloads.Resource, unloadszones.Arrival, unloadszones.Departure, unloadszones.Latitude, unloadszones.Longitude "
							+ "FROM users INNER JOIN usersunloads ON users.UserID = usersunloads.UserID "
							+ "INNER JOIN unloads ON usersunloads.UnloadID = unloads.UnloadID INNER JOIN unloadszones ON usersunloads.UnloadID = unloadszones.UnloadID WHERE Username=?");
			stmtGetUnloads.setString(1, username);
			ResultSet rsGetUnloads = stmtGetUnloads.executeQuery();

			List<String> materials = new ArrayList<String>();
			List<Zone> zones = new ArrayList<Zone>();

			int currentUnloadID = -1;
			while (rsGetUnloads.next()) {
				if (currentUnloadID != rsGetUnloads.getInt("UnloadID")) {
					currentUnloadID = rsGetUnloads.getInt("UnloadID");

					User user = new User(username, zones, materials);
					System.out.println(user);

					// System.out.println(materials);
					materials = new ArrayList<String>();

					// System.out.println(zones);
					zones = new ArrayList<Zone>();

				}
				// System.out.println(rsGetUnloads.getString("UnloadID") + " " +
				// rsGetUnloads.getString("Resource") + " " +
				// rsGetUnloads.getString("Arrival"));
				materials.add(rsGetUnloads.getString("Resource"));
				zones.add(new Zone(DateTime.parse(rsGetUnloads.getString("Arrival")), DateTime.parse(rsGetUnloads.getString("Departure")), rsGetUnloads.getDouble("Latitude"), rsGetUnloads
						.getDouble("Longitude")));
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	public void deleteUnloads(String username) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("DELETE FROM unloads WHERE username=?");
			statement.setString(1, username);
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private int getLatestUserID() {
		try {
			Statement statement = this.connection.createStatement();
			return statement.executeQuery("SELECT UserID FROM users ORDER BY UserID DESC LIMIT 1").getInt("UserID");
		} catch (SQLException e) {
			// Swallow exception
		}
		return -1;
	}

	private int getLatestUnloadID() {
		try {
			Statement statement = this.connection.createStatement();
			return statement.executeQuery("SELECT UnloadID FROM unloads ORDER BY UnloadID DESC LIMIT 1").getInt("UnloadID");
		} catch (SQLException e) {
			// Swallow exception
		}
		return -1;
	}

}
