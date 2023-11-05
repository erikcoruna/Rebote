import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartTrainer extends JFrame {
	
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(StartTrainer.class.getName());
	private static final long serialVersionUID = 1L;
	
	public StartTrainer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inicio Entrenador");
		setAutoRequestFocus(false);
		
		JPanel northPanel = new JPanel();
		northPanel.setLayout(new BorderLayout());
		JLabel title = new JLabel("¡Bienvenido a REBOTE!");
		Font titleFont = new Font("Arial", Font.BOLD, 30);
		title.setFont(titleFont);
		northPanel.add(title, BorderLayout.NORTH);
		
		JTextField searcher = new JTextField("Buscar...");
		northPanel.add(searcher, BorderLayout.CENTER);
		
		JPanel centerPanel = new JPanel();
		centerPanel.setLayout(new GridBagLayout());
		
		//Añadir Imagen
		//Añadir Buscador
		
		JPanel southPanel = new JPanel();
		southPanel.setLayout(new GridLayout(2, 2));
		JButton selectButton = new JButton("Seleccionar");
		southPanel.add(selectButton);
		JButton editTrainerButton = new JButton("Editar entrenador");
		southPanel.add(editTrainerButton);
		JButton createTrainerButton = new JButton("Crear entrenador");
		southPanel.add(createTrainerButton);
		JButton deleteTrainerButton = new JButton("Eliminar tu usuario");
		deleteTrainerButton.setBackground(Color.RED);
		deleteTrainerButton.setForeground(Color.ORANGE);
		southPanel.add(deleteTrainerButton);
		
		add(northPanel, BorderLayout.NORTH);
		add(centerPanel, BorderLayout.CENTER);
		add(southPanel, BorderLayout.SOUTH);
		setVisible(true);
		
		searcher.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				if (searcher.getText().equals("")) {
					searcher.setText("Buscar...");
					logger.info("Salido del campo de texto.");
				}
			}
			@Override
			public void focusGained(FocusEvent e) {
				searcher.setText("");
				logger.info("Entrado al campo de texto.");
			}
		});
		selectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("Pulsado el botón selectButton.");
			}
		});
		editTrainerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("Pulsado el botón editTrainerButton.");
			}
		});
		createTrainerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("Pulsado el botón createTrainerButton.");
			}
		});
		deleteTrainerButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("Pulsado el botón deleteTrainerButton.");
			}
		});
	}
}
