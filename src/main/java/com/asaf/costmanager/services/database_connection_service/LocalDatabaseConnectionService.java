package com.asaf.costmanager.services.database_connection_service;

import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class LocalDatabaseConnectionService implements IDatabaseConnectionService {
	private Connection connection;
	private final String databaseName;
	
	public LocalDatabaseConnectionService(String databaseName) {
		this.databaseName = databaseName;
	}
	
	@Override
	public Connection connectIfNeeded() {
		try {
			if (this.connection == null || this.connection.isClosed()) {
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
				this.connection = DriverManager.getConnection("jdbc:derby:" + this.databaseName + ";create=true");
			}
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return this.connection;
	}
	
	@Override
	public void disconnect() {
		try {
			if (this.connection != null && !this.connection.isClosed())
				this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
