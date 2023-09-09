package com.asaf.costmanager.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainView implements ActionListener {
	
	private ReportsView reportsView;
	private CostsView costsView;
	private CategoriesView categoriesView;
	
	private JPanel panel1;
	private JPanel topView;
	private JPanel contentView;
	
	private JButton categoriesButton;
	private JButton reportsButton;
	private JButton addCostButton;
	
	public MainView() {
		JFrame frame = new JFrame("Cost Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 570);
		frame.setMinimumSize(frame.getSize());
		frame.setMaximumSize(frame.getSize());
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this.panel1);
		
		this.setupViews();
		this.setupListeners();
	}
	
	private void setupViews() {
		this.reportsView = new ReportsView();
		this.costsView = new CostsView();
		this.categoriesView = new CategoriesView();
	}
	
	private void setupListeners() {
		this.categoriesButton.addActionListener(this);
		this.reportsButton.addActionListener(this);
		this.addCostButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.categoriesButton)
			System.out.println("Categories button clicked");
		else if (e.getSource() == this.reportsButton)
			System.out.println("Reports button clicked");
		else if (e.getSource() == this.addCostButton)
			System.out.println("Add a cost button clicked");
		else
			System.out.println("Unknown button clicked");
	}
}
