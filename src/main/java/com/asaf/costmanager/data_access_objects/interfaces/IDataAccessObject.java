package com.asaf.costmanager.data_access_objects.interfaces;

import java.util.List;

public interface IDataAccessObject<Element> {
	Element create(Element element);
	Element read(int id);
	List<Element> readAll();
	void update(int id, Element category);
	Element delete(int id);
}
