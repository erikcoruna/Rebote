package gui;
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
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.toedter.calendar.JDateChooser;

import db.SQLiteDBManager;
import domain.Player;
import domain.Trainer;
import domain.UserRepositoryException;

public class WindowRegister extends JFrame {
	public static void main(String[] args) {
		new WindowRegister();
	}

	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(WindowStart.class.getName());
	private static final long serialVersionUID = 1L;
	
	public JTextField textFieldName = new JTextField();
	public JTextField textFieldFirstSurname = new JTextField();
	public JTextField textFieldSecondSurname = new JTextField();
	public JPasswordField passwordFieldPassword = new JPasswordField();
	public JPasswordField passwordFieldPassword2 = new JPasswordField();
	public JDateChooser birthDateChooser = new JDateChooser();
	public JComboBox<String> countryComboBox = new JComboBox<>(new Vector<>(Arrays.asList(
			"España", "Francia", "Portugal", "Alemania", "Italia"
			)));
	public JRadioButton coach = new JRadioButton("Entrenador");
	public JRadioButton player = new JRadioButton("Jugador");
	
	public WindowRegister() {
		setSize(480, 760);
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

		JLabel labelName = new JLabel("Introduzca su nombre:");
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelFirstSurname = new JLabel("Introduzca su primer apellido:");
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelSecondSurname = new JLabel("Introduzca su segundo apellido:");
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword = new JLabel("Introduzca su contraseña:");
		passwordFieldPassword.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword2 = new JLabel("Introduzca de nuevo su contraseña:");
		passwordFieldPassword2.setPreferredSize(new Dimension(300, 20));
		JLabel labelBirthDate = new JLabel("Fecha nacimiento: ");
		birthDateChooser.setDateFormatString("yyyy-MM-dd");
		JLabel labelCountry = new JLabel("Seleccione su país:");
		textFieldName.setPreferredSize(new Dimension(300, 20));

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(coach);
		grupo.add(player);

		panel.add(labelName, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(textFieldName, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelFirstSurname, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(textFieldFirstSurname, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelSecondSurname, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(textFieldSecondSurname, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(passwordFieldPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelPassword2, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(passwordFieldPassword2, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelBirthDate, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(birthDateChooser, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelCountry, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(countryComboBox, gbc);
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
				if (!textFieldName.getText().isEmpty() && !textFieldFirstSurname.getText().isEmpty() && !textFieldSecondSurname.getText().isEmpty()
						&& !passwordFieldPassword.getPassword().toString().isEmpty() && !passwordFieldPassword2.getPassword().toString().isEmpty()
						&& !(birthDateChooser.getDate() == null) && (coach.isSelected() || player.isSelected())) {
					if (birthDateChooser.getDate().before(new Date())) {
						SQLiteDBManager dbManager = new SQLiteDBManager();
						try {
							System.out.println("Conectando con la base de datos...");
							dbManager.connect("src/db/rebote.db");
							
							Date date = birthDateChooser.getDate();
							GregorianCalendar calendar = new GregorianCalendar();
							calendar.setTime(date);
							
							if (coach.isSelected()) {
								dbManager.createTrainerTable();
								Trainer trainer = new Trainer(
										textFieldName.getText(),
										textFieldFirstSurname.getText(),
										textFieldSecondSurname.getText(),
										passwordFieldPassword.getPassword().toString(),
										calendar,
										countryComboBox.getSelectedItem().toString(),
										new Vector<String>()
										);
								dbManager.storeTrainer(trainer);
								System.out.println("Entrenador " + textFieldName.getText() + " ha sido registrado correctamente.");
							} else if (player.isSelected()) {
								dbManager.createPlayerTable();
								Player player = new Player(
										textFieldName.getText(),
										textFieldFirstSurname.getText(),
										textFieldSecondSurname.getText(),
										passwordFieldPassword.getPassword().toString(),
										calendar,
										countryComboBox.getSelectedItem().toString(),
										"",
										0,
										0.0f,
										""
										);
								dbManager.storePlayer(player);
								System.out.println("Jugador " + textFieldName.getText() + " ha sido registrado correctamente.");
							}
							dbManager.disconnect();
						} catch (UserRepositoryException ex) {
							System.out.println("Error accediendo a la base de datos.");
							ex.printStackTrace();
						}
					} else {
						System.out.println("La fecha de nacimiento no puede ser superior a la actual.");
					}
				} else {
					System.out.println("Debes rellenar todos los campos para registrar un usuario.");
				}
				logger.info("Pulsado el botón confirm.");
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
		textFieldFirstSurname.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto.");
			}
		});
		textFieldSecondSurname.addFocusListener(new FocusListener() {
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