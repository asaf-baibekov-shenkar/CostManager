package com.asaf.costmanager.services.database_table_service.exceptions;

import com.asaf.costmanager.exceptions.CostManagerException;

public class DatabaseCostManagerException extends CostManagerException {
	
	private final DatabaseTableErrorType errorType;
	
	public DatabaseCostManagerException(DatabaseTableErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}
	
	public DatabaseCostManagerException(DatabaseTableErrorType errorType, String message, Throwable cause) {
		super(message, cause);
		this.errorType = errorType;
	}
	
	public DatabaseTableErrorType getErrorType() {
		return this.errorType;
	}
}
