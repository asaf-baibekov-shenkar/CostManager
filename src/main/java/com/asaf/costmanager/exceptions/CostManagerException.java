package com.asaf.costmanager.exceptions;

public class CostManagerException extends Exception {
	
	public CostManagerException(String message) {
		super(message);
	}
	
	public CostManagerException(String message, Throwable cause) {
		super(message, cause);
	}
}
