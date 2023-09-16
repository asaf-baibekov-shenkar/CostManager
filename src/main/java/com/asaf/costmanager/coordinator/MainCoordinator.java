package com.asaf.costmanager.coordinator;

import com.asaf.costmanager.data_access_objects.DatabaseCategoryDataAccessObject;
import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.services.database_connection_service.LocalDatabaseConnectionService;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.CategoriesDerbyDatabaseTableService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;
import com.asaf.costmanager.view_models.CategoriesViewModel;
import com.asaf.costmanager.view_models.MainViewModel;
import com.asaf.costmanager.views.MainView;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainCoordinator implements Coordinator {
	
	private @Nullable MainView mainView;
	
	private final IDatabaseTableService<Category> categoriesService;
	private final CompositeDisposable compositeDisposable;
	private final ArrayList<Coordinator> coordinators;
	
	public MainCoordinator() {
		this.coordinators = new ArrayList<>();
		this.compositeDisposable = new CompositeDisposable();
		IDatabaseConnectionService databaseConnectionService = new LocalDatabaseConnectionService("CostManagerDatabase");
		databaseConnectionService.connectIfNeeded();
		this.categoriesService = new CategoriesDerbyDatabaseTableService(databaseConnectionService);
	}
	
	@Override
	public List<Coordinator> getCoordinators() {
		return this.coordinators;
	}
	
	@Override
	public void start() {
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
		this.mainView.activateReportsView();
	}
	
	private void showCostsView() {
		if (this.mainView == null) return;
		this.mainView.activateCostsView();
	}
	
	private void showCategoriesView() {
		if (this.mainView == null) return;
		try {
			this.categoriesService.createTableIfNotExist();
		} catch (Exception e) {
			e.printStackTrace();
		}
		IDataAccessObject<Category> categoryDAO = new DatabaseCategoryDataAccessObject(this.categoriesService);
		CategoriesViewModel categoriesViewModel = new CategoriesViewModel(categoryDAO);
		this.mainView.activateCategoriesView(categoriesViewModel);
	}
}
