package gui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;

import com.toedter.calendar.JDateChooser;

import db.SQLiteDBManager;
import domain.Player;
import domain.Team;
import domain.UserRepositoryException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WindowHomePlayer extends JFrame {

	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new WindowHomePlayer(new Player("erik.player", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", "A1", 170, 60.4f, new Team("team1", "Bilbao", "Bilbao Basket", "Este es el equipo de Bilbao.")));
	}
	
	public static void updatePlayer(Player player) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			dbManager.updatePlayer(player);
		} catch (UserRepositoryException e) {
			e.printStackTrace();
		}
	}
	
	public WindowHomePlayer(Player player) {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		
		//https://www.discoduroderoer.es/como-crear-pestanas-con-la-clase-jtabbedpane-en-java/
		//Un ejemplo de JTabbedPane, para saber como implementarlo en nuestro código
		JTabbedPane tabbedPanel = new JTabbedPane(); 
		
		JPanel paneProfile = new JPanel(new GridBagLayout());
       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 10, 10, 0);
        
        SQLiteDBManager dbManager = new SQLiteDBManager();
        JLabel labelUsername = new JLabel("Nombre de usuario: " + player.getUsername());
        JLabel labelName = new JLabel("Nombre: " + player.getName());
		JLabel labelFirstSurname = new JLabel("Primer apellido: " + player.getFirstSurname());
		JLabel labelSecondSurname = new JLabel("Segundo apellido: " + player.getSecondSurname());
		JLabel labelPassword = new JLabel("Contraseña: " + player.getPassword());
		JLabel labelBirthDate = new JLabel("Fecha de nacimiento: " + dbManager.calendarToString(player.getBirthDate()));
		JLabel labelCountry = new JLabel("País: " + player.getCountry());
		JButton buttonChangeUsername = new JButton("Cambiar");
		JButton buttonChangeName = new JButton("Cambiar");
		JButton buttonChangeFirstSurname = new JButton("Cambiar");
		JButton buttonChangeSecondSurname = new JButton("Cambiar");
		JButton buttonChangePassword = new JButton("Cambiar");
		JButton buttonChangeBirthDate = new JButton("Cambiar");
		JButton buttonChangeCountry = new JButton("Cambiar");
        
        paneProfile.add(labelUsername, gbc);
        gbc.gridy = 1;
        paneProfile.add(labelName, gbc);       
        gbc.gridy = 2;
        paneProfile.add(labelFirstSurname, gbc);       
        gbc.gridy = 3;
        paneProfile.add(labelSecondSurname, gbc);
        gbc.gridy = 4;
        paneProfile.add(labelPassword, gbc);
        gbc.gridy = 5;
		paneProfile.add(labelBirthDate, gbc);
		gbc.gridy = 6;
		paneProfile.add(labelCountry, gbc);
		gbc.gridx = 1;
		gbc.gridy = 0;
		paneProfile.add(buttonChangeUsername, gbc);
		gbc.gridy = 1;
		paneProfile.add(buttonChangeName, gbc);
		gbc.gridy = 2;
		paneProfile.add(buttonChangeFirstSurname, gbc);
		gbc.gridy = 3;
		paneProfile.add(buttonChangeSecondSurname, gbc);
		gbc.gridy = 4;
		paneProfile.add(buttonChangePassword, gbc);
		gbc.gridy = 5;
		paneProfile.add(buttonChangeBirthDate, gbc);
		gbc.gridy = 6;
		paneProfile.add(buttonChangeCountry, gbc);
		
		JScrollPane scrollPane = new JScrollPane(paneProfile);
		
        tabbedPanel.addTab("Tu perfil", scrollPane);
        
        buttonChangeUsername.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String username = JOptionPane.showInputDialog(null, "Ingresa tu nuevo nombre de usuario:", "Nombre de usuario", JOptionPane.PLAIN_MESSAGE);
				if (username != null && !username.isBlank()) {
					player.setUsername(username);
					updatePlayer(player);
					labelUsername.setText("Nombre de usuario: " + username);
				}
			}
		});
        
        buttonChangeName.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String name = JOptionPane.showInputDialog(null, "Ingresa tu nuevo nombre:", "Nombre", JOptionPane.PLAIN_MESSAGE);
				if (name != null && !name.isBlank()) {
					player.setName(name);
					updatePlayer(player);
					labelName.setText("Nombre: " + name);
				}
			}
		});
        
        buttonChangeFirstSurname.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String firstSurname = JOptionPane.showInputDialog(null, "Ingresa tu nuevo primer apellido:", "Primer apellido", JOptionPane.PLAIN_MESSAGE);
				if (firstSurname != null && !firstSurname.isBlank()) {
					player.setFirstSurname(firstSurname);
					updatePlayer(player);
					labelFirstSurname.setText("Primer apellido: " + firstSurname);
				}
			}
		});
        
        buttonChangeSecondSurname.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String secondSurname = JOptionPane.showInputDialog(null, "Ingresa tu nuevo segundo apellido:", "Segundo apellido", JOptionPane.PLAIN_MESSAGE);
				if (secondSurname != null && !secondSurname.isBlank()) {
					player.setSecondSurname(secondSurname);
					updatePlayer(player);
					labelSecondSurname.setText("Segundo apellido: " + secondSurname);
				}
			}
		});
        
        buttonChangePassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String password = JOptionPane.showInputDialog(null, "Ingresa tu nueva contraseña:", "Contraseña", JOptionPane.PLAIN_MESSAGE);
				if (password != null && !password.isBlank()) {
					player.setPassword(password);
					updatePlayer(player);
					labelPassword.setText("Contraseña: " + password);
				}
			}
		});
        
        buttonChangeBirthDate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String birthDate = JOptionPane.showInputDialog(null, "Ingresa tu nueva fecha de nacimiento (yyyy-MM-dd):", "Fecha de nacimiento", JOptionPane.PLAIN_MESSAGE);
				if (birthDate != null && !birthDate.isBlank()) {
					player.setBirthDate(dbManager.stringToCalendar(birthDate));
					updatePlayer(player);
					labelBirthDate.setText("Fecha de nacimiento: " + birthDate);
				}
			}
		});
        
        buttonChangeCountry.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String country = JOptionPane.showInputDialog(null, "Ingresa tu nuevo país:", "País", JOptionPane.PLAIN_MESSAGE);
				if (country != null && !country.isBlank()) {
					player.setCountry(country);
					updatePlayer(player);
					labelCountry.setText("País: " + country);
				}
			}
		});
        
        //https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
        //Para hacer que haya solo scroll vertical
        JPanel panelTeam = new JPanel();
        JScrollPane scrollPaneTeam = new JScrollPane(panelTeam);
        scrollPaneTeam.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTeam.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       // for(Player p : players) {
       //	JTable tableTeammates = new JTable(tableModel);
       // 	String[] columnNames = {"ID", "Categoría", "Autor", "Coordenadas"};
       //	DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
       //	panelTeam.add(tableTeammates);
       // }
		tabbedPanel.addTab("Tu equipo", scrollPaneTeam);

        JPanel panelSearch = new JPanel();
        panelSearch.add(new JLabel("Buscador de otros jugadores y equipos"));
        tabbedPanel.addTab("Buscador", panelSearch);

        JPanel panelGame = new JPanel();
        panelGame.add(new JLabel("Registro de partidos que habéis jugado"));
        tabbedPanel.addTab("Partidos", panelGame);
        add(tabbedPanel);
 
		setVisible(true);
	}
	
}
