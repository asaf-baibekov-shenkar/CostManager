package com.asaf.costmanager.services.database_connection_service.interfaces;

import com.asaf.costmanager.exceptions.CostManagerException;

import java.sql.Connection;

public interface IDatabaseConnectionService {
	Connection connectIfNeeded();
	void disconnect();
	void dropDatabase() throws CostManagerException;
	void dropDatabaseIfExists() throws CostManagerException;
}
