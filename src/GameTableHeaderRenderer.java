import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class GameTableHeaderRenderer implements TableCellRenderer {

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		JLabel label = new JLabel();

		// Erik Coruña Rodríguez_2023-11-26_23-15.txt
		// Se han modificado algunas cosas en el código.
        ImageIcon icon = new ImageIcon("img/" + value + ".png");
        Image scaledImage = icon.getImage().getScaledInstance(90, 30, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);

        label.setIcon(scaledIcon);
        label.setHorizontalAlignment(JLabel.CENTER);
        label.setVerticalAlignment(JLabel.CENTER);
        
        label.setOpaque(true);
		
		return label;
	}

}
