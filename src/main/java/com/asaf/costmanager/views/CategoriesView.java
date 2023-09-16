package com.asaf.costmanager.views;

import com.asaf.costmanager.models.Category;
import com.asaf.costmanager.view_models.CategoriesViewModel;
import io.reactivex.rxjava3.disposables.CompositeDisposable;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CategoriesView implements ActionListener {
	
	public CategoriesViewModel viewModel;
	
	private final CompositeDisposable compositeDisposable;
	
	private JPanel panel;
	private JTextField categoryTextField;
	private JButton saveButton;
	private JTable categoriesTable;
	private JButton deleteCategoriesButton;
	
	public CategoriesView(CategoriesViewModel viewModel) {
		this.viewModel = viewModel;
		this.compositeDisposable = new CompositeDisposable();
		
		this.setupCategoriesTable();
		this.setupListeners();
		this.setupRx();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	private void setupCategoriesTable() {
		DefaultTableModel categoriesTableModel = this.createTableModel();
		List<Category> categories = this.viewModel.getAllCategories();
		for (Category category : categories)
			categoriesTableModel.addRow(new Object[] { false, category.getId(), category.getName() });
		this.categoriesTable.setModel(categoriesTableModel);
		
		this.categoriesTable.getColumnModel().getColumn(0).setCellRenderer(this.createCheckBoxRenderer());
		for (int i = 1; i < this.categoriesTable.getColumnCount(); i++)
			this.categoriesTable.getColumnModel().getColumn(i).setCellRenderer(this.createCenterRenderer());
		for (int i = 0; i < this.categoriesTable.getModel().getColumnCount(); i++)
			this.categoriesTable.getColumnModel().getColumn(i).setHeaderRenderer(this.createHeaderRenderer());
		
		this.categoriesTable.setGridColor(Color.BLACK);
		this.categoriesTable.setShowHorizontalLines(true);
		this.categoriesTable.setShowVerticalLines(true);
	}
	
	private void setupListeners() {
		this.saveButton.addActionListener(this);
		this.deleteCategoriesButton.addActionListener(this);
	}
	
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
	
	private DefaultTableCellRenderer createCheckBoxRenderer() {
		return new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				JCheckBox checkBox = new JCheckBox();
				checkBox.setSelected((Boolean) value);
				checkBox.setHorizontalAlignment(JLabel.CENTER);
				checkBox.setBackground(Color.WHITE);
				return checkBox;
			}
		};
	}
	
	private DefaultTableCellRenderer createCenterRenderer() {
		return new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
				setHorizontalAlignment(JLabel.CENTER);
				return this;
			}
		};
	}
	
	private DefaultTableCellRenderer createHeaderRenderer() {
		return new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
				super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
				setHorizontalAlignment(JLabel.CENTER);
				setBackground(Color.BLACK);
				setForeground(Color.WHITE);
				return this;
			}
		};
	}
	
	private void scrollTableToBottom() {
		int lastRow = this.categoriesTable.getRowCount() - 1;
		Rectangle rectangle = this.categoriesTable.getCellRect(lastRow, 0, true);
		this.categoriesTable.scrollRectToVisible(rectangle);
	}
	
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
