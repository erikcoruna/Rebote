import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Register extends JFrame {
	public static void main(String[] args) {
		new Register();
	}

	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(Start.class.getName());
	private static final long serialVersionUID = 1L;
	
	JTextField textFieldName = new JTextField("Introduzca nombre de usuario...");
	JPasswordField passwordFieldPassword = new JPasswordField();
	JPasswordField passwordFieldPassword2 = new JPasswordField();
	JRadioButton coach = new JRadioButton("Entrenador");
	JRadioButton player = new JRadioButton("Jugador");
	
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
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword = new JLabel("Introduzca su contraseña:");
		passwordFieldPassword.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword2 = new JLabel("Introduzca de nuevo su contraseña:");
		passwordFieldPassword2.setPreferredSize(new Dimension(300, 20));

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
		// Nuevo panel para meter los botones coach y player
		JPanel rolePanel = new JPanel();
		// Le ponemos el mismo layout que al panel principal
		rolePanel.setLayout(new GridBagLayout());
		// Creamos un lineBorder con título para los botones y lo añadimos al panel principal
		Border lineBorder = BorderFactory.createLineBorder(Color.RED);
		Border titledBorder = BorderFactory.createTitledBorder(lineBorder, "Elige tu rol");
		rolePanel.setBorder(titledBorder);
		rolePanel.add(coach, gbc);
		rolePanel.add(player, gbc);
		panel.add(rolePanel);
		
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
				buttonConfirmPressed();
				new Start();
				dispose();
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
				if (textFieldName.getText().equals("")) {
					textFieldName.setText("Introduzca nombre de usuario...");
				}
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				textFieldName.setText("");
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
	
	//https://youtu.be/ScUJx4aWRi0
	//Cogido parte de escritura en csv y adapatado a nuestro proyecto
	private void buttonConfirmPressed() {
		String username = textFieldName.getText();
		String password = new String(passwordFieldPassword.getPassword());
		String passwordConfirm = new String(passwordFieldPassword2.getPassword());
		int trainerOrPlayer = 0;
		
		if (coach.isSelected()) {
			trainerOrPlayer = 1;
		} else if (player.isSelected()) {
			trainerOrPlayer = 0;
		} else {
			JOptionPane.showMessageDialog(this, "No se ha seleccionado un rol", "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		
		if (password.equals(passwordConfirm)) {
			try (FileWriter writer = new FileWriter("files\\register.csv", true)) {
				writer.write(trainerOrPlayer + "," + username + "," + password + "\n");
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "No se ha podido guardar en el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
			textFieldName.setText("");
			passwordFieldPassword.setText("");
			passwordFieldPassword2.setText("");
			
		} else {
			JOptionPane.showMessageDialog(this, "Las contraseñas no coinciden", "ERROR", JOptionPane.ERROR_MESSAGE);
			passwordFieldPassword.setText("");
			passwordFieldPassword2.setText("");
		}
		
	}
}