package com.asaf.costmanager.coordinator;

import java.util.List;

/**
 * Interface for the Coordinator class.
 * <p>
 * Provides methods to start the Coordinator and to get a list of Coordinators.
 * </p>
 */
public interface Coordinator {
	
	/**
	 * Gets a list of Coordinators.
	 *
	 * @return List of Coordinators.
	 */
	List<Coordinator> getCoordinators();
	
	/**
	 * Starts the Coordinator.
	 */
	void start();
}
