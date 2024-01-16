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
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;
import java.util.logging.Logger;

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

import collections.ReboteCollections;
import db.SQLiteDBManager;
import domain.Game;
import domain.Team;
import domain.Trainer;
import domain.UserRepositoryException;
import io.ConfigReader;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class WindowHomeTrainer extends JFrame {

	static Logger logger = Logger.getLogger(WindowStart.class.getName());
	private static final long serialVersionUID = 1L;
	
	private static Path dbPath = Paths.get("resources/db/rebote.db");
	
	private JButton buttonLogout;
	private JPanel panelTeam;
	private JPanel panelNorthTeam;
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
	
	private static void updateTrainer(Trainer trainer) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			dbManager.connect(dbPath.toString());
			dbManager.updateTrainer(trainer);
		} catch (UserRepositoryException e) {
			logger.warning(ConfigReader.dbConnectError);
		}
	}
	
	private static void updateTeam(Team team) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			dbManager.connect(dbPath.toString());
			dbManager.updateTeam(team);
		} catch (UserRepositoryException e) {
			logger.warning(ConfigReader.dbConnectError);
		}
	}
	
	private void addTeamPanel(Team team, Trainer trainer) {
		JPanel panelCenterTeams = new JPanel(new GridLayout(1, 3));
		panelCenterTeams.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));
		panelCenterTeams.setMaximumSize(new Dimension(this.getWidth() - 50, 100));
		panelCenterTeams.setPreferredSize(new Dimension(this.getWidth() - 50, 100));
    	panelCenterTeams.setBackground(Color.WHITE);
    	JLabel labelIcon = new JLabel();
    	ImageIcon icon = new ImageIcon("resources/images/" + team.getLeague() + ".png");
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
				try {
					new WindowTeam(team, trainer);
				} catch (Exception e1) {
					logger.warning("No se ha podido acceder al equipo.");
				}
				dispose();
				logger.info("Abierto el equipo: " + team.getName());
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
			dbManager.connect(dbPath.toString());
			
			Team team1 = dbManager.getTeam(game.getTeam1());
			JPanel team1Panel = new JPanel(new GridLayout(2, 2));
			team1Panel.setBackground(Color.WHITE);
			JLabel team1NameLabel = new JLabel(team1.getName().toUpperCase());
			JLabel team1LeagueLabel = new JLabel();
			ImageIcon team1Icon = new ImageIcon("resources/images/" + team1.getLeague() + ".png");
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
			ImageIcon team2Icon = new ImageIcon("resources/images/" + team2.getLeague() + ".png");
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
    		logger.warning(ConfigReader.dbConnectError);
    	}
	}
	
	public WindowHomeTrainer(Trainer trainer) throws Exception {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		setLayout(new BorderLayout());
		if (trainer.getTeam() != null) {
			setIconImage(new ImageIcon("resources/images/" + trainer.getTeam().getLeague() + ".png").getImage());
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
		        JLabel labelUsername = new JLabel("Nombre de usuario: " + trainer.getUsername());
		        JLabel labelName = new JLabel("Nombre: " + trainer.getName());
				JLabel labelFirstSurname = new JLabel("Primer apellido: " + trainer.getFirstSurname());
				JLabel labelSecondSurname = new JLabel("Segundo apellido: " + trainer.getSecondSurname());
				JLabel labelPassword = new JLabel("Contraseña: " + trainer.getPassword());
				JLabel labelBirthDate = new JLabel("Fecha de nacimiento: " + dbManager.calendarToString(trainer.getBirthDate()));
				JLabel labelCountry = new JLabel("País: " + trainer.getCountry());
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
							trainer.setUsername(username);
							updateTrainer(trainer);
							labelUsername.setText("Nombre de usuario: " + username);
							logger.info("Actualizado el nombre de usuario a: " + username);
						}
					}
				});
		        
		        buttonChangeName.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String name = JOptionPane.showInputDialog(null, "Ingresa tu nuevo nombre:", "Nombre", JOptionPane.PLAIN_MESSAGE);
						if (name != null && !name.isBlank()) {
							trainer.setName(name);
							updateTrainer(trainer);
							labelName.setText("Nombre: " + name);
							logger.info("Actualizado el nombre a: " + name);
						}
					}
				});
		        
		        buttonChangeFirstSurname.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String firstSurname = JOptionPane.showInputDialog(null, "Ingresa tu nuevo primer apellido:", "Primer apellido", JOptionPane.PLAIN_MESSAGE);
						if (firstSurname != null && !firstSurname.isBlank()) {
							trainer.setFirstSurname(firstSurname);
							updateTrainer(trainer);
							labelFirstSurname.setText("Primer apellido: " + firstSurname);
							logger.info("Actualizado el primer apellido a: " + firstSurname);
						}
					}
				});
		        
		        buttonChangeSecondSurname.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String secondSurname = JOptionPane.showInputDialog(null, "Ingresa tu nuevo segundo apellido:", "Segundo apellido", JOptionPane.PLAIN_MESSAGE);
						if (secondSurname != null && !secondSurname.isBlank()) {
							trainer.setSecondSurname(secondSurname);
							updateTrainer(trainer);
							labelSecondSurname.setText("Segundo apellido: " + secondSurname);
							logger.info("Actualizado el segundo apellido a: " + secondSurname);
						}
					}
				});
		        
		        buttonChangePassword.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						String password = JOptionPane.showInputDialog(null, "Ingresa tu nueva contraseña:", "Contraseña", JOptionPane.PLAIN_MESSAGE);
						if (password != null && !password.isBlank()) {
							trainer.setPassword(password);
							updateTrainer(trainer);
							labelPassword.setText("Contraseña: " + password);
							logger.info("Actualizada la contraseña a: " + password);
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
							trainer.setBirthDate(calendar);
							updateTrainer(trainer);
							labelBirthDate.setText("Fecha de nacimiento: " + dbManager.calendarToString(calendar));
							logger.info("Actualizada la fecha de nacimiento a: " + dbManager.calendarToString(calendar));
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
							trainer.setCountry(country);
							updateTrainer(trainer);
							labelCountry.setText("País: " + country);
							logger.info("Actualizado el país a: " + country);
						}
					}
				});
		        
		        
		     // Tu equipo
		        panelTeam = new JPanel(new BorderLayout());
		        
		        Team trainerTeam = trainer.getTeam();
		        
		        JLabel labelNoTeam = new JLabel("No estás inscrito en ningún equipo");
		    	labelNoTeam.setHorizontalAlignment(JLabel.CENTER);
		    	labelNoTeam.setFont(new Font("Agency FB", Font.BOLD, 20));
		        
		        if (trainerTeam != null) {
		        	JLabel labelNameTeam = new JLabel("Nombre: " + trainerTeam.getName());
		        	JLabel labelCityTeam = new JLabel("Ciudad: " + trainerTeam.getCity());
		        	JLabel labelStadiumTeam = new JLabel("Estadio: " + trainerTeam.getStadium());
		        	JLabel labelDescriptionTeam = new JLabel("Descripción: " + trainerTeam.getDescription());
		        	JButton buttonChangeNameTeam = new JButton("Cambiar");
		        	JButton buttonChangeCityTeam = new JButton("Cambiar");
		        	JButton buttonChangeStadiumTeam = new JButton("Cambiar");
		        	JButton buttonChangeDescriptionTeam = new JButton("Cambiar");
		        	
		        	Map<Integer, Integer> gamesPerTeam = ReboteCollections.gamesPlayedPerTeam(dbPath);
		    		int gamesPlayed = gamesPerTeam.get(trainerTeam.getId());
		    		Integer value = gamesPerTeam.values().iterator().next();
		    		int gamesPlayedTop1 = value;
		    		String isInTop1;
		    		if (gamesPlayed >= gamesPlayedTop1) {
		    			isInTop1 = "Sí";
		    		} else {
		    			isInTop1 = "No";
		    		}
		    		JLabel labelStuff = new JLabel(String.format("<html>Partidos jugados: %d<br/><br/>Partidos ganados: %d<br/><br/>Partidos perdidos: %d<br/><br/>¿Es el equipo con más partidos jugados? %s</html>",
		    				ReboteCollections.gamesPlayedPerTeam(dbPath).get(trainerTeam.getId()),
		    				ReboteCollections.gamesWin(trainerTeam, dbPath).size(),
		    				ReboteCollections.gamesLoseOrTie(trainerTeam, dbPath).size(),
		    				isInTop1));
		        	panelNorthTeam = new JPanel(new GridBagLayout());
		        	
		        	gbc.gridy = 0;
		        	gbc.gridx = 0;
		        	
		        	panelNorthTeam.add(labelNameTeam, gbc);
		        	gbc.gridy = 1;
		        	panelNorthTeam.add(labelCityTeam, gbc);
		        	gbc.gridy = 2;
		        	panelNorthTeam.add(labelStadiumTeam, gbc);
		        	gbc.gridy = 3;
		        	panelNorthTeam.add(labelDescriptionTeam, gbc);
		        	gbc.gridy = 4;
		        	panelNorthTeam.add(labelStuff, gbc);
		        	gbc.gridx = 1;
		        	gbc.gridy = 0;
		        	panelNorthTeam.add(buttonChangeNameTeam, gbc);
		        	gbc.gridy = 1;
		        	panelNorthTeam.add(buttonChangeCityTeam, gbc);
		        	gbc.gridy = 2;
		        	panelNorthTeam.add(buttonChangeStadiumTeam, gbc);
		        	gbc.gridy = 3;
		        	panelNorthTeam.add(buttonChangeDescriptionTeam, gbc);
		        	
		        	panelTeam.add(panelNorthTeam, BorderLayout.CENTER);
		        	
		        	JButton leaveButton = new JButton("Salir del equipo");
		        	panelTeam.add(leaveButton, BorderLayout.SOUTH);
		        	
		        	buttonChangeNameTeam.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							String name = JOptionPane.showInputDialog(null, "Ingresa el nuevo nombre:", "Nombre", JOptionPane.PLAIN_MESSAGE);
							if (name != null && !name.isBlank()) {
								trainerTeam.setName(name);
								updateTeam(trainerTeam);
								labelNameTeam.setText("Nombre: " + name);
								logger.info("Actualizado el nombre a: " + name);
							}
						}
					});
		        	
		        	buttonChangeCityTeam.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							String city = JOptionPane.showInputDialog(null, "Ingresa la nueva ciudad:", "Ciudad", JOptionPane.PLAIN_MESSAGE);
							if (city != null && !city.isBlank()) {
								trainerTeam.setCity(city);
								updateTeam(trainerTeam);
								labelCityTeam.setText("Ciudad: " + city);
								logger.info("Actualizada la ciudad a: " + city);
							}
						}
					});
		        	
		        	buttonChangeStadiumTeam.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							String stadium = JOptionPane.showInputDialog(null, "Ingresa el nuevo estadio:", "Estadio", JOptionPane.PLAIN_MESSAGE);
							if (stadium != null && !stadium.isBlank()) {
								trainerTeam.setStadium(stadium);
								updateTeam(trainerTeam);
								labelStadiumTeam.setText("Estadio: " + stadium);
								logger.info("Actualizado el estadio a: " + stadium);
							}
						}
					});
		        	
		        	buttonChangeDescriptionTeam.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							String description = JOptionPane.showInputDialog(null, "Ingresa la nueva descripción:", "Descripción", JOptionPane.PLAIN_MESSAGE);
							if (description != null && !description.isBlank()) {
								trainerTeam.setDescription(description);
								updateTeam(trainerTeam);
								labelDescriptionTeam.setText("Descripción: " + description);
								logger.info("Actualizada la descripción a: " + description);
							}
						}
					});
		        	
		        	leaveButton.addActionListener(new ActionListener() {
						
						@Override
						public void actionPerformed(ActionEvent e) {
							try {
								dbManager.connect(dbPath.toString());
								logger.info("Has salido del equipo " + trainer.getTeam().getName());
								trainer.setTeam(null);
								dbManager.updateTrainer(trainer);
								panelNorthTeam.remove(labelNameTeam);
								panelNorthTeam.remove(labelCityTeam);
								panelNorthTeam.remove(labelStadiumTeam);
								panelNorthTeam.remove(labelDescriptionTeam);
								panelNorthTeam.remove(buttonChangeNameTeam);
								panelNorthTeam.remove(buttonChangeCityTeam);
								panelNorthTeam.remove(buttonChangeStadiumTeam);
								panelNorthTeam.remove(buttonChangeDescriptionTeam);
								panelTeam.remove(leaveButton);
								panelTeam.remove(panelNorthTeam);
								panelTeam.add(labelNoTeam, BorderLayout.CENTER);
								WindowHomeTrainer.this.setIconImage(null);
								tabbedPanel.revalidate();
								tabbedPanel.repaint();
							} catch (UserRepositoryException e1) {
								logger.warning(ConfigReader.dbConnectError);
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
					dbManager.connect(dbPath.toString());
					List<Team> teams = new ArrayList<>(dbManager.getAllTeams());
		        
			        panelTeamsSearch = new JPanel();
			        panelTeamsSearch.setLayout(new BoxLayout(panelTeamsSearch, BoxLayout.Y_AXIS));
			        
			        for (Team team : teams) {
			        	addTeamPanel(team, trainer);
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
									addTeamPanel(team, trainer);
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
		        	logger.warning(ConfigReader.dbConnectError);
		        }
		        
		        
		        // Partidos
		        if (trainerTeam != null) {
			        panelGames = new JPanel(new BorderLayout());
			        
			        panelNorthGames = new JPanel(new GridLayout(1, 2));
			        
			        textFieldGames = new JTextField();
			        buttonSearchGames = new JButton("Buscar");
			        
			        panelGames.add(panelNorthGames, BorderLayout.NORTH);
			        panelNorthGames.add(textFieldGames);
			        panelNorthGames.add(buttonSearchGames);
			        
			        try {
						dbManager.connect(dbPath.toString());
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
										logger.warning(ConfigReader.dbConnectError);
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
			        	logger.warning(ConfigReader.dbConnectError);
			        }
		        }
        add(tabbedPanel, BorderLayout.CENTER);
        
        buttonLogout = new JButton("Cerrar sesión");
	    
	    buttonLogout.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new WindowStart();
				logger.info("Cerrada la sesión.");
			}
		});
	    
	    add(buttonLogout, BorderLayout.SOUTH);
		setVisible(true);
	}
	
}
