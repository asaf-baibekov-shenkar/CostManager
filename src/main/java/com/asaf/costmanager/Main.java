package com.asaf.costmanager;

import com.asaf.costmanager.views.MainView;

import javax.swing.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(MainView::new);
	}
}