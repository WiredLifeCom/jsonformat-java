package com.wiredlife.jsonformatjava.dao;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class DataSource {

	private ComboPooledDataSource pool;

	private static DataSource dataSource;

	public DataSource() {
		this.pool = new ComboPooledDataSource();

		this.pool.setInitialPoolSize(5);
		this.pool.setAcquireIncrement(5);
		this.pool.setMaxPoolSize(50);
		this.pool.setMinPoolSize(5);
		this.pool.setMaxStatements(10);
	}

	public static DataSource getInstance() {
		if (dataSource == null) {
			dataSource = new DataSource();
			return dataSource;
		}
		return dataSource;
	}

	public Connection getConnection() throws SQLException {
		return this.pool.getConnection();
	}

	public void setDriverClass(String driverClass) throws PropertyVetoException {
		this.pool.setDriverClass(driverClass);
	}

	public void setJdbcUrl(String jdbcUrl) {
		this.pool.setJdbcUrl(jdbcUrl);
	}

}
