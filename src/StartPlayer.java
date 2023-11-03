import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPlayer extends JFrame {
	public static void main(String[] args) {
		new StartPlayer();
	}
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(StartPlayer.class.getName());
	private static final long serialVersionUID = 1L;
	
	public StartPlayer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inicio Jugador");
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
		JButton editUserButton = new JButton("Editar usuario");
		southPanel.add(editUserButton);
		JButton createUserButton = new JButton("Crear usuario");
		southPanel.add(createUserButton);
		JButton deleteUserButton = new JButton("Eliminar tu usuario");
		deleteUserButton.setBackground(Color.RED);
		deleteUserButton.setForeground(Color.ORANGE);
		// ALT + x para atajo de teclado
		deleteUserButton.setMnemonic(KeyEvent.VK_X);
		deleteUserButton.setToolTipText("ALT + x para atajo de teclado.");
		southPanel.add(deleteUserButton);
		
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
	}
}
