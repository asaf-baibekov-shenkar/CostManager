package com.asaf.costmanager;

import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.services.database_connection_service.LocalDatabaseConnectionService;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.CategoriesDerbyDatabaseTableService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;
import com.asaf.costmanager.views.MainView;

import javax.swing.*;
import java.util.List;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainView::new);
		
		IDatabaseConnectionService databaseConnectionService = new LocalDatabaseConnectionService("CostManagerDatabase");
		IDatabaseTableService<Category> categoriesService = new CategoriesDerbyDatabaseTableService(databaseConnectionService);
		try {
			categoriesService.createTableIfNotExist();
			categoriesService.insertRecord(new Category("aaa"));
			categoriesService.insertRecord(new Category("bbb"));
			List<Category> list = categoriesService.fetchRecords();
			System.out.println(list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
