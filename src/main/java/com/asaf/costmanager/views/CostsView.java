package com.asaf.costmanager.views;

import com.asaf.costmanager.view_models.CostsViewModel;

import javax.swing.*;

public class CostsView {
	
	private final CostsViewModel viewModel;
	
	private JPanel panel;
	private JButton saveButton;
	private JComboBox categoriesComboBox;
	private JComboBox currencyComboBox;
	private JTextField descriptionTextField;
	private JTextField amountTextField;
	
	public CostsView(CostsViewModel viewModel) {
		this.viewModel = viewModel;
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
