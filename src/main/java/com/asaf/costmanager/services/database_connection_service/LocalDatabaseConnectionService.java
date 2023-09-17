package com.asaf.costmanager.services.database_connection_service;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.stream.Stream;

/**
 * Implementation of the IDatabaseConnectionService interface for local databases.
 * <p>
 * This class manages the connection to a local database, and provides methods
 * to drop the database.
 * </p>
 */
public class LocalDatabaseConnectionService implements IDatabaseConnectionService {
	private Connection connection;
	private final String databaseName;
	
	/**
	 * Constructs a LocalDatabaseConnectionService object.
	 *
	 * @param databaseName the name of the database.
	 */
	public LocalDatabaseConnectionService(String databaseName) {
		this.databaseName = databaseName;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void disconnect() {
		try {
			if (this.connection != null && !this.connection.isClosed())
				this.connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dropDatabase() throws CostManagerException {
		this.disconnect();
		try (Stream<Path> paths = Files.walk(Path.of(databaseName))) {
			paths.sorted(Comparator.reverseOrder())
				.map(Path::toFile)
				.forEach(file -> {
					if (!file.delete())
						System.err.println("Failed to delete " + file);
				});
		} catch (IOException e) {
			throw new CostManagerException("Error deleting the database", e);
		}
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void dropDatabaseIfExists() throws CostManagerException {
		Path databasePath = Paths.get(databaseName);
		if (!Files.exists(databasePath)) return;
		dropDatabase();
	}
}
