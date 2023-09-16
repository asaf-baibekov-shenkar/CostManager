package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;

import java.util.List;

public class CostsViewModel {
	
	private final IDataAccessObject<Cost> costsDOA;
	private final IDataAccessObject<Category> categoriesDOA;
	
	public CostsViewModel(IDataAccessObject<Cost> costsDOA, IDataAccessObject<Category> categoriesDOA) {
		this.costsDOA = costsDOA;
		this.categoriesDOA = categoriesDOA;
	}
	
	public List<Category> getCategories() {
		return this.categoriesDOA.readAll();
	}
	
}
