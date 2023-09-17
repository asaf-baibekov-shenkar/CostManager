package com.asaf.costmanager;

import com.asaf.costmanager.coordinator.MainCoordinator;

/**
 * Main class - Entry point of the application.
 */
public class Main {
	
	/**
	 * Main method which is the entry point of the application.
	 *
	 * @param args command-line arguments passed to the application.
	 */
	public static void main(String[] args) {
		new MainCoordinator().start();
	}
}
