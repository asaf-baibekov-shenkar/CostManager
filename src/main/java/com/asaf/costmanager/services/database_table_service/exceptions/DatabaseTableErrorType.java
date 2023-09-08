package com.asaf.costmanager.services.database_table_service.exceptions;

public enum DatabaseTableErrorType {
	TABLE_ALREADY_EXISTS,
	TABLE_DOES_NOT_EXIST,
	CREATE_TABLE_FAILED,
	DROP_TABLE_FAILED,
	FETCH_FAILED,
	INSERT_FAILED,
	UPDATE_FAILED,
	DELETE_FAILED
}
