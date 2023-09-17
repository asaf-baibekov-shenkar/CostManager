package com.asaf.costmanager.models;

/**
 * Represents a currency in the CostManager application.
 * <p>
 * A currency is identified by its unique ID and has associated name and symbol.
 * </p>
 */
public class Currency {
	
	private int id;
	
	private String name;
	
	private String symbol;
	
	/**
	 * Constructor for Currency with name and symbol.
	 *
	 * @param name The name of the currency.
	 * @param symbol The symbol of the currency.
	 */
	public Currency(String name, String symbol) {
		this.id = -1;
		setName(name);
		setSymbol(symbol);
	}
	
	/**
	 * Constructor for Currency with an ID, name, and symbol.
	 *
	 * @param id The ID of the currency.
	 * @param name The name of the currency.
	 * @param symbol The symbol of the currency.
	 */
	public Currency(int id, String name, String symbol) {
		setId(id);
		setName(name);
		setSymbol(symbol);
	}
	
	/**
	 * Gets the ID of the currency.
	 *
	 * @return The ID of the currency.
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * Sets the ID of the currency.
	 *
	 * @param id The ID of the currency.
	 * @throws IllegalArgumentException if the ID is negative.
	 */
	public void setId(int id) {
		if (id < 0)
			throw new IllegalArgumentException("ID cannot be negative");
		this.id = id;
	}
	
	/**
	 * Gets the name of the currency.
	 *
	 * @return The name of the currency.
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Sets the name of the currency.
	 *
	 * @param name The name of the currency.
	 * @throws IllegalArgumentException if the name is null or empty.
	 */
	public void setName(String name) {
		if (name == null || name.trim().isEmpty())
			throw new IllegalArgumentException("Name cannot be null or empty");
		this.name = name;
	}
	
	/**
	 * Gets the symbol of the currency.
	 *
	 * @return The symbol of the currency.
	 */
	public String getSymbol() {
		return symbol;
	}
	
	/**
	 * Sets the symbol of the currency.
	 *
	 * @param symbol The symbol of the currency.
	 * @throws IllegalArgumentException if the symbol is null or empty.
	 */
	public void setSymbol(String symbol) {
		if (symbol == null || symbol.trim().isEmpty())
			throw new IllegalArgumentException("Symbol cannot be null or empty");
		this.symbol = symbol;
	}
	
	/**
	 * Returns a string representation of the currency.
	 *
	 * @return A string representation of the currency.
	 */
	@Override
	public String toString() {
		return "Currency{" +
				       "id=" + id +
				       ", name='" + name + '\'' +
				       ", symbol='" + symbol + '\'' +
				       '}';
	}
}
