package com.asaf.costmanager.views;

import com.asaf.costmanager.models.Cost;
import com.asaf.costmanager.view_models.ReportsViewModel;
import com.asaf.costmanager.views.table_cell_renderers.CenterTableCellRenderer;
import com.asaf.costmanager.views.table_cell_renderers.HeaderTableCellRenderer;
import io.reactivex.rxjava3.annotations.Nullable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Reports section of the application.
 */
public class ReportsView {
	
	private final ReportsViewModel viewModel;
	
	private final CompositeDisposable compositeDisposable;
	
	private JPanel panel;
	private JTextField yearTextField;
	private JTextField monthTextField;
	private JTextField dayTextField;
	private JButton submitButton;
	private JTable reportsTable;
	
	/**
	 * Constructor for ReportsView.
	 *
	 * @param viewModel the view model for the reports view.
	 */
	public ReportsView(ReportsViewModel viewModel) {
		this.viewModel = viewModel;
		this.compositeDisposable = new CompositeDisposable();
		
		this.setupViews();
		this.setupListeners();
		this.setupRx();
	}
	
	/**
	 * Gets the main panel of the reports view.
	 *
	 * @return the main panel.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Sets up the views for the reports view.
	 */
	private void setupViews() {
		Arrays.asList(this.yearTextField, this.monthTextField, this.dayTextField)
			.forEach((textField) -> {
				textField.setTransferHandler(null);
				textField.setHorizontalAlignment(JTextField.CENTER);
			});
		
	}
	
	/**
	 * Sets up the action listeners for the components in the reports view.
	 */
	private void setupListeners() {
		this.submitButton.addActionListener(e -> {
			@Nullable Integer year = this.yearTextField.getText().isEmpty() ? null : Integer.parseInt(this.yearTextField.getText());
			@Nullable Integer month = this.monthTextField.getText().isEmpty() ? null : Integer.parseInt(this.monthTextField.getText());
			@Nullable Integer day = this.dayTextField.getText().isEmpty() ? null : Integer.parseInt(this.dayTextField.getText());
			this.viewModel.updateCostsReport(year, month, day);
		});
		this.dayTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				ReportsView.this.acceptNumbersOnly(evt, 31, 2);
			}
		});
		this.monthTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				ReportsView.this.acceptNumbersOnly(evt, 12, 2);
			}
		});
		this.yearTextField.addKeyListener(new java.awt.event.KeyAdapter() {
			@Override
			public void keyTyped(java.awt.event.KeyEvent evt) {
				ReportsView.this.acceptNumbersOnly(evt, 2030, 4);
			}
		});
	}
	
	/**
	 * Limits the input of the text fields to only numbers, backspace, and delete,
	 * with a maximum value and maximum length.
	 *
	 * @param evt       the key event.
	 * @param maxValue  the maximum value for the text field.
	 * @param maxLength the maximum length for the text field.
	 */
	private void acceptNumbersOnly(java.awt.event.KeyEvent evt, int maxValue, int maxLength) {
		char c = evt.getKeyChar();
		boolean isNumber = (c >= '0') && (c <= '9');
		boolean isBackspace = (c == java.awt.event.KeyEvent.VK_BACK_SPACE);
		boolean isDelete = (c == java.awt.event.KeyEvent.VK_DELETE);
		boolean isRemoveCharacter = isBackspace || isDelete;
		JTextField textField = (JTextField) evt.getSource();
		if (!isNumber && !isRemoveCharacter)
			evt.consume();
		else if (textField.getText().length() >= maxLength && !isRemoveCharacter)
			evt.consume();
		else if (!isRemoveCharacter && Integer.parseInt(textField.getText() + c) > maxValue) {
			evt.consume();
			textField.setText(String.valueOf(maxValue));
		}
	}
	
	/**
	 * Sets up the reactive stream for the reports view.
	 */
	private void setupRx() {
		this.compositeDisposable.add(
			this.viewModel
				.getCostsReportsObservable()
				.subscribe(this::refreshTable)
		);
	}
	
	/**
	 * Refreshes the reports table with the given costs.
	 *
	 * @param costs the list of costs to be displayed.
	 */
	private void refreshTable(List<Cost> costs) {
		String[] columnNames = {"ID", "Date", "Category", "Description", "Cost"};
		Object[][] data = costs
			.stream()
			.map(cost -> new Object[] {
				cost.getId(),
				cost.getDate().toString(),
				cost.getCategory().getName(),
				cost.getDescription(),
				cost.getTotalCost() + cost.getCurrency().getSymbol()
			})
			.toArray(Object[][]::new);
		this.reportsTable.setModel(new DefaultTableModel(data, columnNames));
		
		for (int i = 0; i < this.reportsTable.getModel().getColumnCount(); i++)
			this.reportsTable.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderTableCellRenderer());
		for (int i = 0; i < this.reportsTable.getColumnCount(); i++)
			this.reportsTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
		
		this.reportsTable.setGridColor(Color.BLACK);
		this.reportsTable.setShowHorizontalLines(true);
		this.reportsTable.setShowVerticalLines(true);
	}
}
