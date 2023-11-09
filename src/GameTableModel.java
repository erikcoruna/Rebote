import java.util.Arrays;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public class GameTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	private List<String> columnNames = Arrays.asList("ID", "Categoría", "Autor", "Puntuación", "Coordenadas");
	private List<GameScore> gameScore;
	
	public GameTableModel(List<GameScore> gameScore) {
		this.gameScore = gameScore;
	}
	
	@Override
	public int getRowCount() {
		return gameScore.size();
	}

	@Override
	public int getColumnCount() {
		return columnNames.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0) {
			return gameScore.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return gameScore.get(rowIndex).getCategory();
		} else if (columnIndex == 2) {
			return gameScore.get(rowIndex).getName();
		} else if (columnIndex == 3) {
			return gameScore.get(rowIndex).getScore();
		} else {
			return gameScore.get(rowIndex).getCoordinates();
		}
	}
	
	public void removeRow(int rowIndex) {
		gameScore.remove(rowIndex);
	}
}
