package com.asaf.costmanager.data_access_objects;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.util.List;

public class DatabaseCurrencyDataAccessObject implements IDataAccessObject<Currency> {
	
	private final IDatabaseTableService<Currency> currenciesDatabaseTableService;
	
	public DatabaseCurrencyDataAccessObject(IDatabaseTableService<Currency> currenciesDatabaseTableService) {
		this.currenciesDatabaseTableService = currenciesDatabaseTableService;
	}
	
	@Override
	public void create(Currency currency) {
		try {
			this.currenciesDatabaseTableService.insertRecord(currency);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Currency read(int id) {
		try {
			return this.currenciesDatabaseTableService.fetchRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Currency> readAll() {
		try {
			return this.currenciesDatabaseTableService.fetchRecords();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void update(int id, Currency category) {
		try {
			this.currenciesDatabaseTableService.updateRecord(id, category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Currency delete(int id) {
		try {
			return this.currenciesDatabaseTableService.deleteRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
}
