import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.logging.Logger;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Register extends JFrame {

	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(Start.class.getName());
	private static final long serialVersionUID = 1L;

	public Register() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Registrarse");

		// https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
        // Se ha modificado parte del código para adecuarlo a nuestro proyecto con los botones y textos necesarios.
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;

		JLabel labelName = new JLabel("Introduzca un nombre de usuario:");
		JTextField textFieldName = new JTextField();
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword = new JLabel("Introduzca su contraseña:");
		JPasswordField passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword2 = new JLabel("Introduzca de nuevo su contraseña:");
		JPasswordField passwordFieldPassword2 = new JPasswordField();
		passwordFieldPassword2.setPreferredSize(new Dimension(300, 20));
		JLabel labelRole = new JLabel("Elija su rol:");
		JRadioButton coach = new JRadioButton("Entrenador");
		JRadioButton player = new JRadioButton("Jugador");

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(coach);
		grupo.add(player);

		panel.add(labelName, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(textFieldName, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(passwordFieldPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelPassword2, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(passwordFieldPassword2, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelRole, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(coach, gbc);
		panel.add(player, gbc);
		
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
				logger.info("Pulsado el botón confirm.");
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Start();
				dispose();
				logger.info("Pulsado el botón cancel.");
			}
		});
		textFieldName.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto.");
			}
		});
		passwordFieldPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto.");
			}
		});
		passwordFieldPassword2.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto.");
			}
		});
		coach.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Pulsado el botón coach.");
			}
		});
		player.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				logger.info("Pulsado el botón player.");
			}
		});
	}
}