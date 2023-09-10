package com.asaf.costmanager.coordinator;

import java.util.List;

public interface Coordinator {
	List<Coordinator> getCoordinators();
	void start();
}
