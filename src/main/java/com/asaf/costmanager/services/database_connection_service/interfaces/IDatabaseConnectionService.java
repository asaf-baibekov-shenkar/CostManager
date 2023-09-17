package com.asaf.costmanager.services.database_connection_service.interfaces;

import com.asaf.costmanager.exceptions.CostManagerException;

import java.sql.Connection;

/**
 * Interface for database connection services.
 * <p>
 * Provides methods to manage database connections, and to drop the database.
 * </p>
 */
public interface IDatabaseConnectionService {
	
	/**
	 * Connects to the database if not already connected.
	 *
	 * @return The database connection.
	 */
	Connection connectIfNeeded();
	
	/**
	 * Disconnects from the database.
	 */
	void disconnect();
	
	/**
	 * Drops the database.
	 *
	 * @throws CostManagerException if there is an error dropping the database.
	 */
	void dropDatabase() throws CostManagerException;
	
	/**
	 * Drops the database if it exists.
	 *
	 * @throws CostManagerException if there is an error dropping the database.
	 */
	void dropDatabaseIfExists() throws CostManagerException;
}
