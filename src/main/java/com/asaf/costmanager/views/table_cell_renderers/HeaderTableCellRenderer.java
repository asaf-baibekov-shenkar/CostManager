package com.asaf.costmanager.views.table_cell_renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class HeaderTableCellRenderer extends DefaultTableCellRenderer {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, false, hasFocus, row, column);
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setBackground(Color.BLACK);
		this.setForeground(Color.WHITE);
		return this;
	}
}
