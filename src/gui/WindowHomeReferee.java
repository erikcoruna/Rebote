package gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import com.toedter.calendar.JDateChooser;

import db.SQLiteDBManager;
import domain.Referee;
import domain.UserRepositoryException;

public class WindowHomeReferee extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new WindowHomeReferee(new Referee("erik.referee", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", null));
	}
	
	private static void updateReferee(Referee referee) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			dbManager.updateReferee(referee);
		} catch (UserRepositoryException e) {
			e.printStackTrace();
		}
	}
	
	public WindowHomeReferee(Referee referee) {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		
		//https://www.discoduroderoer.es/como-crear-pestanas-con-la-clase-jtabbedpane-en-java/
		//Un ejemplo de JTabbedPane, para saber como implementarlo en nuestro código
		JTabbedPane tabbedPanel = new JTabbedPane();
		
		//Tu perfil
			JPanel panelProfile = new JPanel(new GridBagLayout());
			
			GridBagConstraints gbc = new GridBagConstraints();
			gbc.gridx = 0;
			gbc.gridy = 0; 
			gbc.anchor = GridBagConstraints.WEST;
			gbc.insets = new Insets(0, 10, 10, 0);
			
			SQLiteDBManager dbManager = new SQLiteDBManager();
			JLabel labelUsername = new JLabel("Nombre de usuario: " + referee.getUsername());
			JLabel labelName = new JLabel("Nombre: " + referee.getName());
			JLabel labelFirstSurname = new JLabel("Primer apellido: " + referee.getFirstSurname());
			JLabel labelSecondSurname = new JLabel("Segundo apellido: " + referee.getSecondSurname());
			JLabel labelPassword = new JLabel("Contraseña: " + referee.getPassword());
			JLabel labelBirthDate = new JLabel("Fecha de nacimiento: " + referee.getBirthDate());
			JLabel labelCountry = new JLabel("Pais: " + referee.getCountry());
			JButton buttonChangeUsername = new JButton("Cambiar");
			JButton buttonChangeName = new JButton("Cambiar");
			JButton buttonChangeFirstSurname = new JButton("Cambiar");
			JButton buttonChangeSecondSurname = new JButton("Cambiar");
			JButton buttonChangePassword = new JButton("Cambiar");
			JButton buttonChangeBirthDate = new JButton("Cambiar");
			JButton buttonChangeCountry = new JButton("Cambiar");
			
			panelProfile.add(labelUsername, gbc);
	        gbc.gridy = 1;
	        panelProfile.add(labelName, gbc);       
	        gbc.gridy = 2;
	        panelProfile.add(labelFirstSurname, gbc);       
	        gbc.gridy = 3;
	        panelProfile.add(labelSecondSurname, gbc);
	        gbc.gridy = 4;
	        panelProfile.add(labelPassword, gbc);
	        gbc.gridy = 5;
			panelProfile.add(labelBirthDate, gbc);
			gbc.gridy = 6;
			panelProfile.add(labelCountry, gbc);
			gbc.gridx = 1;
			gbc.gridy = 0;
			panelProfile.add(buttonChangeUsername, gbc);
			gbc.gridy = 1;
			panelProfile.add(buttonChangeName, gbc);
			gbc.gridy = 2;
			panelProfile.add(buttonChangeFirstSurname, gbc);
			gbc.gridy = 3;
			panelProfile.add(buttonChangeSecondSurname, gbc);
			gbc.gridy = 4;
			panelProfile.add(buttonChangePassword, gbc);
			gbc.gridy = 5;
			panelProfile.add(buttonChangeBirthDate, gbc);
			gbc.gridy = 6;
			panelProfile.add(buttonChangeCountry, gbc);
			
			JScrollPane scrollPane = new JScrollPane(panelProfile);
			
	        tabbedPanel.addTab("Tu perfil", scrollPane);
	        
	        buttonChangeUsername.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = JOptionPane.showInputDialog(null, "Ingresa tu nuevo nombre de usuario:", "Nombre de usuario", JOptionPane.PLAIN_MESSAGE);
				if (username != null && !username.isBlank()) {
					referee.setUsername(username);
					updateReferee(referee);
					labelUsername.setText("Nombre de usuario: " + username);
					}
				}
			});
	        
	        buttonChangeName.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Ingresa tu nuevo nombre:", "Nombre", JOptionPane.PLAIN_MESSAGE);
				if (name != null && !name.isBlank()) {
					referee.setName(name);
					updateReferee(referee);
					labelName.setText("Nombre: " + name);
					}
				}
			});
	        
	        buttonChangeFirstSurname.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstSurname = JOptionPane.showInputDialog(null, "Ingresa tu nuevo primer apellido:", "Primer apellido", JOptionPane.PLAIN_MESSAGE);
				if (firstSurname != null && !firstSurname.isBlank()) {
					referee.setFirstSurname(firstSurname);
					updateReferee(referee);
					labelFirstSurname.setText("Primer apellido: " + firstSurname);
					}
				}
			});
	        
	        buttonChangeSecondSurname.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				String secondSurname = JOptionPane.showInputDialog(null, "Ingresa tu nuevo segundo apellido:", "Segundo apellido", JOptionPane.PLAIN_MESSAGE);
				if (secondSurname != null && !secondSurname.isBlank()) {
					referee.setSecondSurname(secondSurname);
					updateReferee(referee);
					labelSecondSurname.setText("Segundo apellido: " + secondSurname);
					}
				}
			});
	        
	        buttonChangePassword.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = JOptionPane.showInputDialog(null, "Ingresa tu nueva contraseña:", "Contraseña", JOptionPane.PLAIN_MESSAGE);
				if (password != null && !password.isBlank()) {
					referee.setPassword(password);
					updateReferee(referee);
					labelPassword.setText("Contraseña: " + password);
					}
				}
			});
	        
	        buttonChangeBirthDate.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 1));
				JLabel label = new JLabel("Elige tu nueva fecha de nacimiento (yyyy-MM-dd):");
				JDateChooser dateChooser = new JDateChooser();
				dateChooser.setDateFormatString("yyyy-MM-dd");
				panel.add(label);
				panel.add(dateChooser);
				int result = JOptionPane.showConfirmDialog(null, panel, "Fecha de nacimiento", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (result == 0 && dateChooser.getDate() != null) {
					Date date = dateChooser.getDate();
					GregorianCalendar calendar = new GregorianCalendar();
					calendar.setTime(date);
					referee.setBirthDate(calendar);
					updateReferee(referee);
					labelBirthDate.setText("Fecha de nacimiento: " + dbManager.calendarToString(calendar));
					}
				}
			});
	        
	        buttonChangeCountry.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
				JPanel panel = new JPanel(new GridLayout(2, 1));
				JLabel label = new JLabel("Elige tu nuevo país:");
				JComboBox<String> comboBox = new JComboBox<>(new Vector<>(Arrays.asList(
						"España", "Francia", "Portugal", "Alemania", "Italia"
						)));
				panel.add(label);
				panel.add(comboBox);
				int result = JOptionPane.showConfirmDialog(null, panel, "País", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
				String country = comboBox.getSelectedItem().toString();
				if (result == 0 && country != null) {
					referee.setCountry(country);
					updateReferee(referee);
					labelCountry.setText("País: " + country);
					}
				}
			});
	        
	      //Nuevo partido
	        JPanel panelNewGame = new JPanel();
	        
	        JLabel labelNotice = new JLabel("Añadir la venta de editar partidos");
	        panelNewGame.add(labelNotice);
	        
	        JScrollPane scrollPane1 = new JScrollPane(panelNewGame);
	        tabbedPanel.addTab("Nuevo partido", scrollPane1);
		
        add(tabbedPanel);
		setVisible(true);
	}
}
