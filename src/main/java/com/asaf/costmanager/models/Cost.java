package com.asaf.costmanager.models;

import java.util.Date;

/**
 * Represents a cost in the CostManager application.
 * <p>
 * A cost is identified by its unique ID and has associated category,
 * total cost, currency, description, and date.
 * </p>
 */
public class Cost {
	
	private int id;
	
	private Category category;
	
	private double total_cost;
	
	private Currency currency;
	
	private String description;
	
	private Date date;
	
	/**
	 * Constructor for Cost with category, currency, total_cost, description, and date.
	 *
	 * @param category The category of the cost.
	 * @param currency The currency of the cost.
	 * @param total_cost The total cost.
	 * @param description The description of the cost.
	 * @param date The date of the cost.
	 */
	public Cost(Category category, Currency currency, double total_cost, String description, Date date) {
		this.id = -1;
		setCategory(category);
		setTotalCost(total_cost);
		setCurrency(currency);
		setDescription(description);
		setDate(date);
	}
	
	/**
	 * Constructor for Cost with an ID, category, currency, total_cost, description, and date.
	 *
	 * @param id The ID of the cost.
	 * @param category The category of the cost.
	 * @param currency The currency of the cost.
	 * @param total_cost The total cost.
	 * @param description The description of the cost.
	 * @param date The date of the cost.
	 */
	public Cost(int id, Category category, Currency currency, double total_cost, String description, Date date) {
		setId(id);
		setCategory(category);
		setTotalCost(total_cost);
		setCurrency(currency);
		setDescription(description);
		setDate(date);
	}
	
	/**
	 * Gets the ID of the cost.
	 *
	 * @return The ID of the cost.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the cost.
	 *
	 * @param id The ID of the cost.
	 * @throws IllegalArgumentException if the ID is negative.
	 */
	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("ID cannot be negative");
		this.id = id;
	}
	
	/**
	 * Gets the category of the cost.
	 *
	 * @return The category of the cost.
	 */
	public Category getCategory() {
		return category;
	}
	
	/**
	 * Sets the category of the cost.
	 *
	 * @param category The category of the cost.
	 * @throws IllegalArgumentException if the category is null.
	 */
	public void setCategory(Category category) {
		if (category == null)
			throw new IllegalArgumentException("Category cannot be null");
		this.category = category;
	}
	
	/**
	 * Gets the total cost.
	 *
	 * @return The total cost.
	 */
	public double getTotalCost() {
		return total_cost;
	}
	
	/**
	 * Sets the total cost.
	 *
	 * @param total_cost The total cost.
	 * @throws IllegalArgumentException if the total_cost is negative.
	 */
	public void setTotalCost(double total_cost) {
		if (total_cost < 0)
			throw new IllegalArgumentException("Sum cannot be negative");
		this.total_cost = total_cost;
	}
	
	/**
	 * Gets the currency of the cost.
	 *
	 * @return The currency of the cost.
	 */
	public Currency getCurrency() {
		return currency;
	}
	
	/**
	 * Sets the currency of the cost.
	 *
	 * @param currency The currency of the cost.
	 */
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	/**
	 * Gets the description of the cost.
	 *
	 * @return The description of the cost.
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Sets the description of the cost.
	 *
	 * @param description The description of the cost.
	 * @throws IllegalArgumentException if the description is null or empty.
	 */
	public void setDescription(String description) {
		if (description == null || description.trim().isEmpty())
			throw new IllegalArgumentException("Description cannot be null or empty");
		this.description = description;
	}
	
	/**
	 * Gets the date of the cost.
	 *
	 * @return The date of the cost.
	 */
	public Date getDate() {
		return date;
	}
	
	/**
	 * Sets the date of the cost.
	 *
	 * @param date The date of the cost.
	 * @throws IllegalArgumentException if the date is null.
	 */
	public void setDate(Date date) {
		if (date == null)
			throw new IllegalArgumentException("Date cannot be null");
		this.date = date;
	}
	
	/**
	 * Returns a string representation of the cost.
	 *
	 * @return A string representation of the cost.
	 */
	@Override
	public String toString() {
		return "Cost{" +
				       "id=" + id +
				       ", category=" + category +
				       ", total_cost=" + total_cost +
				       ", currency='" + currency + '\'' +
				       ", description='" + description + '\'' +
				       ", date=" + date +
				       '}';
	}
}
