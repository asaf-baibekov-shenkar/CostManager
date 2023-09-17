package com.asaf.costmanager.coordinator;

import com.asaf.costmanager.MockDataCostManager;
import com.asaf.costmanager.data_access_objects.DatabaseCategoryDataAccessObject;
import com.asaf.costmanager.data_access_objects.DatabaseCostDataAccessObject;
import com.asaf.costmanager.data_access_objects.DatabaseCurrencyDataAccessObject;
import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_connection_service.LocalDatabaseConnectionService;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.CategoriesDerbyDatabaseTableService;
import com.asaf.costmanager.services.database_table_service.CostsDerbyDatabaseTableService;
import com.asaf.costmanager.services.database_table_service.CurrenciesDerbyDatabaseTableService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;
import com.asaf.costmanager.view_models.CategoriesViewModel;
import com.asaf.costmanager.view_models.CostsViewModel;
import com.asaf.costmanager.view_models.MainViewModel;
import com.asaf.costmanager.view_models.ReportsViewModel;
import com.asaf.costmanager.views.MainView;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainCoordinator implements Coordinator {
	
	private @Nullable MainView mainView;
	
	private final IDatabaseConnectionService databaseConnectionService;
	private final IDatabaseTableService<Cost> costsService;
	private final IDatabaseTableService<Currency> currenciesService;
	private final IDatabaseTableService<Category> categoriesService;
	private final CompositeDisposable compositeDisposable;
	private final ArrayList<Coordinator> coordinators;
	
	private final CategoriesViewModel categoriesViewModel;
	private final CostsViewModel costsViewModel;
	private final ReportsViewModel reportsViewModel;
	
	public MainCoordinator() {
		this.coordinators = new ArrayList<>();
		this.compositeDisposable = new CompositeDisposable();
		this.databaseConnectionService = new LocalDatabaseConnectionService("CostManagerDatabase");
		this.costsService = new CostsDerbyDatabaseTableService(databaseConnectionService);
		this.currenciesService = new CurrenciesDerbyDatabaseTableService(databaseConnectionService);
		this.categoriesService = new CategoriesDerbyDatabaseTableService(databaseConnectionService);
		
		IDataAccessObject<Cost> costsDAO = new DatabaseCostDataAccessObject(this.costsService);
		IDataAccessObject<Currency> currencyDAO = new DatabaseCurrencyDataAccessObject(this.currenciesService);
		IDataAccessObject<Category> categoryDAO = new DatabaseCategoryDataAccessObject(this.categoriesService);
		this.categoriesViewModel = new CategoriesViewModel(categoryDAO);
		this.costsViewModel = new CostsViewModel(costsDAO, currencyDAO, categoryDAO);
		this.reportsViewModel = new ReportsViewModel(costsDAO);
		
		this.compositeDisposable.add(
			this.categoriesViewModel
				.updateCategoryObservable()
				.subscribe((databaseEvent) -> {
					this.costsViewModel.updateCategories();
				})
		);
	}
	
	@Override
	public List<Coordinator> getCoordinators() {
		return this.coordinators;
	}
	
	@Override
	public void start() {
//		this.resetDatabase();
//		Locale locale = Locale.forLanguageTag("he-IL");
		Locale locale = null;
		MainViewModel mainViewModel = new MainViewModel(locale);
		this.compositeDisposable.add(
			mainViewModel
				.getNavigationTypeObservable()
				.subscribe((navigationType) -> {
					switch (navigationType) {
						case Reports    -> this.showReportsView();
						case Costs      -> this.showCostsView();
						case Categories -> this.showCategoriesView();
					}
				})
		);
		SwingUtilities.invokeLater(() -> {
			this.mainView = new MainView(mainViewModel);
			mainViewModel.reportNavigationSelected();
		});
	}
	
	private void showReportsView() {
		if (this.mainView == null) return;
		this.mainView.activateReportsView(this.reportsViewModel);
	}
	
	private void showCostsView() {
		if (this.mainView == null) return;
		this.mainView.activateCostsView(this.costsViewModel);
	}
	
	private void showCategoriesView() {
		if (this.mainView == null) return;
		this.mainView.activateCategoriesView(this.categoriesViewModel);
	}
	
	private void resetDatabase() {
		MockDataCostManager mockDataCostManager = new MockDataCostManager(
			this.categoriesService,
			this.currenciesService,
			this.costsService
		);
		try {
			this.databaseConnectionService.dropDatabaseIfExists();
			mockDataCostManager.createMockData();
		} catch (CostManagerException e) {
			throw new RuntimeException(e);
		}
	}
}
