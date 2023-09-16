package com.asaf.costmanager.views;

import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.view_models.CostsViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CostsView {
	
	private final CostsViewModel viewModel;
	
	private JPanel panel;
	private JButton saveButton;
	private JComboBox<String> categoriesComboBox;
	private JComboBox currencyComboBox;
	private JTextField descriptionTextField;
	private JTextField amountTextField;
	
	public CostsView(CostsViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.setupViews();
	}
	
	private void setupViews() {
		Dimension size = this.categoriesComboBox.getPreferredSize();
		this.categoriesComboBox.setMaximumSize(size);
		this.categoriesComboBox.setMinimumSize(size);
		this.categoriesComboBox.setPreferredSize(size);
		
		List<Category> categories = this.viewModel.getCategories();
		DefaultComboBoxModel<String> categoriesModel = new DefaultComboBoxModel<>();
		for (Category category : categories)
			categoriesModel.addElement(category.getName());
		this.categoriesComboBox.setModel(categoriesModel);
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
