import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateTrainer extends JFrame {
	
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(StartPlayer.class.getName());
	private static final long serialVersionUID = 1L;

	public CreateTrainer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Creacion de Jugador");
			
		JPanel titlePanel = new JPanel();
		JLabel labelTitle = new JLabel("Creación de Entrenador");
		Font font = new Font("Arial", Font.BOLD, 30);
		labelTitle.setFont(font);
		titlePanel.add(labelTitle);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		
		JLabel nameLabel = new JLabel("Nombre:");
		JTextField nameTextField = new JTextField(50);
		JLabel surname1Label = new JLabel("Primer apellido:");
		JTextField surname1TextField = new JTextField(50);
		JLabel surname2Label = new JLabel("Segundo apellido:");
		JTextField surname2TextField = new JTextField(50);
		//posible mejora con JList
		JLabel birthLabel = new JLabel("Fecha nacimiento (dd/mm/aaaa): "); 
		JTextField birthTextField = new JTextField(50);
		
		
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameLabel, gbc);
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameTextField, gbc);
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname1Label, gbc);
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname1TextField, gbc);
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname2Label, gbc);
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname2TextField, gbc);
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(birthLabel, gbc);
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(birthTextField, gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Guardar"));
		buttonPanel.add(new JButton("Cancelar"));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add(titlePanel, BorderLayout.NORTH);
		add(informationPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
		
		nameTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto nameTextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto nameTextField.");
			}
		});
		surname1TextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto surname1TextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto surname1TextField.");
			}
		});
		surname2TextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto surname2TextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto surname2TextField.");
			}
		});
		birthTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto birthTextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto birthTextField.");
			}
		});
	}
}
