package com.asaf.costmanager;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MockDataCostManager {
	
	private final IDatabaseConnectionService databaseConnectionService;
	private final IDatabaseTableService<Category> categoryTableService;
	private final IDatabaseTableService<Cost> costTableService;
	
	public MockDataCostManager(
		IDatabaseConnectionService databaseConnectionService,
		IDatabaseTableService<Category> categoryTableService,
		IDatabaseTableService<Cost> costTableService
	) {
		this.databaseConnectionService = databaseConnectionService;
		this.categoryTableService = categoryTableService;
		this.costTableService = costTableService;
	}
	
	public void createMockData() throws CostManagerException {
		this.databaseConnectionService.connectIfNeeded();
		
		this.categoryTableService.createTableIfNotExist();
		this.costTableService.createTableIfNotExist();
		
		List<Category> categories = Arrays.asList(
			new Category("Food"),
			new Category("Clothes"),
			new Category("Entertainment"),
			new Category("Gadgets"),
			new Category("Transportation")
		);
		for (Category category : categories)
			this.categoryTableService.insertRecord(category);
		
		List<Cost> costs = Arrays.asList(
			new Cost(categories.get(0), 20, "ILS", "Burger", getDate(2021, Month.MARCH, 1)),
			new Cost(categories.get(0), 30, "ILS", "Pizza", getDate(2021, Month.MARCH, 2)),
			new Cost(categories.get(0), 40, "ILS", "Sushi", getDate(2021, Month.MARCH, 3)),
			new Cost(categories.get(1), 50, "ILS", "Dress", getDate(2021, Month.MARCH, 4)),
			new Cost(categories.get(1), 60, "ILS", "Shirt", getDate(2021, Month.MARCH, 2)),
			new Cost(categories.get(2), 70, "ILS", "Movie", getDate(2021, Month.MARCH, 5)),
			new Cost(categories.get(2), 80, "ILS", "Theater", getDate(2021, Month.MARCH, 6)),
			new Cost(categories.get(3), 3000, "ILS", "iPhone", getDate(2022, Month.APRIL, 7))
		);
		for (Cost cost : costs)
			this.costTableService.insertRecord(cost);
		
	}
	
	private static Date getDate(int year, Month month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
