package com.asaf.costmanager.exceptions;

/**
 * Custom exception class for the CostManager application.
 * <p>
 * This class extends the standard Java Exception class and provides constructors to
 * create exceptions with a message and an optional cause.
 * </p>
 */
public class CostManagerException extends Exception {
	
	/**
	 * Constructor for CostManagerException with a message.
	 *
	 * @param message The message describing the exception.
	 */
	public CostManagerException(String message) {
		super(message);
	}
	
	/**
	 * Constructor for CostManagerException with a message and a cause.
	 *
	 * @param message The message describing the exception.
	 * @param cause The cause of the exception.
	 */
	public CostManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
