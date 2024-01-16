package gui;
import java.awt.Point;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import domain.Basket;
import domain.Expulsion;
import domain.Foul;
import domain.FreeThrow;
import domain.GameScore;

public class GameTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	private Object[] columnNames;
	private List<GameScore> data;
	
	public GameTableModel(Object[] columnNames, List<GameScore> incidents) {
		this.columnNames = columnNames;
		this.data = incidents;
	}
	
	@Override
	public int getRowCount() {
		return data.size();
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
			return data.get(rowIndex).getId();
		} else if (columnIndex == 1) {
			return getCategory(data.get(rowIndex));
		} else if (columnIndex == 2) {
			return data.get(rowIndex).getAuthor().getName();
		} else if (columnIndex == 3) {
			Integer nullValue = null;
			
			// Solo devuelve una puntuación si es una canasta o un tiro libre
			if (data.get(rowIndex) instanceof Basket) {
				Basket basket = (Basket) data.get(rowIndex);
				return basket.getValue();
			}else if (data.get(rowIndex) instanceof FreeThrow) {
				FreeThrow freeThrow = (FreeThrow) data.get(rowIndex);
				
				if (freeThrow.isSuccess()) {
					return 1;
				}else {
					return 0;
				}
			}else {
				return nullValue;
			}

		} else {
			if (data.get(rowIndex) instanceof Basket) {
				Basket basket = (Basket) data.get(rowIndex);
				return basket.getCoordinates();
			}else {
				Point nullPoint = null;
				return nullPoint;
			}
		}
	}
	
	// Obtener el nombre de la categoría a la que pertenece nuestro gameScore
	public String getCategory(GameScore gameScore) {
		if (gameScore instanceof Foul) {
			return "Falta";
		} else if (gameScore instanceof Basket) {
			return "Canasta";
		}else if (gameScore instanceof FreeThrow) {
			return "Tiro Libre";
		}else if (gameScore instanceof Expulsion) {
			return "Expulsión";
		}else {
			return null;
		}
	} 
}
