package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.models.Currency;

import java.util.Date;
import java.util.List;

public class CostsViewModel {
	
	private final IDataAccessObject<Cost> costsDOA;
	private final IDataAccessObject<Currency> currenciesDOA;
	private final IDataAccessObject<Category> categoriesDOA;
	
	private List<Category> categories;
	private List<Currency> currencies;
	
	public CostsViewModel(
		IDataAccessObject<Cost> costsDOA,
		IDataAccessObject<Currency> currenciesDOA,
		IDataAccessObject<Category> categoriesDOA
	) {
		this.costsDOA = costsDOA;
		this.currenciesDOA = currenciesDOA;
		this.categoriesDOA = categoriesDOA;
		this.categories = this.categoriesDOA.readAll();
		this.currencies = this.currenciesDOA.readAll();
	}
	
	public Category getCategoryAt(int index) {
		return this.categories.get(index);
	}
	
	public int getCategoriesCount() {
		return this.categories.size();
	}
	
	public Currency getCurrencyAt(int index) {
		return this.currencies.get(index);
	}
	
	public int getCurrenciesCount() {
		return this.currencies.size();
	}
	
	public void addCost(int categoryIndex, int currencyIndex, double amount, String description) throws CostManagerException {
		if (categoryIndex < 0 || categoryIndex >= this.categories.size())
			throw new CostManagerException("Invalid category index");
		if (currencyIndex < 0 || currencyIndex >= this.currencies.size())
			throw new CostManagerException("Invalid currency index");
		if (amount <= 0)
			throw new CostManagerException("Invalid amount");
		if (description == null || description.isEmpty())
			throw new CostManagerException("Invalid description");
		Cost cost = new Cost(this.categories.get(categoryIndex), this.currencies.get(currencyIndex), amount, description, new Date());
		this.costsDOA.create(cost);
	}
}
