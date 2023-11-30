package gui;
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

public class WindowCreateTeam extends JFrame {
	public static void main(String[] args) {
		new WindowCreateTeam();
	}
	
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(WindowStartPlayer.class.getName());
	private static final long serialVersionUID = 1L;

	public WindowCreateTeam() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Creacion de Equipo");
			
		JPanel titlePanel = new JPanel();
		JLabel labelTitle = new JLabel("Creación de Equipo");
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
		JLabel locationLabel = new JLabel("Ciudad:");
		JTextField locationTextField = new JTextField(50);
		JLabel stadiumLabel = new JLabel("Nombre de la cancha/estadio:");
		JTextField stadiumTextField = new JTextField(50);
		JLabel descriptionLabel = new JLabel("Descripción:");
		JTextField descriptionTextField = new JTextField(100);
		
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameLabel, gbc);
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameTextField, gbc);
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(locationLabel, gbc);
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(locationTextField, gbc);
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(stadiumLabel, gbc);
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(stadiumTextField, gbc);
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(descriptionLabel, gbc);
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(descriptionTextField, gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Cancelar"));
		buttonPanel.add(new JButton("Guardar"));
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
		locationTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto locationTextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto locationTextField.");
			}
		});
		stadiumTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto stadiumTextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto stadiumTextField.");
			}
		});
		descriptionTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto descriptionTextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto descriptionTextField.");
			}
		});
	}
}
