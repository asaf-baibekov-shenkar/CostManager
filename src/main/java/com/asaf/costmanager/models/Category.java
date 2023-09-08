package com.asaf.costmanager.models;

public class Category {
	
	private int id;
	
	private String name;
	
	public Category(String name) {
		this.id = -1;
		setName(name);
	}
	
	public Category(int id, String name) {
		setId(id);
		setName(name);
	}
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("ID cannot be negative");
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}
	
	@Override
	public String toString() {
		return "Category{" +
				       "id=" + id +
				       ", name='" + name + '\'' +
				       '}';
	}
}
