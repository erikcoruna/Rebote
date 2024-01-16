package gui;

import io.CSVFileManager;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import collections.ReboteCollections;
import db.SQLiteDBManager;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
import domain.UserRepositoryException;


public class WindowLogin extends JFrame {
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(WindowStart.class.getName());
	private static final long serialVersionUID = 1L;
	
	CSVFileManager fileManager = new CSVFileManager();
	public JTextField textFieldUsername = new JTextField();
	public JPasswordField passwordFieldPassword = new JPasswordField();

    public WindowLogin() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Iniciar Sesión");
	
		// https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
        // Se ha modificado parte del código para adecuarlo a nuestro proyecto con los botones y textos necesarios.
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel labelUsername = new JLabel("Introduzca su nombre de usuario:");
		textFieldUsername.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword = new JLabel("Introduzca su contraseña:");
		passwordFieldPassword.setPreferredSize(new Dimension(300, 20));
		
		panel.add(labelUsername, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(textFieldUsername, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(passwordFieldPassword, gbc);
		
		for (int i = 0; i < 3; i++) {
			panel.add(new JLabel(" "), gbc);
		}
		
		JButton confirm = new JButton("Confirmar");
		JButton cancel = new JButton("Cancelar");

		panel.add(confirm, gbc);
		panel.add(cancel, gbc);
		
		add(panel);
		setVisible(true);
		
		// https://github.com/andoni-eguiluz/ud-progII-2023/blob/master/Clase2023-2/src/tema5/resueltos/ej5b8/VentanaPrincipal.java
        // Cogido la parte de los ActionListener y modificado para nuestro código.
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!textFieldUsername.getText().isEmpty() && !String.valueOf(passwordFieldPassword.getPassword()).isEmpty()) {
					if (textFieldUsername.getText().equals("admin") && String.valueOf(passwordFieldPassword.getPassword()).equals("admin")) {
						dispose();
						try {
							new WindowLeague(ReboteCollections.createLeague(Paths.get("resources/db/rebote.db")));
						} catch (Exception e1) {
							e1.printStackTrace();
						}
					} else {
						SQLiteDBManager dbManager = new SQLiteDBManager();
						String inputUsername = textFieldUsername.getText();
						String inputPassword = String.valueOf(passwordFieldPassword.getPassword());
						try {
							dbManager.connect("resources/db/rebote.db");
							
							for (Player player : dbManager.getAllPlayers()) {
								if (player.getUsername().equals(inputUsername) && String.valueOf(player.getPassword()).equals(inputPassword)) {
									dispose();
									new WindowHomePlayer(player);
									logger.info("Login como jugador: " + player.getUsername());
								}
							}
							for (Trainer trainer : dbManager.getAllTrainers()) {
								if (trainer.getUsername().equals(inputUsername) && String.valueOf(trainer.getPassword()).equals(inputPassword)) {
									new WindowHomeTrainer(trainer);
									dispose();
									logger.info("Login como entrenador: " + trainer.getUsername());
								}
							}
							
							dbManager.disconnect();
						} catch (UserRepositoryException e1) {
							e1.printStackTrace();
						}
					}
				}
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowStart();
				dispose();
				logger.info("Pulsado el botón cancel.");
			}
		});
    }
}
