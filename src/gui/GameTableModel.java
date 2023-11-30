package gui;
import javax.swing.table.AbstractTableModel;

import domain.GameScore;

public class GameTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private Object[] columnNames;
	private GameScore[] data;
	
	public GameTableModel(Object[] columnNames, GameScore[] data) {
		this.columnNames = columnNames;
		this.data = data;
	}
	
	@Override
	public int getRowCount() {
		return data.length;
	}

	@Override
	public int getColumnCount() {
		return columnNames.length;
	}

	@Override
	public String getColumnName(int column) {
		return (String) columnNames[column];
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return data[rowIndex].getId();
		} else if (columnIndex == 1) {
			return data[rowIndex].getCategory();
		} else if (columnIndex == 2) {
			return data[rowIndex].getName();
		} else if (columnIndex == 3) {
			return data[rowIndex].getScore();
		} else {
			return data[rowIndex].getCoordinates();
		}
	}
}
