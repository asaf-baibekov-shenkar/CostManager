package com.asaf.costmanager.views.table_cell_renderers;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;

public class CheckboxTableCellRenderer extends DefaultTableCellRenderer {

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
		JCheckBox checkBox = new JCheckBox();
		checkBox.setSelected((Boolean) value);
		checkBox.setHorizontalAlignment(JLabel.CENTER);
		checkBox.setBackground(Color.WHITE);
		return checkBox;
	}
}
