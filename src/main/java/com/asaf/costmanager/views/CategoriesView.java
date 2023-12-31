package com.asaf.costmanager.views;

import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.view_models.CategoriesViewModel;
import com.asaf.costmanager.views.table_cell_renderers.CenterTableCellRenderer;
import com.asaf.costmanager.views.table_cell_renderers.CheckboxTableCellRenderer;
import com.asaf.costmanager.views.table_cell_renderers.HeaderTableCellRenderer;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * View class for managing the categories in the application.
 */
public class CategoriesView implements ActionListener {
	
	public CategoriesViewModel viewModel;
	
	private final CompositeDisposable compositeDisposable;
	
	private JPanel panel;
	private JTextField categoryTextField;
	private JButton saveButton;
	private JTable categoriesTable;
	private JButton deleteCategoriesButton;
	
	/**
	 * Constructor for the CategoriesView.
	 *
	 * @param viewModel the view model for the categories view.
	 */
	public CategoriesView(CategoriesViewModel viewModel) {
		this.viewModel = viewModel;
		this.compositeDisposable = new CompositeDisposable();
		
		this.setupCategoriesTable();
		this.setupListeners();
		this.setupRx();
	}
	
	/**
	 * Returns the main panel for the categories view.
	 *
	 * @return the main panel for the categories view.
	 */
	public JPanel getPanel() {
		return panel;
	}
	
	/**
	 * Sets up the categories table.
	 */
	private void setupCategoriesTable() {
		DefaultTableModel categoriesTableModel = this.createTableModel();
		List<Category> categories = this.viewModel.getAllCategories();
		for (Category category : categories)
			categoriesTableModel.addRow(new Object[] { false, category.getId(), category.getName() });
		this.categoriesTable.setModel(categoriesTableModel);
		
		this.categoriesTable.getColumnModel().getColumn(0).setCellRenderer(new CheckboxTableCellRenderer());
		for (int i = 1; i < this.categoriesTable.getColumnCount(); i++)
			this.categoriesTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
		for (int i = 0; i < this.categoriesTable.getModel().getColumnCount(); i++)
			this.categoriesTable.getColumnModel().getColumn(i).setHeaderRenderer(new HeaderTableCellRenderer());
		
		this.categoriesTable.setGridColor(Color.BLACK);
		this.categoriesTable.setShowHorizontalLines(true);
		this.categoriesTable.setShowVerticalLines(true);
	}
	
	/**
	 * Sets up the action listeners for the components in the categories view.
	 */
	private void setupListeners() {
		this.saveButton.addActionListener(this);
		this.deleteCategoriesButton.addActionListener(this);
	}
	
	/**
	 * Sets up the reactive streams for the categories view.
	 */
	private void setupRx() {
		this.compositeDisposable.add(
			this.viewModel
				.updateCategoryObservable()
				.subscribe((databaseEvent) -> {
					switch (databaseEvent) {
						case RECORD_SAVED -> {
							this.setupCategoriesTable();
							this.categoryTextField.setText("");
							this.scrollTableToBottom();
						}
						case RECORD_DELETED -> this.setupCategoriesTable();
					}
				})
		);
	}
	
	/**
	 * Creates a table model for the categories table.
	 *
	 * @return a table model for the categories table.
	 */
	private DefaultTableModel createTableModel() {
		return new DefaultTableModel(new String[] {"Select", "ID", "Name"}, 0) {
			@Override
			public Class<?> getColumnClass(int columnIndex) {
				return switch (columnIndex) {
					case 0 -> Boolean.class;
					case 1 -> Integer.class;
					case 2 -> String.class;
					default -> super.getColumnClass(columnIndex);
				};
			}
			
			@Override
			public boolean isCellEditable(int row, int column) {
				return column == 0;
			}
		};
	}
	
	/**
	 * Scrolls the categories table to the bottom.
	 */
	private void scrollTableToBottom() {
		int lastRow = this.categoriesTable.getRowCount() - 1;
		Rectangle rectangle = this.categoriesTable.getCellRect(lastRow, 0, true);
		this.categoriesTable.scrollRectToVisible(rectangle);
	}
	
	/**
	 * Handles the actions for the components in the categories view.
	 *
	 * @param e the action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.saveButton)
			this.viewModel.saveCategory(this.categoryTextField.getText());
		else if (e.getSource() == this.deleteCategoriesButton) {
			DefaultTableModel tableModel = (DefaultTableModel) this.categoriesTable.getModel();
			for (int i = 0; i < tableModel.getRowCount(); i++)
				if ((Boolean) tableModel.getValueAt(i, 0))
					this.viewModel.deleteCategory((Integer) tableModel.getValueAt(i, 1));
		}
	}
}
