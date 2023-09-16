package com.asaf.costmanager.models;

public class Currency {
	
	private int id;
	
	private String name;
	
	private String symbol;
	
	public Currency(String name, String symbol) {
		this.id = -1;
		setName(name);
		setSymbol(symbol);
	}
	
	public Currency(int id, String name, String symbol) {
		setId(id);
		setName(name);
		setSymbol(symbol);
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
	
	public String getSymbol() {
		return symbol;
	}
	
	public void setSymbol(String symbol) {
		if (symbol == null || symbol.trim().isEmpty())
			throw new IllegalArgumentException("Symbol cannot be null or empty");
		this.symbol = symbol;
	}
	
	@Override
	public String toString() {
		return this.name + " (" + this.symbol + ")";
	}
}
