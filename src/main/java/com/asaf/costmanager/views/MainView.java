package com.asaf.costmanager.views;

import javax.swing.*;

public class MainView {
	private JPanel panel1;
	private JButton categoriesButton;
	private JButton reportsButton;
	private JButton addACostButton;
	
	public MainView() {
		JFrame frame = new JFrame("Cost Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 570);
		frame.setMinimumSize(frame.getSize());
		frame.setMaximumSize(frame.getSize());
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this.panel1);
	}
	
	private void createUIComponents() {
		// TODO: place custom component creation code here
	}
}
