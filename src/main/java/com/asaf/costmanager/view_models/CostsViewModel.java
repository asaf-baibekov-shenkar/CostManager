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

/**
 * ViewModel class for managing the costs.
 */
public class CostsViewModel {
	
	private final IDataAccessObject<Cost> costsDOA;
	private final IDataAccessObject<Category> categoriesDOA;
	
	private final List<Currency> currencies;
	
	private final BehaviorSubject<List<Category>> categoriesBehaviorSubject;
	private final PublishSubject<List<Cost>> costsPublishSubject;
	
	/**
	 * Constructor for the CostsViewModel.
	 *
	 * @param costsDOA the Data Access Object for the costs.
	 * @param currenciesDOA the Data Access Object for the currencies.
	 * @param categoriesDOA the Data Access Object for the categories.
	 */
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
	
	/**
	 * Returns an observable for the categories updates.
	 *
	 * @return an observable for the categories updates.
	 */
	public Observable<List<Category>> getCategoriesObservable() {
		return this.categoriesBehaviorSubject.hide();
	}
	
	/**
	 * Returns an observable for the costs updates.
	 *
	 * @return an observable for the costs updates.
	 */
	public Observable<List<Cost>> getCostsObservable() {
		return this.costsPublishSubject.hide();
	}
	
	/**
	 * Fetches a category by its index.
	 *
	 * @param index the index of the category.
	 * @return the category at the specified index.
	 */
	public Category getCategoryAt(int index) {
		return this.categoriesBehaviorSubject.getValue().get(index);
	}
	
	/**
	 * Fetches the number of categories.
	 *
	 * @return the number of categories.
	 */
	public int getCategoriesCount() {
		return this.categoriesBehaviorSubject.getValue().size();
	}
	
	/**
	 * Fetches a currency by its index.
	 *
	 * @param index the index of the currency.
	 * @return the currency at the specified index.
	 */
	public Currency getCurrencyAt(int index) {
		return this.currencies.get(index);
	}
	
	/**
	 * Fetches the number of currencies.
	 *
	 * @return the number of currencies.
	 */
	public int getCurrenciesCount() {
		return this.currencies.size();
	}
	
	/**
	 * Adds a new cost.
	 *
	 * @param categoryIndex the index of the category.
	 * @param currencyIndex the index of the currency.
	 * @param amount the amount of the cost.
	 * @param description the description of the cost.
	 * @throws CostManagerException if the parameters are not valid.
	 */
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
	
	/**
	 * Updates the categories.
	 */
	public void updateCategories() {
		this.categoriesBehaviorSubject.onNext(this.categoriesDOA.readAll());
	}
}
