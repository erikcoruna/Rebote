package gui;
import java.awt.Color;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;


public class GameTableRenderer implements TableCellRenderer {
	private static Color colorAction = Color.WHITE;
	
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JPanel panel = new JPanel();
		JLabel label = new JLabel();
		
		if (!(value == null)) {
			if (column == 4) {
				Point point = (Point) value;
				label.setText("x=" + point.x + " y=" + point.y);
				paintLabel(panel, column, value);
			} else {
				label.setText(value.toString());
				paintLabel(panel,column, value);
			}
		} else {
			label.setText("/");
			label.setForeground(Color.BLACK);
			panel.setForeground(Color.BLACK);
			panel.setBackground(Color.BLACK);
		}
		panel.add(label);
		
		return panel;
	}
	
	public static void paintLabel(JPanel panel, int column, Object value) {
		if (column == 0) {
			colorAction = Color.WHITE;
		}
		if (column == 1) {
			if (value == "Canasta") {
				colorAction = Color.GREEN;
			} else if (value == "Falta") {
				colorAction = Color.ORANGE;
			} else if (value == "Tiro Libre") {
				colorAction = Color.GRAY;
			} else if (value == "Expulsión") {
				colorAction = Color.RED;
			}
		}
		panel.setBackground(colorAction);
	}
}
