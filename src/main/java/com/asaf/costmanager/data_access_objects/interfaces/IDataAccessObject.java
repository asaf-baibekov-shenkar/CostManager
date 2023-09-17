package com.asaf.costmanager.data_access_objects.interfaces;

import java.util.List;

/**
 * Interface for Data Access Objects.
 * <p>
 * This interface specifies the methods that all Data Access Objects must implement.
 * </p>
 * @param <Element> The type of the objects that will be managed by the DAO.
 */
public interface IDataAccessObject<Element> {
	
	/**
	 * Create a new element.
	 *
	 * @param element The element to be created.
	 */
	void create(Element element);
	
	/**
	 * Read an element by its id.
	 *
	 * @param id The id of the element to be read.
	 * @return The element with the specified id.
	 */
	Element read(int id);
	
	/**
	 * Read all elements.
	 *
	 * @return A list of all elements.
	 */
	List<Element> readAll();
	
	/**
	 * Update an existing element.
	 *
	 * @param id The id of the element to be updated.
	 * @param category The updated element.
	 */
	void update(int id, Element category);
	
	/**
	 * Delete an element by its id.
	 *
	 * @param id The id of the element to be deleted.
	 * @return The deleted element.
	 */
	Element delete(int id);
}
