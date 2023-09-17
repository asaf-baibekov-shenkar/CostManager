package com.asaf.costmanager.services.database_table_service.exceptions;

import com.asaf.costmanager.exceptions.CostManagerException;

/**
 * Exception thrown when an operation on a database table fails.
 * <p>
 * This exception includes an error type that specifies the kind of operation that failed.
 * </p>
 */
public class DatabaseCostManagerException extends CostManagerException {
	
	private final DatabaseTableErrorType errorType;
	
	/**
	 * Constructs a new DatabaseCostManagerException with the specified error type and detail message.
	 *
	 * @param errorType the type of the error.
	 * @param message the detail message.
	 */
	public DatabaseCostManagerException(DatabaseTableErrorType errorType, String message) {
		super(message);
		this.errorType = errorType;
	}
	
	/**
	 * Constructs a new DatabaseCostManagerException with the specified error type, detail message, and cause.
	 *
	 * @param errorType the type of the error.
	 * @param message the detail message.
	 * @param cause the cause of the exception.
	 */
	public DatabaseCostManagerException(DatabaseTableErrorType errorType, String message, Throwable cause) {
		super(message, cause);
		this.errorType = errorType;
	}
	
	/**
	 * Returns the error type of this exception.
	 *
	 * @return the error type.
	 */
	public DatabaseTableErrorType getErrorType() {
		return this.errorType;
	}
}