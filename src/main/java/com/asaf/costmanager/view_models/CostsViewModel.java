package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.models.Currency;

import java.util.List;

public class CostsViewModel {
	
	private final IDataAccessObject<Cost> costsDOA;
	private final IDataAccessObject<Currency> currenciesDOA;
	private final IDataAccessObject<Category> categoriesDOA;
	
	public CostsViewModel(
		IDataAccessObject<Cost> costsDOA,
		IDataAccessObject<Currency> currenciesDOA,
		IDataAccessObject<Category> categoriesDOA
	) {
		this.costsDOA = costsDOA;
		this.currenciesDOA = currenciesDOA;
		this.categoriesDOA = categoriesDOA;
	}
	
	public List<Category> getCategories() {
		return this.categoriesDOA.readAll();
	}
	
	public List<Currency> getCurrencies() {
		return this.currenciesDOA.readAll();
	}
}
