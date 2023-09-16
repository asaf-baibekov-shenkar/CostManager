package com.asaf.costmanager.view_models;

import com.asaf.costmanager.services.database_connection_service.interfaces.IDatabaseConnectionService;

public class CostsViewModel {
	
	private final IDatabaseConnectionService databaseConnectionService;
	
	public CostsViewModel(IDatabaseConnectionService databaseConnectionService) {
		this.databaseConnectionService = databaseConnectionService;
	}
	
}
