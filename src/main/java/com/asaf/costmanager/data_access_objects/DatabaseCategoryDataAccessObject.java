package com.asaf.costmanager.data_access_objects;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.util.List;

/**
 * Data Access Object for Categories.
 * <p>
 * This class provides an implementation for the IDataAccessObject interface for Category objects.
 * </p>
 */
public class DatabaseCategoryDataAccessObject implements IDataAccessObject<Category> {
	
	private final IDatabaseTableService<Category> categoriesDatabaseTableService;
	
	/**
	 * Constructor for DatabaseCategoryDataAccessObject.
	 *
	 * @param categoriesDatabaseTableService The database table service for categories.
	 */
	public DatabaseCategoryDataAccessObject(IDatabaseTableService<Category> categoriesDatabaseTableService) {
		this.categoriesDatabaseTableService = categoriesDatabaseTableService;
	}
	
	/**
	 * Create a new category.
	 *
	 * @param category The category to be created.
	 */
	@Override
	public void create(Category category) {
		try {
			this.categoriesDatabaseTableService.insertRecord(category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Read a category by its id.
	 *
	 * @param id The id of the category to be read.
	 * @return The category with the specified id.
	 */
	@Override
	public Category read(int id) {
		try {
			return this.categoriesDatabaseTableService.fetchRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Read all categories.
	 *
	 * @return A list of all categories.
	 */
	@Override
	public List<Category> readAll() {
		try {
			return this.categoriesDatabaseTableService.fetchRecords();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Update an existing category.
	 *
	 * @param id The id of the category to be updated.
	 * @param category The updated category.
	 */
	@Override
	public void update(int id, Category category) {
		try {
			this.categoriesDatabaseTableService.updateRecord(id, category);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Delete a category by its id.
	 *
	 * @param id The id of the category to be deleted.
	 * @return The deleted category.
	 */
	@Override
	public Category delete(int id) {
		try {
			return this.categoriesDatabaseTableService.deleteRecord(id);
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
}
