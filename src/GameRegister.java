import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

		//Vamos a poner una botonera en la parte inferior y luego dividir el resto de la ventana en dos, para que
		//en una mitad tengamos un campo de baloncesto en el que interactuar y en la otra, una tabla en la que se
		//van registrando todas las acciones que se registran
public class GameRegister extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new GameRegister();
		}
	
	public GameRegister() {
		setSize(960, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Registro de partido");
		
		JPanel panelCenter = new JPanel();
		panelCenter.setLayout(new GridLayout(1, 2));
		
		//Panel en el que se visualiza un campo de baloncesto y que al clicar registra una accón
		JPanel panelGame = new JPanel();
		ImageIcon field = new ImageIcon("img/paneGame.png");
		JLabel labelGame = new JLabel(field);
		panelGame.add(labelGame);
		
		// Crear una tabla con scroll en la que se regisgraran las acciones
		JPanel panelTable = new JPanel();
		Object[] columnNames = {"id", "category", "autor", "score", "coordinates"};
		GameScore[] data = {
				new GameScore("11112222A", "1A", "Erik", 2, new Point(0, 0)),
				new GameScore("33334444B", "2B", "Ander", 3, new Point(1, 0))
		};
		
		JTable tableActions = new JTable();
		GameTableModel tableModel = new GameTableModel(columnNames, data);
		JScrollPane scrollPane = new JScrollPane(tableActions);
		tableActions.setModel(tableModel);
		tableActions.setDefaultRenderer(Object.class, new GameTableRenderer());
		tableActions.getTableHeader().setDefaultRenderer(new GameTableHeaderRenderer());
		panelTable.add(scrollPane);
		
		//Botonera con opciones en la parte de abajo a la derecha
		JPanel panelButtonBox = new JPanel();
		panelButtonBox.setLayout(new FlowLayout(FlowLayout.RIGHT));
	
		JButton buttonFinish = new JButton();
		buttonFinish.setText("Finalizar");
		
		//https://chuidiang.org/index.php?title=Botones_con_icono_y_texto
		//De esta página hemos obtenido el código para insertar la imagen de icono en un botón
		JButton buttonHelp = new JButton();
		buttonHelp.setIcon(new ImageIcon("img/buttonHelp.png")); 
		buttonHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Se encuentra usted en la ventana de registro de partidos.\n\n A continuación, haga clic sobre las coordenadas del campo en las que esté ocurriendo la acción a registrar. \nEn ese momento, se desplegará una ventana en la que podrá meter los datos necesarios de dicha acción y \nésta quedará registrada.\n\n Cuando termine, podrá usted ver en la parte derecha de la ventana las acciones que ya han sido registradas \ny después de hacer clic sobre ellas, podrá eliminarlas pulsando delete mientras clicas la acción a borrar o \nhaciendo ctrl+z para eliminar la más reciente.");
				
			}
		});
		
		
		panelButtonBox.add(buttonHelp);
		panelButtonBox.add(buttonFinish);
		panelCenter.add(panelGame);
		panelCenter.add(panelTable);
		add(panelCenter, BorderLayout.CENTER);
		add(panelButtonBox, BorderLayout.SOUTH);
		setVisible(true);
	}

}
