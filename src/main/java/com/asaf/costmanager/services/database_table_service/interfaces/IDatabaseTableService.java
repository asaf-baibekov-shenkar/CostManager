package com.asaf.costmanager.services.database_table_service.interfaces;

import com.asaf.costmanager.exceptions.CostManagerException;

import java.util.List;

public interface IDatabaseTableService<Record> {
	void createTable() throws CostManagerException;
	void createTableIfNotExist() throws CostManagerException;
	void dropTable() throws CostManagerException;
	void dropTableIfExist() throws CostManagerException;
	void insertRecord(Record record) throws CostManagerException;
	void updateRecord(int id, Record record) throws CostManagerException;
	Record fetchRecord(int id) throws CostManagerException;
	List<Record> fetchRecords() throws CostManagerException;
	Record deleteRecord(int id) throws CostManagerException;
}
