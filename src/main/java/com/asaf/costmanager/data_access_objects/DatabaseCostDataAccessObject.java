package com.asaf.costmanager.data_access_objects;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.util.List;

/**
 * Data Access Object for Costs.
 * <p>
 * This class provides an implementation for the IDataAccessObject interface for Cost objects.
 * </p>
 */
public class DatabaseCostDataAccessObject implements IDataAccessObject<Cost> {
	
	private final IDatabaseTableService<Cost> costsDatabaseTableService;
	
	/**
	 * Constructor for DatabaseCostDataAccessObject.
	 *
	 * @param costsDatabaseTableService The database table service for costs.
	 */
	public DatabaseCostDataAccessObject(IDatabaseTableService<Cost> costsDatabaseTableService) {
		this.costsDatabaseTableService = costsDatabaseTableService;
	}
	
	/**
	 * Create a new cost.
	 *
	 * @param cost The cost to be created.
	 */
	@Override
	public void create(Cost cost) {
		try {
			this.costsDatabaseTableService.insertRecord(cost);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Read a cost by its id.
	 *
	 * @param id The id of the cost to be read.
	 * @return The cost with the specified id.
	 */
	@Override
	public Cost read(int id) {
		try {
			return this.costsDatabaseTableService.fetchRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Read all costs.
	 *
	 * @return A list of all costs.
	 */
	@Override
	public List<Cost> readAll() {
		try {
			return this.costsDatabaseTableService.fetchRecords();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Update an existing cost.
	 *
	 * @param id The id of the cost to be updated.
	 * @param category The updated cost.
	 */
	@Override
	public void update(int id, Cost category) {
		try {
			this.costsDatabaseTableService.updateRecord(id, category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Delete a cost by its id.
	 *
	 * @param id The id of the cost to be deleted.
	 * @return The deleted cost.
	 */
	@Override
	public Cost delete(int id) {
		try {
			return this.costsDatabaseTableService.deleteRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
}
