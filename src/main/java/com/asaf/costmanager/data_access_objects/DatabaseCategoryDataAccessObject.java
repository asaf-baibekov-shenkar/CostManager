package com.asaf.costmanager.data_access_objects;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.util.List;

public class DatabaseCategoryDataAccessObject implements IDataAccessObject<Category> {
	
	private final IDatabaseTableService<Category> categoriesDatabaseTableService;
	
	public DatabaseCategoryDataAccessObject(IDatabaseTableService<Category> categoriesDatabaseTableService) {
		this.categoriesDatabaseTableService = categoriesDatabaseTableService;
	}
	
	@Override
	public Category create(Category category) {
		try {
			return this.categoriesDatabaseTableService.insertRecord(category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Category read(int id) {
		try {
			return this.categoriesDatabaseTableService.fetchRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public List<Category> readAll() {
		try {
			return this.categoriesDatabaseTableService.fetchRecords();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public void update(int id, Category category) {
		try {
			this.categoriesDatabaseTableService.updateRecord(id, category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	@Override
	public Category delete(int id) {
		return null;
	}
}
