package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

import com.toedter.calendar.JDateChooser;

import db.SQLiteDBManager;
import domain.League;
import domain.Player;
import domain.Team;
import domain.UserRepositoryException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WindowHomePlayer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelSearch;
	private JPanel panelNorthSearch;
	private JPanel panelTeamsSearch;
	private JTextField textFieldSearch;
	private JButton buttonSearch;
	private JScrollPane scrollPaneSearch;
	
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
	
	private void addTeamPanel(Team team) {
		JPanel panelCenter = new JPanel(new GridLayout(1, 3));
		panelCenter.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelCenter.setMaximumSize(new Dimension(this.getWidth() - 50, 100));
		panelCenter.setPreferredSize(new Dimension(this.getWidth() - 50, 100));
    	panelCenter.setBackground(Color.WHITE);
    	JLabel labelIcon = new JLabel();
    	ImageIcon icon = new ImageIcon("src/img/" + team.getLeague() + ".png");
    	labelIcon.setIcon(icon);
    	JLabel labelNameSearch = new JLabel(team.getName());
    	JLabel labelCitySearch = new JLabel(team.getCity());
    	panelCenter.add(labelIcon);
    	panelCenter.add(labelNameSearch);
    	panelCenter.add(labelCitySearch);
    	panelTeamsSearch.add(panelCenter);
    	panelTeamsSearch.add(Box.createRigidArea(new Dimension(0, 10)));
    	
    	panelCenter.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				System.out.println(team.getCity());
			}
		});
	}
	
	public WindowHomePlayer(Player player) {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		
		//https://www.discoduroderoer.es/como-crear-pestanas-con-la-clase-jtabbedpane-en-java/
		//Un ejemplo de JTabbedPane, para saber como implementarlo en nuestro código
		JTabbedPane tabbedPanel = new JTabbedPane(); 
		
		JPanel panelProfile = new JPanel(new GridBagLayout());
       
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
					player.setBirthDate(calendar);
					updatePlayer(player);
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

        panelSearch = new JPanel(new BorderLayout());
        
        panelNorthSearch = new JPanel(new GridLayout(1, 2));
        
        textFieldSearch = new JTextField();
        buttonSearch = new JButton("Buscar");
        
        panelSearch.add(panelNorthSearch, BorderLayout.NORTH);
        panelNorthSearch.add(textFieldSearch);
        panelNorthSearch.add(buttonSearch);
        
        List<Team> teams = new ArrayList<>();
        teams.add(new Team("team1", "Bilbao", "Bilbao", "Equipo baloncesto Bilbao.", League.A));
        teams.add(new Team("team1", "Barakaldo", "Barakaldo", "Equipo baloncesto Barakaldo.", League.B));
        teams.add(new Team("team1", "Trapaga", "Trapaga", "Equipo baloncesto Trapaga.", League.C));
        teams.add(new Team("team1", "Sestao", "Sestao", "Equipo baloncesto Sestao.", League.A));
        teams.add(new Team("team1", "Portu", "Portu", "Equipo baloncesto Portu.", League.B));
        teams.add(new Team("team2", "Portu", "Portu", "Equipo baloncesto Portu.", League.C));
        
        panelTeamsSearch = new JPanel();
        panelTeamsSearch.setLayout(new BoxLayout(panelTeamsSearch, BoxLayout.Y_AXIS));
        
        for (Team team : teams) {
        	addTeamPanel(team);
        }
        
        scrollPaneSearch = new JScrollPane(panelTeamsSearch);
        scrollPaneSearch.getVerticalScrollBar().setUnitIncrement(16);
        panelSearch.add(scrollPaneSearch, BorderLayout.CENTER);
        
        tabbedPanel.addTab("Buscador", panelSearch);
        
        buttonSearch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String textSearch = textFieldSearch.getText().toLowerCase();
				panelTeamsSearch.removeAll();
				for (Team team : teams) {
					if (team.getName().toLowerCase().contains(textSearch)) {
						addTeamPanel(team);
					}
				}
				if (panelTeamsSearch.getComponentCount() == 0) {
					JLabel labelNoTeam = new JLabel("No hay ningún equipo con ese nombre.");
					panelTeamsSearch.add(labelNoTeam);
				}
				
				panelTeamsSearch.revalidate();
				panelTeamsSearch.repaint();
			}
		});
        
        JPanel panelGame = new JPanel();
        panelGame.add(new JLabel("Registro de partidos que habéis jugado"));
        tabbedPanel.addTab("Partidos", panelGame);
        add(tabbedPanel);
 
		setVisible(true);
	}
}
