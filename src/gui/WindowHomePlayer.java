package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
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
import domain.Game;
import domain.League;
import domain.Player;
import domain.Team;
import domain.UserRepositoryException;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WindowHomePlayer extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel panelTeam;
	private JPanel panelTeams;
	private JPanel panelGames;
	private JPanel panelNorthSearch;
	private JPanel panelTeamsSearch;
	private JPanel panelNorthGames;
	private JPanel panelGamesSearch;
	private JTextField textFieldTeams;
	private JTextField textFieldGames;
	private JButton buttonSearchTeams;
	private JButton buttonSearchGames;
	private JScrollPane scrollPaneTeams;
	private JScrollPane scrollPaneGames;
	
	private static void updatePlayer(Player player) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			dbManager.updatePlayer(player);
		} catch (UserRepositoryException e) {
			e.printStackTrace();
		}
	}
	
	private void addTeamPanel(Team team, Player player) {
		JPanel panelCenterTeams = new JPanel(new GridLayout(1, 3));
		panelCenterTeams.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelCenterTeams.setMaximumSize(new Dimension(this.getWidth() - 50, 100));
		panelCenterTeams.setPreferredSize(new Dimension(this.getWidth() - 50, 100));
    	panelCenterTeams.setBackground(Color.WHITE);
    	JLabel labelIcon = new JLabel();
    	ImageIcon icon = new ImageIcon("src/img/" + team.getLeague() + ".png");
    	labelIcon.setIcon(icon);
    	JLabel labelNameSearch = new JLabel(team.getName());
    	JLabel labelCitySearch = new JLabel(team.getCity());
    	panelCenterTeams.add(labelIcon);
    	panelCenterTeams.add(labelNameSearch);
    	panelCenterTeams.add(labelCitySearch);
    	panelTeamsSearch.add(panelCenterTeams);
    	panelTeamsSearch.add(Box.createRigidArea(new Dimension(0, 10)));
    	
    	panelCenterTeams.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new WindowTeam(team, player);
				dispose();
			}
		});
	}
	
	private void addGamePanel(Game game) {
		JPanel panelCenterGames = new JPanel(new BorderLayout());
		panelCenterGames.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelCenterGames.setMaximumSize(new Dimension(this.getWidth() - 50, 150));
		panelCenterGames.setPreferredSize(new Dimension(this.getWidth() - 50, 150));
    	panelCenterGames.setBackground(Color.WHITE);
    	SQLiteDBManager dbManager = new SQLiteDBManager();
    	try {
    		System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			Team team1 = dbManager.getTeam(game.getTeam1());
			JPanel team1Panel = new JPanel(new GridLayout(2, 2));
			team1Panel.setBackground(Color.WHITE);
			JLabel team1NameLabel = new JLabel(team1.getName().toUpperCase());
			JLabel team1LeagueLabel = new JLabel();
			ImageIcon team1Icon = new ImageIcon("src/img/" + team1.getLeague() + ".png");
			team1LeagueLabel.setIcon(team1Icon);
			JLabel team1PointsLabel = new JLabel("P: " + game.getTeamScore1());
			JLabel team1FoultsLabel = new JLabel("F: " + game.getTeamFoults1());
			team1Panel.add(team1NameLabel);
			team1Panel.add(team1LeagueLabel);
			team1Panel.add(team1PointsLabel);
			team1Panel.add(team1FoultsLabel);
			panelCenterGames.add(team1Panel, BorderLayout.WEST);
			
			JLabel vsLabel = new JLabel("VS");
			vsLabel.setFont(new Font("Agency FB", Font.BOLD, 40));
			vsLabel.setHorizontalAlignment(JLabel.CENTER);
			panelCenterGames.add(vsLabel, BorderLayout.CENTER);
			
			Team team2 = dbManager.getTeam(game.getTeam2());
			JPanel team2Panel = new JPanel(new GridLayout(2, 2));
			team2Panel.setBackground(Color.WHITE);
			JLabel team2NameLabel = new JLabel(team2.getName().toUpperCase());
			JLabel team2LeagueLabel = new JLabel();
			ImageIcon team2Icon = new ImageIcon("src/img/" + team2.getLeague() + ".png");
			team2LeagueLabel.setIcon(team2Icon);
			JLabel team2PointsLabel = new JLabel("P: " + game.getTeamScore2());
			JLabel team2FoultsLabel = new JLabel("F: " + game.getTeamFoults2());
			team2Panel.add(team2NameLabel);
			team2Panel.add(team2LeagueLabel);
			team2Panel.add(team2PointsLabel);
			team2Panel.add(team2FoultsLabel);
			panelCenterGames.add(team2Panel, BorderLayout.EAST);
			
			panelGamesSearch.add(panelCenterGames);
	    	panelGamesSearch.add(Box.createRigidArea(new Dimension(0, 10)));
    	} catch (UserRepositoryException e) {
    		System.out.println("No se ha podido acceder a la base de datos.");
    		e.printStackTrace();
    	}
	}
	
	public WindowHomePlayer(Player player) {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		if (player.getTeam() != null) {
			setIconImage(new ImageIcon("src/img/" + player.getTeam().getLeague() + ".png").getImage());
		}
		
		
		//https://www.discoduroderoer.es/como-crear-pestanas-con-la-clase-jtabbedpane-en-java/
		//Un ejemplo de JTabbedPane, para saber como implementarlo en nuestro código
		JTabbedPane tabbedPanel = new JTabbedPane(); 
		
		
		// Tu perfil
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
        
        
        // Tu equipo
        panelTeam = new JPanel(new BorderLayout());
        
        Team playerTeam = player.getTeam();
        
        JLabel labelNoTeam = new JLabel("No estás inscrito en ningún equipo");
    	labelNoTeam.setHorizontalAlignment(JLabel.CENTER);
    	labelNoTeam.setFont(new Font("Agency FB", Font.BOLD, 20));
        
        if (playerTeam != null) {
        	JLabel labelTeamStuff = new JLabel(String.format("<html>Ciudad: %s&#9;Estadio: %s<br/><br/>Descripción: %s</html>",
    				playerTeam.getCity(),
    				playerTeam.getStadium(),
    				playerTeam.getDescription()));
        	labelTeamStuff.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
        	panelTeam.add(labelTeamStuff, BorderLayout.NORTH);
        	
        	JButton leaveButton = new JButton("Salir del equipo");
        	panelTeam.add(leaveButton, BorderLayout.SOUTH);
        	
        	leaveButton.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						System.out.println("Conectando con la base de datos...");
						dbManager.connect("src/db/rebote.db");
						System.out.println("Has salido del equipo " + player.getTeam().getName());
						player.setTeam(null);
						dbManager.updatePlayer(player);
						panelTeam.remove(labelTeamStuff);
						panelTeam.remove(leaveButton);
						panelTeam.add(labelNoTeam);
						WindowHomePlayer.this.setIconImage(null);
						panelTeam.revalidate();
						panelTeam.repaint();
					} catch (UserRepositoryException e1) {
						System.out.println("No se ha podido acceder a la base de datos.");
						e1.printStackTrace();
					}
				}
			});
        } else {
        	panelTeam.add(labelNoTeam, BorderLayout.CENTER);
        }
        
        tabbedPanel.addTab("Tu equipo", panelTeam);

        
        // Buscador
        panelTeams = new JPanel(new BorderLayout());
        
        panelNorthSearch = new JPanel(new GridLayout(1, 2));
        
        textFieldTeams = new JTextField();
        buttonSearchTeams = new JButton("Buscar");
        
        panelTeams.add(panelNorthSearch, BorderLayout.NORTH);
        panelNorthSearch.add(textFieldTeams);
        panelNorthSearch.add(buttonSearchTeams);
        
        try {
        	System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			List<Team> teams = new ArrayList<>(dbManager.getAllTeams());
        
	        panelTeamsSearch = new JPanel();
	        panelTeamsSearch.setLayout(new BoxLayout(panelTeamsSearch, BoxLayout.Y_AXIS));
	        
	        for (Team team : teams) {
	        	addTeamPanel(team, player);
	        }
	        
	        scrollPaneTeams = new JScrollPane(panelTeamsSearch);
	        scrollPaneTeams.getVerticalScrollBar().setUnitIncrement(16);
	        panelTeams.add(scrollPaneTeams, BorderLayout.CENTER);
	        
	        tabbedPanel.addTab("Buscador", panelTeams);
	        
	        buttonSearchTeams.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String textSearch = textFieldTeams.getText().toLowerCase();
					panelTeamsSearch.removeAll();
					for (Team team : teams) {
						if (team.getName().toLowerCase().contains(textSearch)) {
							addTeamPanel(team, player);
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
        } catch (UserRepositoryException e) {
        	System.out.println("No se ha podido acceder a la base de datos.");
        	e.printStackTrace();
        }
        
        
        // Partidos
        panelGames = new JPanel(new BorderLayout());
        
        panelNorthGames = new JPanel(new GridLayout(1, 2));
        
        textFieldGames = new JTextField();
        buttonSearchGames = new JButton("Buscar");
        
        panelGames.add(panelNorthGames, BorderLayout.NORTH);
        panelNorthGames.add(textFieldGames);
        panelNorthGames.add(buttonSearchGames);
        
        try {
        	System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			Comparator<Game> gameComparator = (game1, game2) -> {return Integer.compare(game1.getId(), game2.getId()) * -1;};
			Set<Game> games = new TreeSet<>(gameComparator);
			games.addAll(dbManager.getAllGames());
        
	        panelGamesSearch = new JPanel();
	        panelGamesSearch.setLayout(new BoxLayout(panelGamesSearch, BoxLayout.Y_AXIS));
	        
	        for (Game game : games) {
	        	addGamePanel(game);
	        }
	        
	        scrollPaneGames = new JScrollPane(panelGamesSearch);
	        scrollPaneGames.getVerticalScrollBar().setUnitIncrement(16);
	        panelGames.add(scrollPaneGames, BorderLayout.CENTER);
	        
	        tabbedPanel.addTab("Partidos", panelGames);
	        
	        buttonSearchGames.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					String textSearch = textFieldGames.getText().toLowerCase();
					panelGamesSearch.removeAll();
					for (Game game : games) {
						try {
							Team team1 = dbManager.getTeam(game.getTeam1());
							Team team2 = dbManager.getTeam(game.getTeam2());
							if (team1.getName().toLowerCase().contains(textSearch) || team2.getName().toLowerCase().contains(textSearch)) {
								addGamePanel(game);
							}
						} catch (UserRepositoryException e1) {
							System.out.println("No se ha podido acceder a la base de datos");
							e1.printStackTrace();
						}
					}
					if (panelGamesSearch.getComponentCount() == 0) {
						JLabel labelNoGame = new JLabel("No hay ningún partido.");
						panelGamesSearch.add(labelNoGame);
					}
					
					panelGamesSearch.revalidate();
					panelGamesSearch.repaint();
				}
			});
        } catch (UserRepositoryException e) {
        	System.out.println("No se ha podido acceder a la base de datos.");
        	e.printStackTrace();
        }
        add(tabbedPanel);
		setVisible(true);
	}
}
