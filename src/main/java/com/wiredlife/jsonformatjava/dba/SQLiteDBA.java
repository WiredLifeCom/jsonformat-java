package com.wiredlife.jsonformatjava.dba;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.wiredlife.jsonformatjava.model.Data;

public class SQLiteDBA {

	private Connection connection;

	private SQLiteDBA() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public SQLiteDBA(String database) {
		this();
		try {
			this.connection = DriverManager.getConnection(String.format("jdbc:sqlite:%s", database));

			Statement statement = this.connection.createStatement();
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS unloads (id int auto_increment, username string, data string)");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addUnload(Data data) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("INSERT INTO unloads (username, data) VALUES (?, ?)");
			statement.setString(1, data.getUser().getUsername());
			statement.setString(2, Data.toJson(data));
			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getUnloads(String username) {
		try {
			PreparedStatement statement = this.connection.prepareStatement("SELECT data FROM unloads WHERE username=?");
			statement.setString(1, username);
			ResultSet rs = statement.executeQuery();

			List<String> datas = new ArrayList<String>();
			while (rs.next()) {
				datas.add(rs.getString("data"));
			}
			return datas;
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

}
