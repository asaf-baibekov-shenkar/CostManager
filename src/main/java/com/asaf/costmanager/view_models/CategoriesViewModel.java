package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Category;

import java.util.List;

public class CategoriesViewModel {
	
	private final IDataAccessObject<Category> categoryDAO;
	
	public CategoriesViewModel(IDataAccessObject<Category> categoryDAO) {
		this.categoryDAO = categoryDAO;
	}
	
	public void saveCategory(String text) {
		this.categoryDAO.create(new Category(text));
	}
	
	public List<Category> getAllCategories() {
		return this.categoryDAO.readAll();
	}
	
	public void deleteCategory(int id) {
		this.categoryDAO.delete(id);
	}
}
