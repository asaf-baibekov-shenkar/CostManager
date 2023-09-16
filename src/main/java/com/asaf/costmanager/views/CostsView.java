package com.asaf.costmanager.views;

import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.view_models.CostsViewModel;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class CostsView {
	
	private final CostsViewModel viewModel;
	
	private JPanel panel;
	private JButton saveButton;
	private JComboBox<String> categoriesComboBox;
	private JComboBox<String> currencyComboBox;
	private JTextField descriptionTextField;
	private JTextField amountTextField;
	
	public CostsView(CostsViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.setupViews();
	}
	
	private void setupViews() {
		Arrays.asList(this.categoriesComboBox, this.currencyComboBox)
			.forEach((comboBox) -> {
				Dimension size = comboBox.getPreferredSize();
				comboBox.setMaximumSize(size);
				comboBox.setMinimumSize(size);
				comboBox.setPreferredSize(size);
			});
		
		List<Category> categories = this.viewModel.getCategories();
		DefaultComboBoxModel<String> categoriesModel = new DefaultComboBoxModel<>();
		for (Category category : categories)
			categoriesModel.addElement(category.getName());
		this.categoriesComboBox.setModel(categoriesModel);
		
		List<Currency> currencies = this.viewModel.getCurrencies();
		DefaultComboBoxModel<String> currenciesModel = new DefaultComboBoxModel<>();
		for (Currency currency : currencies)
			currenciesModel.addElement(currency.getSymbol() + '-' + currency.getName());
		this.currencyComboBox.setModel(currenciesModel);
	}
	
	public JPanel getPanel() {
		return panel;
	}
}
