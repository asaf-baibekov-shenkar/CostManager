package com.asaf.costmanager.data_access_objects;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.util.List;

/**
 * Data Access Object for Currencies.
 * <p>
 * This class provides an implementation for the IDataAccessObject interface for Currency objects.
 * </p>
 */
public class DatabaseCurrencyDataAccessObject implements IDataAccessObject<Currency> {
	
	private final IDatabaseTableService<Currency> currenciesDatabaseTableService;
	
	/**
	 * Constructor for DatabaseCurrencyDataAccessObject.
	 *
	 * @param currenciesDatabaseTableService The database table service for currencies.
	 */
	public DatabaseCurrencyDataAccessObject(IDatabaseTableService<Currency> currenciesDatabaseTableService) {
		this.currenciesDatabaseTableService = currenciesDatabaseTableService;
	}
	
	/**
	 * Create a new currency.
	 *
	 * @param currency The currency to be created.
	 */
	@Override
	public void create(Currency currency) {
		try {
			this.currenciesDatabaseTableService.insertRecord(currency);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Read a currency by its id.
	 *
	 * @param id The id of the currency to be read.
	 * @return The currency with the specified id.
	 */
	@Override
	public Currency read(int id) {
		try {
			return this.currenciesDatabaseTableService.fetchRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Read all currencies.
	 *
	 * @return A list of all currencies.
	 */
	@Override
	public List<Currency> readAll() {
		try {
			return this.currenciesDatabaseTableService.fetchRecords();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Update an existing currency.
	 *
	 * @param id The id of the currency to be updated.
	 * @param category The updated currency.
	 */
	@Override
	public void update(int id, Currency category) {
		try {
			this.currenciesDatabaseTableService.updateRecord(id, category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Delete a currency by its id.
	 *
	 * @param id The id of the currency to be deleted.
	 * @return The deleted currency.
	 */
	@Override
	public Currency delete(int id) {
		try {
			return this.currenciesDatabaseTableService.deleteRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
}
