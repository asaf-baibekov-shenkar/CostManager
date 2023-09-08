package com.asaf.costmanager.services.database_connection_service.interfaces;

import java.sql.Connection;

public interface IDatabaseConnectionService {
	Connection connectIfNeeded();
	void disconnect();
}
