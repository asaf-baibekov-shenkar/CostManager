package com.asaf.costmanager.view_models;

import com.asaf.costmanager.data_access_objects.interfaces.IDataAccessObject;
import com.asaf.costmanager.models.Cost;

public class ReportsViewModel {
	
	private final IDataAccessObject<Cost> costsDAO;
	
	public ReportsViewModel(IDataAccessObject<Cost> costsDAO) {
		this.costsDAO = costsDAO;
	}
}
