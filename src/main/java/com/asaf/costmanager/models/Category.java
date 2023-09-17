package com.asaf.costmanager.models;

/**
 * Represents a category in the CostManager application.
 * <p>
 * A category is identified by its unique ID and has a name.
 * </p>
 */
public class Category {
	
	private int id;
	
	private String name;
	
	/**
	 * Constructor for Category with a name.
	 *
	 * @param name The name of the category.
	 */
	public Category(String name) {
		this.id = -1;
		setName(name);
	}
	
	/**
	 * Constructor for Category with an ID and a name.
	 *
	 * @param id The ID of the category.
	 * @param name The name of the category.
	 */
	public Category(int id, String name) {
		setId(id);
		setName(name);
	}
	
	/**
	 * Gets the ID of the category.
	 *
	 * @return The ID of the category.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the category.
	 *
	 * @param id The ID of the category.
	 * @throws IllegalArgumentException if the ID is negative.
	 */
	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("ID cannot be negative");
		this.id = id;
	}
	
	/**
	 * Gets the name of the category.
	 *
	 * @return The name of the category.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the category.
	 *
	 * @param name The name of the category.
	 * @throws IllegalArgumentException if the name is null or empty.
	 */
	public void setName(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}
	
	/**
	 * Returns a string representation of the category.
	 *
	 * @return A string representation of the category.
	 */
	@Override
	public String toString() {
		return "Category{" +
				       "id=" + id +
				       ", name='" + name + '\'' +
				       '}';
	}
}
