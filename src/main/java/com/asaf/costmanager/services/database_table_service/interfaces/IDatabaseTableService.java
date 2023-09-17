package com.asaf.costmanager.services.database_table_service.interfaces;

import com.asaf.costmanager.exceptions.CostManagerException;

import java.util.List;

/**
 * Interface for services that manage database table operations.
 *
 * @param <Record> the type of the records in the table.
 */
public interface IDatabaseTableService<Record> {
	/**
	 * Creates a new table.
	 *
	 * @throws CostManagerException if the table cannot be created.
	 */
	void createTable() throws CostManagerException;
	
	/**
	 * Creates a new table if it does not already exist.
	 *
	 * @throws CostManagerException if the table cannot be created.
	 */
	void createTableIfNotExist() throws CostManagerException;
	
	/**
	 * Drops the table.
	 *
	 * @throws CostManagerException if the table cannot be dropped.
	 */
	void dropTable() throws CostManagerException;
	
	/**
	 * Drops the table if it exists.
	 *
	 * @throws CostManagerException if the table cannot be dropped.
	 */
	void dropTableIfExist() throws CostManagerException;
	
	/**
	 * Inserts a new record into the table.
	 *
	 * @param record the record to insert.
	 * @throws CostManagerException if the record cannot be inserted.
	 */
	void insertRecord(Record record) throws CostManagerException;
	
	/**
	 * Updates an existing record in the table.
	 *
	 * @param id the id of the record to update.
	 * @param record the updated record.
	 * @throws CostManagerException if the record cannot be updated.
	 */
	void updateRecord(int id, Record record) throws CostManagerException;
	
	/**
	 * Fetches a record from the table by its id.
	 *
	 * @param id the id of the record to fetch.
	 * @return the fetched record.
	 * @throws CostManagerException if the record cannot be fetched.
	 */
	Record fetchRecord(int id) throws CostManagerException;
	
	/**
	 * Fetches all records from the table.
	 *
	 * @return a list of all records in the table.
	 * @throws CostManagerException if the records cannot be fetched.
	 */
	List<Record> fetchRecords() throws CostManagerException;
	
	/**
	 * Deletes a record from the table by its id.
	 *
	 * @param id the id of the record to delete.
	 * @return the deleted record.
	 * @throws CostManagerException if the record cannot be deleted.
	 */
	Record deleteRecord(int id) throws CostManagerException;
}
