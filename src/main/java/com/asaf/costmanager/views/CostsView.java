package com.asaf.costmanager.views;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.view_models.CostsViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class CostsView implements ActionListener {
	
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
		this.setupListeners();
	}
	
	private void setupViews() {
		Arrays.asList(this.categoriesComboBox, this.currencyComboBox)
			.forEach((comboBox) -> {
				Dimension size = comboBox.getPreferredSize();
				comboBox.setMaximumSize(size);
				comboBox.setMinimumSize(size);
				comboBox.setPreferredSize(size);
			});
		
		DefaultComboBoxModel<String> categoriesModel = new DefaultComboBoxModel<>();
		int categoriesCount = this.viewModel.getCategoriesCount();
		for (int i = 0; i < categoriesCount; i++)
			categoriesModel.addElement(this.viewModel.getCategoryAt(i).getName());
			
		this.categoriesComboBox.setModel(categoriesModel);
		
		DefaultComboBoxModel<String> currenciesModel = new DefaultComboBoxModel<>();
		int currenciesCount = this.viewModel.getCurrenciesCount();
		for (int i = 0; i < currenciesCount; i++) {
			Currency currency = this.viewModel.getCurrencyAt(i);
			currenciesModel.addElement(currency.getSymbol() + '-' + currency.getName());
		}
		this.currencyComboBox.setModel(currenciesModel);
		
		this.amountTextField.setTransferHandler(null);
		this.amountTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			public void keyTyped(java.awt.event.KeyEvent evt) {
				char c = evt.getKeyChar();
				boolean isNumber = (c >= '0') && (c <= '9');
				boolean isDot = (c == '.');
				boolean isBackspace = (c == java.awt.event.KeyEvent.VK_BACK_SPACE);
				boolean isDelete = (c == java.awt.event.KeyEvent.VK_DELETE);
				if (!isNumber && !isDot && !isBackspace && !isDelete)
					evt.consume();
			}
		});
	}
	
	private void setupListeners() {
		this.saveButton.addActionListener(this);
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.saveButton) {
			int categoryIndex = this.categoriesComboBox.getSelectedIndex();
			int currencyIndex = this.currencyComboBox.getSelectedIndex();
			double amount = Double.parseDouble(this.amountTextField.getText());
			String description = this.descriptionTextField.getText();
			try {
				this.viewModel.addCost(categoryIndex, currencyIndex, amount, description);
			} catch (CostManagerException ex) {
				throw new RuntimeException(ex);
			}
			this.categoriesComboBox.setSelectedIndex(0);
			this.currencyComboBox.setSelectedIndex(0);
			this.descriptionTextField.setText("");
			this.amountTextField.setText("");
		}
		
	}
}
