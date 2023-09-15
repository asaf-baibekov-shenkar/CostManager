package com.asaf.costmanager.views;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CategoriesView implements ActionListener {
	
	private JPanel panel;
	private JTextField categoryTextField;
	private JButton saveButton;
	private JTable categoriesTable;
	private JButton deleteCategoriesButton;
	
	public CategoriesView() {
		this.setupListeners();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	private void setupListeners() {
		this.saveButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
	
	}
}
