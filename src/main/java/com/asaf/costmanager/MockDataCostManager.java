package com.asaf.costmanager;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MockDataCostManager {
	
	private final IDatabaseTableService<Category> categoryTableService;
	private final IDatabaseTableService<Currency> currencyTableService;
	private final IDatabaseTableService<Cost> costTableService;
	
	public MockDataCostManager(
		IDatabaseTableService<Category> categoryTableService,
		IDatabaseTableService<Currency> currencyTableService,
		IDatabaseTableService<Cost> costTableService
	) {
		this.categoryTableService = categoryTableService;
		this.currencyTableService = currencyTableService;
		this.costTableService = costTableService;
	}
	
	public void createMockData() throws CostManagerException {
		this.categoryTableService.createTableIfNotExist();
		this.currencyTableService.createTableIfNotExist();
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
		
		List<Currency> currencies = Arrays.asList(
			new Currency("ILS", "₪"),
			new Currency("USD", "$"),
			new Currency("EUR", "€"),
			new Currency("GBP", "£"),
			new Currency("JPY", "¥"),
			new Currency("PLN", "zł"),
			new Currency("RUB", "₽")
		);
		for (Currency currency : currencies)
			this.currencyTableService.insertRecord(currency);
		
		List<Cost> costs = Arrays.asList(
			new Cost(categories.get(0), currencies.get(0), 20, "Burger", getDate(2021, Month.MARCH, 1)),
			new Cost(categories.get(0), currencies.get(0), 30, "Pizza", getDate(2021, Month.MARCH, 2)),
			new Cost(categories.get(0), currencies.get(4), 40, "Sushi", getDate(2021, Month.MARCH, 3)),
			new Cost(categories.get(1), currencies.get(2), 50, "Dress", getDate(2021, Month.MARCH, 4)),
			new Cost(categories.get(1), currencies.get(1), 60, "Shirt", getDate(2021, Month.MARCH, 2)),
			new Cost(categories.get(2), currencies.get(3), 70, "Movie", getDate(2021, Month.MARCH, 5)),
			new Cost(categories.get(2), currencies.get(6), 80, "Theater", getDate(2021, Month.MARCH, 6)),
			new Cost(categories.get(3), currencies.get(5), 3000, "iPhone", getDate(2022, Month.APRIL, 7))
		);
		for (Cost cost : costs)
			this.costTableService.insertRecord(cost);
		
	}
	
	private static Date getDate(int year, Month month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
