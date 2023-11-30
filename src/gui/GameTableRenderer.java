package gui;
import java.awt.Component;
import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GameTableRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = new JLabel();
		
		if (column == 4) {
			Point point = (Point) value;
			label.setText("x=" + point.x + " y=" + point.y);
		} else {
			label.setText(value.toString());
		}
		
		return label;
	}
}
