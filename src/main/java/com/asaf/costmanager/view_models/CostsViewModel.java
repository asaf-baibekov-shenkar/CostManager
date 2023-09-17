package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.models.Currency;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.subjects.BehaviorSubject;
import io.reactivex.rxjava3.subjects.PublishSubject;

import java.util.Date;
import java.util.List;

public class CostsViewModel {
	
	private final IDataAccessObject<Cost> costsDOA;
	private final IDataAccessObject<Category> categoriesDOA;
	
	private final List<Currency> currencies;
	
	private final BehaviorSubject<List<Category>> categoriesBehaviorSubject;
	private final PublishSubject<List<Cost>> costsPublishSubject;
	
	public CostsViewModel(
		IDataAccessObject<Cost> costsDOA,
		IDataAccessObject<Currency> currenciesDOA,
		IDataAccessObject<Category> categoriesDOA
	) {
		this.costsDOA = costsDOA;
		this.categoriesDOA = categoriesDOA;
		this.currencies = currenciesDOA.readAll();
		this.categoriesBehaviorSubject = BehaviorSubject.create();
		this.costsPublishSubject = PublishSubject.create();
		this.updateCategories();
	}
	
	public Observable<List<Category>> getCategoriesObservable() {
		return this.categoriesBehaviorSubject.hide();
	}
	
	public Observable<List<Cost>> getCostsObservable() {
		return this.costsPublishSubject.hide();
	}
	
	public Category getCategoryAt(int index) {
		return this.categoriesBehaviorSubject.getValue().get(index);
	}
	
	public int getCategoriesCount() {
		return this.categoriesBehaviorSubject.getValue().size();
	}
	
	public Currency getCurrencyAt(int index) {
		return this.currencies.get(index);
	}
	
	public int getCurrenciesCount() {
		return this.currencies.size();
	}
	
	public void addCost(int categoryIndex, int currencyIndex, double amount, String description) throws CostManagerException {
		if (categoryIndex < 0 || categoryIndex >= this.getCategoriesCount())
			throw new CostManagerException("Invalid category index");
		if (currencyIndex < 0 || currencyIndex >= this.currencies.size())
			throw new CostManagerException("Invalid currency index");
		if (amount <= 0)
			throw new CostManagerException("Invalid amount");
		if (description == null || description.isEmpty())
			throw new CostManagerException("Invalid description");
		Cost cost = new Cost(this.getCategoryAt(categoryIndex), this.currencies.get(currencyIndex), amount, description, new Date());
		this.costsDOA.create(cost);
		this.costsPublishSubject.onNext(this.costsDOA.readAll());
	}
	
	public void updateCategories() {
		this.categoriesBehaviorSubject.onNext(this.categoriesDOA.readAll());
	}
}
