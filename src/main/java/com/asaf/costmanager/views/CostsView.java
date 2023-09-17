package com.asaf.costmanager.views;

import com.asaf.costmanager.exceptions.CostManagerException;
import com.asaf.costmanager.models.Currency;
import com.asaf.costmanager.view_models.CostsViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

/**
 * View class for managing the costs in the application.
 */
public class CostsView implements ActionListener {
	
	private final CostsViewModel viewModel;
	
	private final CompositeDisposable compositeDisposable;
	
	private JPanel panel;
	private JButton saveButton;
	private JComboBox<String> categoriesComboBox;
	private JComboBox<String> currencyComboBox;
	private JTextField descriptionTextField;
	private JTextField amountTextField;
	
	/**
	 * Constructor for the CostsView.
	 *
	 * @param viewModel the view model for the costs view.
	 */
	public CostsView(CostsViewModel viewModel) {
		this.viewModel = viewModel;
		this.compositeDisposable = new CompositeDisposable();
		
		this.setupViews();
		this.setupListeners();
		this.setupRx();
	}
	
	/**
	 * Sets up the views for the costs view.
	 */
	private void setupViews() {
		Arrays.asList(this.categoriesComboBox, this.currencyComboBox)
			.forEach((comboBox) -> {
				Dimension size = comboBox.getPreferredSize();
				comboBox.setMaximumSize(size);
				comboBox.setMinimumSize(size);
				comboBox.setPreferredSize(size);
			});
		
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
	
	/**
	 * Sets up the action listeners for the components in the costs view.
	 */
	private void setupListeners() {
		this.saveButton.addActionListener(this);
	}
	
	/**
	 * Sets up the reactive streams for the costs view.
	 */
	private void setupRx() {
		this.compositeDisposable.add(
			this.viewModel
				.getCategoriesObservable()
				.subscribe((databaseEvent) -> {
					DefaultComboBoxModel<String> categoriesModel = new DefaultComboBoxModel<>();
					int categoriesCount = this.viewModel.getCategoriesCount();
					for (int i = 0; i < categoriesCount; i++)
						categoriesModel.addElement(this.viewModel.getCategoryAt(i).getName());
					this.categoriesComboBox.setModel(categoriesModel);
				})
		);
	}
	
	/**
	 * Returns the main panel for the costs view.
	 *
	 * @return the main panel for the costs view.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Handles the actions for the components in the costs view.
	 *
	 * @param e the action event.
	 */
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
