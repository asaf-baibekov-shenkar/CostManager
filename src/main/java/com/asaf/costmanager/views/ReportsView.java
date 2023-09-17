package com.asaf.costmanager.views;

import com.asaf.costmanager.view_models.ReportsViewModel;

import javax.swing.*;
import java.util.Arrays;

public class ReportsView {
	
	private final ReportsViewModel viewModel;
	
	private JPanel panel;
	private JTextField yearTextField;
	private JTextField monthTextField;
	private JTextField dayTextField;
	private JButton submitButton;
	private JTable reportsTable;
	
	public ReportsView(ReportsViewModel viewModel) {
		this.viewModel = viewModel;
		
		this.setupViews();
		this.setupListeners();
	}
	
	public JPanel getPanel() {
		return panel;
	}
	
	private void setupViews() {
		Arrays.asList(this.yearTextField, this.yearTextField, this.dayTextField)
			.forEach((textField) -> textField.setTransferHandler(null));
	}
	
	private void setupListeners() {
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
}
