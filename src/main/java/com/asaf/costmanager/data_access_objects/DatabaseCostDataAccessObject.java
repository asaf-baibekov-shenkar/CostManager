package com.asaf.costmanager.data_access_objects;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.util.List;

public class DatabaseCostDataAccessObject implements IDataAccessObject<Cost> {
	
	private final IDatabaseTableService<Cost> costsDatabaseTableService;
	
	public DatabaseCostDataAccessObject(IDatabaseTableService<Cost> costsDatabaseTableService) {
		this.costsDatabaseTableService = costsDatabaseTableService;
	}
	
	@Override
	public Cost create(Cost cost) {
		try {
			return this.costsDatabaseTableService.insertRecord(cost);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Cost read(int id) {
		try {
			return this.costsDatabaseTableService.fetchRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Cost> readAll() {
		try {
			return this.costsDatabaseTableService.fetchRecords();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void update(int id, Cost category) {
		try {
			this.costsDatabaseTableService.updateRecord(id, category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Cost delete(int id) {
		try {
			return this.costsDatabaseTableService.deleteRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
}
