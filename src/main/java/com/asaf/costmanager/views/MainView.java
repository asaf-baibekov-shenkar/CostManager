package com.asaf.costmanager.views;

import com.asaf.costmanager.view_models.CategoriesViewModel;
import com.asaf.costmanager.view_models.CostsViewModel;
import com.asaf.costmanager.view_models.MainViewModel;
import com.asaf.costmanager.view_models.ReportsViewModel;

import javax.swing.*;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Locale;

/**
 * Main user interface of the application.
 */
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
	
	/**
	 * Constructor for MainView.
	 *
	 * @param viewModel the view model for the main view.
	 */
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
	
	/**
	 * Sets up the views for the main view.
	 */
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
	
	/**
	 * Sets up the action listeners for the components in the main view.
	 */
	private void setupListeners() {
		this.categoriesButton.addActionListener(this);
		this.reportsButton.addActionListener(this);
		this.costsButton.addActionListener(this);
	}
	
	/**
	 * Handles the actions for the components in the main view.
	 *
	 * @param e the action event.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.reportsButton)
			this.viewModel.reportNavigationSelected();
		else if (e.getSource() == this.costsButton)
			this.viewModel.costsNavigationSelected();
		else if (e.getSource() == this.categoriesButton)
			this.viewModel.categoriesNavigationSelected();
	}
	
	/**
	 * Activates a button and sets the color based on the navigation type.
	 *
	 * @param navigationType the navigation type.
	 */
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
	
	/**
	 * Activates the reports view.
	 *
	 * @param viewModel the view model for the reports view.
	 */
	public void activateReportsView(ReportsViewModel viewModel) {
		this.activateButton(MainViewModel.NavigationType.Reports);
		this.contentView.removeAll();
		if (this.reportsView == null)
			this.reportsView = new ReportsView(viewModel);
		this.contentView.add(this.reportsView.getPanel());
		this.contentView.revalidate();
		this.contentView.repaint();
	}
	
	/**
	 * Activates the costs view.
	 *
	 * @param viewModel the view model for the costs view.
	 */
	public void activateCostsView(CostsViewModel viewModel) {
		this.activateButton(MainViewModel.NavigationType.Costs);
		this.contentView.removeAll();
		if (this.costsView == null)
			this.costsView = new CostsView(viewModel);
		this.contentView.add(this.costsView.getPanel());
		this.contentView.revalidate();
		this.contentView.repaint();
	}
	
	/**
	 * Activates the categories view.
	 *
	 * @param viewModel the view model for the categories view.
	 */
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
