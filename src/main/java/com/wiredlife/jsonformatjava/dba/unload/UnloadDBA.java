package com.wiredlife.jsonformatjava.dba.unload;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wiredlife.jsonformatjava.model.unload.Unload;
import com.wiredlife.jsonformatjava.model.unload.User;

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
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unloads (UnloadID integer, UserID integer, Data string, primary key (UnloadID) foreign key (UserID) references users(UserID))");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<User> getUsers() {
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

			PreparedStatement stmtInsertUnload = this.connection.prepareStatement("INSERT INTO unloads (UserID, Data) VALUES (?, ?)");
			stmtInsertUnload.setInt(1, userId);
			stmtInsertUnload.setString(2, Unload.toJson(unload));
			stmtInsertUnload.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public List<Unload> getUnloads(String username) {
		try {
			PreparedStatement stmtGetUnloads = this.connection.prepareStatement("SELECT Data FROM unloads INNER JOIN users ON unloads.UserID = users.UserID WHERE users.Username=?");
			stmtGetUnloads.setString(1, username);
			ResultSet rsGetUnloads = stmtGetUnloads.executeQuery();

			List<Unload> unloads = new ArrayList<Unload>();
			while (rsGetUnloads.next()) {
				unloads.add(Unload.fromJson(rsGetUnloads.getString("Data")));
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
