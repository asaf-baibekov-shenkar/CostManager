package com.asaf.costmanager;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseCostManagerException;
import com.asaf.costmanager.services.database_table_service.exceptions.DatabaseTableErrorType;
import com.asaf.costmanager.services.database_table_service.interfaces.IDatabaseTableService;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Class for creating mock data and inserting it into the database.
 */
public class MockDataCostManager {
	
	private final IDatabaseTableService<Category> categoryTableService;
	private final IDatabaseTableService<Currency> currencyTableService;
	private final IDatabaseTableService<Cost> costTableService;
	
	/**
	 * Constructor for the MockDataCostManager class.
	 *
	 * @param categoryTableService service for interacting with the category table in the database.
	 * @param currencyTableService service for interacting with the currency table in the database.
	 * @param costTableService service for interacting with the cost table in the database.
	 */
	public MockDataCostManager(
		IDatabaseTableService<Category> categoryTableService,
		IDatabaseTableService<Currency> currencyTableService,
		IDatabaseTableService<Cost> costTableService
	) {
		this.categoryTableService = categoryTableService;
		this.currencyTableService = currencyTableService;
		this.costTableService = costTableService;
	}
	
	/**
	 * Initializes the database by creating the tables if they don't exist.
	 * If the tables already exist, this method does nothing.
	 *
	 * @throws CostManagerException if an exception occurs while interacting with the database.
	 */
	public void initDatabase() throws CostManagerException {
		this.categoryTableService.createTableIfNotExist();
		try {
			this.currencyTableService.createTable();
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
		} catch (CostManagerException e) {
			if (e instanceof DatabaseCostManagerException databaseCostManagerException)
				if (databaseCostManagerException.getErrorType() != DatabaseTableErrorType.TABLE_ALREADY_EXISTS)
					throw e;
		}
		this.costTableService.createTableIfNotExist();
	}
	
	/**
	 * Creates mock data and inserts it into the database.
	 *
	 * @throws CostManagerException if an exception occurs while interacting with the database.
	 */
	public void createMockData() throws CostManagerException {
		this.initDatabase();
		
		List<Category> categories = Arrays.asList(
			new Category("Food"),
			new Category("Clothes"),
			new Category("Entertainment"),
			new Category("Gadgets"),
			new Category("Transportation")
		);
		for (Category category : categories)
			this.categoryTableService.insertRecord(category);
		
		List<Currency> currencies = this.currencyTableService.fetchRecords();
		
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
	
	/**
	 * Helper method to convert a year, month, and day to a Date object.
	 *
	 * @param year the year.
	 * @param month the month.
	 * @param day the day.
	 * @return the Date object.
	 */
	private static Date getDate(int year, Month month, int day) {
		LocalDate localDate = LocalDate.of(year, month, day);
		return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
	}
}
