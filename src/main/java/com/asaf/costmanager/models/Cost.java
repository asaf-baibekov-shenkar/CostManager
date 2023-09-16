package com.asaf.costmanager.models;

import java.util.Date;

public class Cost {
	
	private int id;
	
	private Category category;
	
	private double total_cost;
	
	private Currency currency;
	
	private String description;
	
	private Date date;
	
	public Cost(Category category, Currency currency, double total_cost, String description, Date date) {
		this.id = -1;
		setCategory(category);
		setTotalCost(total_cost);
		setCurrency(currency);
		setDescription(description);
		setDate(date);
	}
	
	public Cost(int id, Category category, Currency currency, double total_cost, String description, Date date) {
		setId(id);
		setCategory(category);
		setTotalCost(total_cost);
		setCurrency(currency);
		setDescription(description);
		setDate(date);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("ID cannot be negative");
		this.id = id;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		if (category == null)
			throw new IllegalArgumentException("Category cannot be null or empty");
		this.category = category;
	}
	
	public double getTotalCost() {
		return total_cost;
	}
	
	public void setTotalCost(double total_cost) {
		if (total_cost < 0)
			throw new IllegalArgumentException("Sum cannot be negative");
		this.total_cost = total_cost;
	}
	
	public Currency getCurrency() {
		return currency;
	}
	
	public void setCurrency(Currency currency) {
		this.currency = currency;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		if (description == null || description.trim().isEmpty())
			throw new IllegalArgumentException("Description cannot be null or empty");
		this.description = description;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		if (date == null)
			throw new IllegalArgumentException("Date cannot be null");
		this.date = date;
	}
	
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
