package com.wiredlife.jsonformatjava.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.wiredlife.jsonformatjava.model.status.OnlineStatus;
import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;
import com.wiredlife.jsonformatjava.model.unload.Zone;
import com.wiredlife.jsonformatjava.utility.OSValidator;
import com.wiredlife.jsonformatjava.utility.OSValidator.OS;

public class DAO {

	private DataSource dataSource;

	private DAO() throws PropertyVetoException {
		this.dataSource = DataSource.getInstance();

		if (OSValidator.getOS().equals(OS.ANDROID)) {
			this.dataSource.setDriverClass("org.sqldroid.SQLDroidDriver");
			// DriverManager.registerDriver(new
			// org.sqldroid.SQLDroidDriver());
		} else {
			this.dataSource.setDriverClass("org.sqlite.JDBC");
			// DriverManager.registerDriver(new org.sqlite.JDBC());
		}
	}

	public DAO(String database) throws PropertyVetoException, SQLException {
		this();

		this.dataSource.setJdbcUrl(String.format("jdbc:sqlite:%s", database));

		try (Connection connection = this.dataSource.getConnection()) {
			Statement statement = connection.createStatement();
			statement.executeUpdate("PRAGMA foreign_keys=ON");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (UserID integer, Username string unique, primary key (UserID))");
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unloads (UnloadID integer, UserID integer, Date date, primary key (UnloadID) foreign key (UserID) references users(UserID))");
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS unloadszones (UnloadID integer, Latitude double, Longitude double, Radius integer, Material string, Arrival date, Departure date, foreign key (UnloadID) references unloads(UnloadID) ON UPDATE CASCADE ON DELETE CASCADE)");
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS unloadsmaterials (UnloadID integer, Material string, foreign key (UnloadID) references unloads(UnloadID) ON UPDATE CASCADE ON DELETE CASCADE)");
			statement
					.executeUpdate("CREATE TABLE IF NOT EXISTS onlinestatuses (UserID integer, IsHome integer, IpAddress string, foreign key (UserID) references users(UserID) ON UPDATE CASCADE ON DELETE CASCADE)");
		}
	}

	public void addOnlineStatus(OnlineStatus onlineStatus) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement stmtSelectUser = connection.prepareStatement("SELECT UserID, Username FROM users WHERE Username=? LIMIT 1")) {
				stmtSelectUser.setString(1, onlineStatus.getUsername());
				ResultSet rsSelectUser = stmtSelectUser.executeQuery();

				if (!rsSelectUser.next()) {
					PreparedStatement stmtInsertUser = connection.prepareStatement("INSERT INTO users (Username) VALUES (?)");
					stmtInsertUser.setString(1, onlineStatus.getUsername());
					stmtInsertUser.executeUpdate();
				}
			}

			int userID = getUserID(onlineStatus.getUsername());

			try (PreparedStatement stmtSelectOnlineStatus = connection.prepareStatement("SELECT UserID FROM onlinestatuses WHERE UserID=?")) {
				stmtSelectOnlineStatus.setInt(1, userID);
				ResultSet rsSelectOnlineStatus = stmtSelectOnlineStatus.executeQuery();

				if (!rsSelectOnlineStatus.next()) {
					try (PreparedStatement stmtInsertOnlineStatus = connection.prepareStatement("INSERT INTO onlinestatuses (UserID, IsHome, IpAddress) VALUES (?, ?, ?)")) {
						stmtInsertOnlineStatus.setInt(1, userID);
						stmtInsertOnlineStatus.setBoolean(2, onlineStatus.isHome());
						stmtInsertOnlineStatus.setString(3, onlineStatus.getIpAddress());
						stmtInsertOnlineStatus.executeUpdate();
					}
				} else {
					try (PreparedStatement stmtUpdateOnlineStatus = connection.prepareStatement("UPDATE onlinestatuses SET IsHome=?, IpAddress=? WHERE UserID=?")) {
						stmtUpdateOnlineStatus.setBoolean(1, onlineStatus.isHome());
						stmtUpdateOnlineStatus.setString(2, onlineStatus.getIpAddress());
						stmtUpdateOnlineStatus.setInt(1, userID);
					}
				}
			}
		}
	}

	public OnlineStatus getOnlineStatus(String username) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement stmtSelectOnlineStatus = connection
					.prepareStatement("SELECT Username, IsHome, IpAddress FROM onlinestatuses INNER JOIN users ON onlinestatuses.UserID = users.UserID WHERE Username=?")) {
				stmtSelectOnlineStatus.setString(1, username);
				ResultSet rsSelectOnlineStatus = stmtSelectOnlineStatus.executeQuery();

				if (rsSelectOnlineStatus.next()) {
					OnlineStatus onlineStatus = new OnlineStatus();
					onlineStatus.setUsername(rsSelectOnlineStatus.getString("Username"));
					onlineStatus.setHome(rsSelectOnlineStatus.getBoolean("IsHome"));
					onlineStatus.setIpAddress(rsSelectOnlineStatus.getString("IpAddress"));

					return onlineStatus;
				}
				return null;
			}
		}
	}

	public void addUnload(Unload unload) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement stmtSelectUser = connection.prepareStatement("SELECT UserID, Username FROM users WHERE Username=? LIMIT 1")) {
				stmtSelectUser.setString(1, unload.getUser().getUsername());
				ResultSet rsSelectUser = stmtSelectUser.executeQuery();

				if (!rsSelectUser.next()) {
					PreparedStatement stmtInsertUser = connection.prepareStatement("INSERT INTO users (Username) VALUES (?)");
					stmtInsertUser.setString(1, unload.getUser().getUsername());
					stmtInsertUser.executeUpdate();
				}
			}

			int userID = getUserID(unload.getUser().getUsername());

			try (PreparedStatement stmtInsertUnload = connection.prepareStatement("INSERT INTO unloads (UserID, Date) VALUES (?, ?)")) {
				stmtInsertUnload.setInt(1, userID);
				stmtInsertUnload.setString(2, unload.getUnload().toString());
				stmtInsertUnload.executeUpdate();
			}

			int unloadID = getUnloadID(userID, unload.getUnload());

			try (PreparedStatement stmtInsertUnloadsZones = connection
					.prepareStatement("INSERT INTO unloadszones (UnloadID, Latitude, Longitude, Radius, Material, Arrival, Departure) VALUES (?, ?, ?, ?, ?, ?, ?)")) {
				for (Zone zone : unload.getZones()) {
					stmtInsertUnloadsZones.setInt(1, unloadID);
					stmtInsertUnloadsZones.setDouble(2, zone.getLatitude());
					stmtInsertUnloadsZones.setDouble(3, zone.getLongitude());
					stmtInsertUnloadsZones.setInt(4, zone.getRadius());
					stmtInsertUnloadsZones.setString(5, zone.getMaterial());
					stmtInsertUnloadsZones.setString(6, zone.getArrival().toString());
					stmtInsertUnloadsZones.setString(7, zone.getDeparture().toString());
					stmtInsertUnloadsZones.executeUpdate();
				}
			}

			try (PreparedStatement stmtInsertUnloadsMaterials = connection.prepareStatement("INSERT INTO unloadsmaterials (UnloadID, Material) VALUES (?, ?)")) {
				for (String material : unload.getMaterials()) {
					stmtInsertUnloadsMaterials.setInt(1, unloadID);
					stmtInsertUnloadsMaterials.setString(2, material);
					stmtInsertUnloadsMaterials.executeUpdate();
				}
			}
		}
	}

	public List<Unload> getUnloads(String username) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement stmtGetUnloadIds = connection.prepareStatement("SELECT UnloadID, Date FROM unloads INNER JOIN users ON unloads.UserID = users.UserID WHERE Username=?")) {
				stmtGetUnloadIds.setString(1, username);
				ResultSet rsGetUnloadIds = stmtGetUnloadIds.executeQuery();

				List<Unload> unloads = new ArrayList<Unload>();

				while (rsGetUnloadIds.next()) {
					Unload unload = new Unload();

					User user = new User();
					user.setUsername(username);

					try (PreparedStatement stmtGetUnloadsZones = connection.prepareStatement("SELECT Latitude, Longitude, Radius, Material, Arrival, Departure FROM unloadszones WHERE UnloadID=?")) {
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
					}

					try (PreparedStatement stmtGetUnloadsMaterials = connection.prepareStatement("SELECT Material FROM unloadsmaterials WHERE UnloadID=?")) {
						stmtGetUnloadsMaterials.setInt(1, rsGetUnloadIds.getInt("UnloadID"));
						ResultSet rsGetUnloadsMaterials = stmtGetUnloadsMaterials.executeQuery();

						while (rsGetUnloadsMaterials.next()) {
							unload.addMaterial(rsGetUnloadsMaterials.getString("Material"));
						}
					}

					unload.setUser(user);
					unload.setUnload(DateTime.parse(rsGetUnloadIds.getString("Date")));

					unloads.add(unload);
				}
				return unloads;
			}
		}
	}

	public void deleteUnloads(String username) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("DELETE FROM unloads WHERE UserID=(SELECT UserID FROM users WHERE Username=?)")) {
				statement.setString(1, username);
				statement.executeUpdate();
			}
		}
	}

	public void deleteUnload(Unload unload) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("DELETE FROM unloads WHERE UserID=(SELECT UserID FROM users WHERE Username=?) AND Date=?")) {
				statement.setString(1, unload.getUser().getUsername());
				statement.setString(2, unload.getUnload().toString());
				statement.executeUpdate();
			}
		}
	}

	private int getUserID(String username) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("SELECT UserID FROM users WHERE Username=? ORDER BY UserID DESC LIMIT 1")) {
				statement.setString(1, username);
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					return rs.getInt("UserID");
				}
				throw new SQLException("A user by that name doesn't exist");
			}
		}
	}

	private int getUnloadID(int userID, DateTime dateTime) throws SQLException {
		try (Connection connection = this.dataSource.getConnection()) {
			try (PreparedStatement statement = connection.prepareStatement("SELECT UnloadID FROM unloads WHERE UserID=? AND Date=? ORDER BY UserID DESC LIMIT 1")) {
				statement.setInt(1, userID);
				statement.setString(2, dateTime.toString());
				ResultSet rs = statement.executeQuery();
				while (rs.next()) {
					return rs.getInt("UnloadID");
				}
				throw new SQLException("A unload with that user id and date doesn't exist");
			}
		}
	}

}
