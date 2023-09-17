package com.asaf.costmanager.views.table_cell_renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CenterTableCellRenderer extends DefaultTableCellRenderer {
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
		this.setHorizontalAlignment(JLabel.CENTER);
		if (isSelected) {
			this.setBackground(table.getBackground());
			this.setForeground(Color.BLACK);
		}
		return this;
	}
}
