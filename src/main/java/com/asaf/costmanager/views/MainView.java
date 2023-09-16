package com.asaf.costmanager.views;

import com.asaf.costmanager.view_models.CategoriesViewModel;
import com.asaf.costmanager.view_models.CostsViewModel;
import com.asaf.costmanager.view_models.MainViewModel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;

public class MainView implements ActionListener {
	
	private final MainViewModel viewModel;
	
	private ReportsView reportsView;
	private CostsView costsView;
	private CategoriesView categoriesView;
	
	private JPanel panel1;
	private JPanel contentView;
	
	private JButton categoriesButton;
	private JButton reportsButton;
	private JButton costsButton;
	
	public MainView(MainViewModel viewModel) {
		this.viewModel = viewModel;
		
		JFrame frame = new JFrame("Cost Manager");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 570);
		frame.setMinimumSize(frame.getSize());
		frame.setMaximumSize(frame.getSize());
		frame.setResizable(false);
		frame.setVisible(true);
		frame.add(this.panel1);
		
		Locale locale = viewModel.getLocale();
		if (locale != null)
			frame.applyComponentOrientation(ComponentOrientation.getOrientation(locale));
		
		this.setupViews();
		this.setupListeners();
	}
	
	private void setupViews() {
		UIDefaults defaults = UIManager.getLookAndFeelDefaults();
		defaults.put("Button.select", new Color(167, 168, 171));
		Arrays.asList(this.categoriesButton, this.reportsButton, this.costsButton)
			.forEach((view) -> {
				view.setUI(new MetalButtonUI());
				view.setBorderPainted(true);
				view.setFocusPainted(false);
				view.setFont(new Font(view.getFont().getName(), view.getFont().getStyle(), 18));
			});
	}
	
	private void setupListeners() {
		this.categoriesButton.addActionListener(this);
		this.reportsButton.addActionListener(this);
		this.costsButton.addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.reportsButton)
			this.viewModel.reportNavigationSelected();
		else if (e.getSource() == this.costsButton)
			this.viewModel.costsNavigationSelected();
		else if (e.getSource() == this.categoriesButton)
			this.viewModel.categoriesNavigationSelected();
	}
	
	private void activateButton(MainViewModel.NavigationType navigationType) {
		Color selectedForegroundColor = Color.BLACK;
		Color unselectedForegroundColor = new Color(255, 255, 255);
		
		Color selectedBackgroundColor = new Color(255, 255, 255);
		Color unselectedBackgroundColor = new Color(128, 128, 128);
		
		Arrays.asList(this.reportsButton, this.costsButton, this.categoriesButton)
			.forEach((view) -> {
				view.setBackground(unselectedBackgroundColor);
				view.setForeground(unselectedForegroundColor);
			});
		
		switch (navigationType) {
			case Reports -> {
				this.reportsButton.setBackground(selectedBackgroundColor);
				this.reportsButton.setForeground(selectedForegroundColor);
			}
			case Costs -> {
				this.costsButton.setBackground(selectedBackgroundColor);
				this.costsButton.setForeground(selectedForegroundColor);
			}
			case Categories -> {
				this.categoriesButton.setBackground(selectedBackgroundColor);
				this.categoriesButton.setForeground(selectedForegroundColor);
			}
		}
	}
	
	public void activateReportsView() {
		this.activateButton(MainViewModel.NavigationType.Reports);
		this.contentView.removeAll();
		if (this.reportsView == null)
			this.reportsView = new ReportsView();
		this.contentView.add(this.reportsView.getPanel());
		this.contentView.revalidate();
		this.contentView.repaint();
	}
	
	public void activateCostsView(CostsViewModel viewModel) {
		this.activateButton(MainViewModel.NavigationType.Costs);
		this.contentView.removeAll();
		if (this.costsView == null)
			this.costsView = new CostsView(viewModel);
		this.contentView.add(this.costsView.getPanel());
		this.contentView.revalidate();
		this.contentView.repaint();
	}
	
	public void activateCategoriesView(CategoriesViewModel viewModel) {
		this.activateButton(MainViewModel.NavigationType.Categories);
		this.contentView.removeAll();
		if (this.categoriesView == null)
			this.categoriesView = new CategoriesView(viewModel);
		this.contentView.add(this.categoriesView.getPanel());
		this.contentView.revalidate();
		this.contentView.repaint();
	}
}
