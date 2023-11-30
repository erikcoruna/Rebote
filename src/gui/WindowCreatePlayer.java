package gui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.toedter.calendar.JDateChooser;

public class WindowCreatePlayer extends JFrame {
	public static void main(String[] args) {
		new WindowCreatePlayer();
	}
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(WindowStartPlayer.class.getName());
	private static final long serialVersionUID = 1L;
	
	JTextField nameTextField = new JTextField(50);
	JTextField surname1TextField = new JTextField(50);
	JTextField surname2TextField = new JTextField(50);
	JTextField dorsalNumberTextField = new JTextField(50);
	JDateChooser birthDateChooser = new JDateChooser();
	SpinnerModel valuesSpinnerNumberModel = new SpinnerNumberModel(150, 100, 250, 1);
	JSpinner heightSpinner = new JSpinner(valuesSpinnerNumberModel);
	
	public WindowCreatePlayer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Creacion de Jugador");
			
		JPanel titlePanel = new JPanel();
		JLabel labelTitle = new JLabel("Creación de Jugador");
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
		JLabel surname1Label = new JLabel("Primer apellido:");
		JLabel surname2Label = new JLabel("Segundo apellido:");
		JLabel dorsalNumber = new JLabel("Numero de dorsal:");
		JLabel heightLabel = new JLabel("Altura (cm):");
		
		//IAG Hugo Rey Insausti_2023-11-05_17-44
		//El código generado por IA ha sido modificado para adecuarlo a nuestra aplicación
		JLabel birthLabel = new JLabel("Fecha nacimiento: ");
		birthDateChooser.setDateFormatString("MMM. dd, yyyy");
		
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
		informationPanel.add(dorsalNumber, gbc);
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(dorsalNumberTextField, gbc);
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(heightLabel, gbc);
		gbc.gridy = 9;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(heightSpinner, gbc);
		gbc.gridy = 10;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(birthLabel, gbc);
		gbc.gridy = 11;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(birthDateChooser, gbc);
		
		JPanel buttonPanel = new JPanel();
		JButton saveButton = new JButton("Guardar");
		buttonPanel.add(saveButton);
		JButton cancelButton = new JButton("Cancelar");
		buttonPanel.add(cancelButton);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add(titlePanel, BorderLayout.NORTH);
		add(informationPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
		
		saveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				savePlayer();
			}
		});
		
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
		dorsalNumberTextField.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto dorsalNumberTextField.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto dorsalNumberTextField.");
			}
		});
		valuesSpinnerNumberModel.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				logger.info("Cambiado el valor de valuesSpinnerNumberModel.");
			}
		});
		birthDateChooser.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de eleccion de fecha birthDateChooser.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de eleccion de fecha dorsalNumberTextField.");
			}
		});
	}
	
	public void savePlayer() {
		String username = nameTextField.getText();
		String surname1 = surname1TextField.getText();
		String surname2 = surname2TextField.getText();
		String dorsalNumber = dorsalNumberTextField.getText();
		int height = Integer.parseInt(heightSpinner.getValue().toString());
		Date date = birthDateChooser.getDate();
		
		if (!username.isEmpty() && !surname1.isEmpty() && !surname2.isEmpty() && !dorsalNumber.isEmpty() && date != null) {
			try (FileWriter writer = new FileWriter("files\\players.csv", true)) {
				writer.write(username + "," + surname1 + "," + surname2 + "," + dorsalNumber + "," + height + "," + date);
				nameTextField.setText("");
				surname1TextField.setText("");
				surname2TextField.setText("");
				dorsalNumberTextField.setText("");
				heightSpinner.setValue(150);
				birthDateChooser.setDate(null);
			} catch (IOException e) {
				JOptionPane.showMessageDialog(this, "No se ha podido guardar en el fichero", "ERROR", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
			}
		} else {
			System.out.println("Es necesario rellenar todos los campos.");
		}
	}
}
