package gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import domain.GameScore;
import domain.FreeThrow;
import domain.Expulsion;
import domain.Basket;
import domain.Foul;
import domain.League;
import domain.Player;
import domain.Team;
import domain.UserRepositoryException;
import db.SQLiteDBManager;

// Vamos a poner una botonera en la parte inferior y luego dividir el resto de la ventana en dos, para que
// en una mitad tengamos un campo de baloncesto en el que interactuar y en la otra, una tabla en la que se
// van registrando todas las acciones que se registran

public class WindowGameRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static JComboBox<String> comboType;
	private static JComboBox<String> comboName;
	private static JComboBox<String> comboName2;
	private static JPanel panelScore;
	private static JPanel panelName;
	private static JPanel panelName2;
	private static List<Player> homeTeam;
	private static List<String> homeNames;
	private static Map<String, Player> homeNamePlayer;
	private static List<Player> guestTeam;
	private static List<String> guestNames;
	private static Map<String, Player> guestNamePlayer;
	private static List<GameScore> incidents = new ArrayList<>();
	
	
	public static void main(String[] args) {
		// Antes de comenzar el partido habrá que establecerlos manualmente desde la app
		// Team home = new Team("HOME");
		// Team guest = new Team("GUEST");
		Team team1 = new Team(4, "team4", "Bilbao 2", "Bilbao Basket 2", "Este es el equipo de Bilbao 2.", League.C);
		Team team2 = new Team(2, "team2", "Trapaga", "Trapaga Basket", "Este es el equipo de Trapaga.", League.B);
		new WindowGameRegister(team1, team2);
	}
	
	// Inicializar listas de equipos con los jugadores correspondientes
	public WindowGameRegister(Team home, Team guest) {
		setSize(960, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Registro de partido");
		
		homeTeam = getPlayerList(home);
		homeNames = getNamesList(homeTeam);
		homeNamePlayer = getNamesWithPlayers(homeTeam);
		guestTeam = getPlayerList(guest);
		guestNames = getNamesList(guestTeam);
		guestNamePlayer = getNamesWithPlayers(guestTeam);
		
		JPanel panelWest = new JPanel(new BorderLayout());
		
		// Panel en el que se visualiza un campo de baloncesto y un label con el nombre de cada equipo
		JPanel panelGame = new JPanel();
		ImageIcon field = new ImageIcon("resources/images/paneGame.png");
		JLabel labelGame = new JLabel(field);
		panelGame.add(labelGame);
		panelGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				Point point = getLocation();
				new RegisterAction(point, home, guest);
			}
		});
		
		panelWest.add(panelGame, BorderLayout.WEST);
		
		JPanel panelNames = new JPanel();
		panelNames.setLayout(new GridLayout(2, 1));
		JLabel labelHome = new JLabel(home.getName());
		JLabel labelGuest = new JLabel(guest.getName());
		panelNames.add(labelHome);
		panelNames.add(labelGuest);
		panelWest.add(panelNames, BorderLayout.EAST);
		
		// Crear una tabla con scroll en la que se regisgraran las acciones
		JPanel panelTable = new JPanel();
		Object[] columnNames = {"id", "category", "autor", "score", "coordinates"};
		GameScore[] data = {
				//new GameScore("11112222A", "1A", "Erik", 2, new Point(0, 0)),
				//new GameScore("33334444B", "2B", "Ander", 3, new Point(1, 0))
		};
		
		JTable tableActions = new JTable();
		tableActions.setModel(new GameTableModel(columnNames, data));
		tableActions.setDefaultRenderer(Object.class, new GameTableRenderer());
		tableActions.getTableHeader().setDefaultRenderer(new GameTableHeaderRenderer());
		JScrollPane scrollPane = new JScrollPane(tableActions);
		panelTable.add(scrollPane);
		
		// Botonera con opciones en la parte de abajo a la derecha
		JPanel panelButtonBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
		JButton buttonFinish = new JButton("Finalizar");
		
		//https://chuidiang.org/index.php?title=Botones_con_icono_y_texto
		//De esta página hemos obtenido el código para insertar la imagen de icono en un botón
		JButton buttonHelp = new JButton();
		buttonHelp.setIcon(new ImageIcon("resources/images/buttonHelp.png"));
		
		buttonHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Se encuentra usted en la ventana de registro de partidos.\n\n"
						+ "A continuación, haga clic sobre las coordenadas del campo en las que esté ocurriendo la acción a registrar.\n"
						+ "En ese momento, se desplegará una ventana en la que podrá meter los datos necesarios de dicha acción y\n"
						+ "ésta quedará registrada.\n\n"
						+ "Cuando termine, podrá usted ver en la parte derecha de la ventana las acciones que ya han sido registradas\n"
						+ "y después de hacer clic sobre ellas, podrá eliminarlas pulsando delete mientras clicas la acción a borrar o\n"
						+ "haciendo ctrl+z para eliminar la más reciente.");
			}
		});
		
		panelButtonBox.add(buttonHelp);
		panelButtonBox.add(buttonFinish);
		add(panelTable, BorderLayout.CENTER);
		add(panelWest, BorderLayout.WEST);
		add(panelButtonBox, BorderLayout.SOUTH);
		setVisible(true);
	}
	
	// Devuelve una lista con los jugadores del equipo
	public static List<Player> getPlayerList(Team team) {
		List<Player> playerList = new ArrayList<>();
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			dbManager.connect("resources/db/rebote.db");
			playerList = dbManager.getPlayersFromTeam(team);
			System.out.println(playerList);
			dbManager.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return playerList;
	}
	
	// Devuelve una lista con los nombres de los jugadores del equipo
	public static List<String> getNamesList(List<Player> playerList) {
		List<String> nameList = new ArrayList<>();
		for (Player player : playerList) {
			nameList.add(player.getName());
		}
		return nameList;
	}
	
	// Devuelve un mapa que relaciona el nombre de un jugador con el objeto jugador
	public static Map<String, Player> getNamesWithPlayers(List<Player> playerList) {
		Map<String, Player> namesWithPlayers = new HashMap<>();
		for (Player player : playerList) {
			namesWithPlayers.put(player.getName(), player);
		}
		return namesWithPlayers;
	}
	
	public static void changeComboBoxContent(List<String> nameList) {
		 comboName.removeAllItems();

		 System.out.println(nameList);
		 
	     // Agregar nuevos elementos al JComboBox
	     for (String name : nameList) {
	    	 comboName.addItem(name);
	     }
	}
	
	public static void changeComboBoxContent2(List<String> nameList) {
		 comboName2.removeAllItems();

		 System.out.println(nameList);
		 
	     // Agregar nuevos elementos al JComboBox
	     for (String name : nameList) {
	    	 comboName2.addItem(name);
	     }
	}
	
	// Cambiar visibilidad del panel para elegir autor
	private static void scoreVisible() {
        String action = (String) comboType.getSelectedItem();
        panelScore.setVisible("Canasta".equals(action));
    }
	
	public static class RegisterAction extends JFrame {
		
		public static String selectedType;
		private static final long serialVersionUID = 1L;
		
		public RegisterAction(Point point, Team home, Team guest) {
			setTitle("Datos de la acción");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(300, 300);
			
			JPanel panelData = new JPanel(new GridLayout(2, 2));
			
			// Cuadrante superior izquierda para poner el tipo de acción
			JPanel panelType = new JPanel(new GridLayout(2, 1));
			JLabel labelType = new JLabel("Tipo de acción:");
			String[] arrayType = {"Seleccione", "Canasta", "Falta", "Expulsión"};
			comboType = new JComboBox<>(arrayType);
			panelType.add(labelType);
			panelType.add(comboType);
			
			comboType.addActionListener(new ActionListener() {
				
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	selectedType = (String) comboType.getSelectedItem();
	                scoreVisible();
	            }
	        });
			
			panelType.add(labelType);
			panelType.add(comboType);
			
			// Cuadrante inferior izquierda para indicar equipo
			JPanel panelTeam = new JPanel(new GridLayout(3, 1));
			JLabel labelTeam = new JLabel("Seleccione equipo:");
			
			JRadioButton radioButtonHome = new JRadioButton(home.getName());
			JRadioButton radioButtonGuest = new JRadioButton(guest.getName());
				
			radioButtonHome.addActionListener(new ActionListener() {
				
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (radioButtonHome.isSelected()) {
	                	if (!panelName.isVisible()) {
	                		panelName.setVisible(true);
	                	}
	                	changeComboBoxContent(homeNames);
	                }
	            }
	        });
			
			radioButtonGuest.addActionListener(new ActionListener() {
				
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (radioButtonGuest.isSelected()) {
	                	if (!panelName.isVisible()) {
	                		panelName.setVisible(true);
	                	}
	                    changeComboBoxContent(guestNames);
	                }
	            }
	        });
	        
	        ButtonGroup BGTeam = new ButtonGroup();
	        BGTeam.add(radioButtonHome);
	        BGTeam.add(radioButtonGuest);
	        panelTeam.add(labelTeam);
	        panelTeam.add(radioButtonHome);
	        panelTeam.add(radioButtonGuest);
	        
	        
	        // Cuadrante superior derecha para seleccionar jugador
	        panelName = new JPanel(new GridLayout(2, 1));
	        panelName.setVisible(false);
	        JLabel labelName = new JLabel("Elija autor:");
	        comboName = new JComboBox<>(homeNames.toArray(new String[0]));
	        panelName.add(labelName);
	        panelName.add(comboName);
	        
	        
	        
	        // Cuadrante inferior derecha para marcar puntuación
	        panelScore = new JPanel(new GridLayout(2, 1));
	        panelScore.setVisible(false);
			JLabel labelScore = new JLabel("Puntuación:");
			JComboBox<Integer> comboScore = new JComboBox<>(new Integer[]{1, 2, 3});
			panelScore.add(labelScore);
			panelScore.add(comboScore);
	        
	        panelData.add(panelType);
	        panelData.add(panelTeam);
	        panelData.add(panelName);
	        panelData.add(panelScore);
	        
	        // Boton de aceptar
	        JPanel panelButtons = new JPanel();
	        JButton buttonConfirm = new JButton("Confirmar");
	        
	        buttonConfirm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if ((radioButtonGuest.isSelected() || radioButtonHome.isSelected())
							&& !(comboType.getSelectedItem() == "Seleccione")) {
						Player player;
						
						if (radioButtonHome.isSelected()) {
							player = homeNamePlayer.get(comboName.getSelectedItem());
						} else {
							player = guestNamePlayer.get(comboName.getSelectedItem());
						}
						
						switch (selectedType) {
							case "Canasta":
								Basket basket = new Basket(player, (int) comboScore.getSelectedItem());
								basket.setCoordinates(point);
								incidents.add(0, basket);
								dispose();
		                        break;
		                    case "Falta":
		                    	Foul foul = new Foul(player);
		                    	incidents.add(0, foul);
		                    	dispose();
		                    	new WindowFreeThrowCheck(home, guest);
		                        break;
		                    case "Expulsión":
		                    	Expulsion expulsion = new Expulsion(player);
		                    	incidents.add(0, expulsion);
		                    	dispose();
		                        break;
						}
					}
				}});
	        
	        panelButtons.add(buttonConfirm);
			
	        add(panelData, BorderLayout.EAST);
	        add(panelButtons, BorderLayout.SOUTH);
			pack();
			
			setLocationRelativeTo(null);
			setAlwaysOnTop(true);
			setVisible(true);
		}
	}
	
	public static class WindowFreeThrow extends JFrame {
		
		public JPanel panelScored;
		public JPanel panelMissed;
		
		private static final long serialVersionUID = 1L;
		
		public WindowFreeThrow(Team home, Team guest) {
			setTitle("Tiro libre");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(300, 300);
			
			JPanel panelData = new JPanel(new GridLayout(2, 2));
			JPanel panelTeam = new JPanel(new GridLayout(3, 1));
			JLabel labelTeam = new JLabel("Seleccione equipo:");
			JRadioButton radioButtonHome = new JRadioButton(home.getName());
			JRadioButton radioButtonGuest = new JRadioButton(guest.getName());
				
			radioButtonHome.addActionListener(new ActionListener() {
				
				@Override
		        public void actionPerformed(ActionEvent e) {
					if (radioButtonHome.isSelected()) {
		              	if (!panelName2.isVisible()) {
		               		panelName2.setVisible(true);
		               	}
		               	changeComboBoxContent2(homeNames);
		            }
		        }
		    });
				
			radioButtonGuest.addActionListener(new ActionListener() {
		    
				@Override
		        public void actionPerformed(ActionEvent e) {
					if (radioButtonGuest.isSelected()) {
		               	if (!panelName2.isVisible()) {
		               		panelName2.setVisible(true);
		               	}
		                changeComboBoxContent2(guestNames);
		            }
		        }
		    });
			
	        ButtonGroup BGTeam = new ButtonGroup();
	        BGTeam.add(radioButtonHome);
	        BGTeam.add(radioButtonGuest);
	        panelTeam.add(labelTeam);
	        panelTeam.add(radioButtonHome);
	        panelTeam.add(radioButtonGuest);
	        
	        // Cuadrante superior derecha para seleccionar jugador
	        panelName2 = new JPanel(new GridLayout(2, 1));
	        panelName2.setVisible(false);
	        JLabel labelName = new JLabel("Elija autor:");
	        comboName2 = new JComboBox<>(homeNames.toArray(new String[0]));
	        
	        comboName2.addActionListener(new ActionListener() {
	        	
	            @Override
	            public void actionPerformed(ActionEvent e) {
                	if (!(panelMissed.isVisible() && panelScored.isVisible())) {
                		panelMissed.setVisible(true);
                		panelScored.setVisible(true);
                	}
	            }
	        });
	        
	        panelName2.add(labelName);
	        panelName2.add(comboName2);
			
			panelScored = new JPanel();
			panelScored.setVisible(false);
			JButton buttonScored = new JButton("Acierto");
			
			buttonScored.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Player player;
					
					if (radioButtonHome.isSelected()) {
						player = homeNamePlayer.get(comboName2.getSelectedItem());
					} else {
						player = guestNamePlayer.get(comboName2.getSelectedItem());
					}
					
					FreeThrow freeThrow = new FreeThrow(player, true);
					incidents.add(freeThrow);
					dispose();
				}
			});
			
			panelScored.add(buttonScored);
			
			panelMissed = new JPanel();
			panelMissed.setVisible(false);
			JButton buttonMissed = new JButton("Fallo");
			
			buttonMissed.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Player player;
					
					if (radioButtonHome.isSelected()) {
						player = homeNamePlayer.get(comboName2.getSelectedItem());
					} else {
						player = guestNamePlayer.get(comboName2.getSelectedItem());
					}
					
					FreeThrow freeThrow = new FreeThrow(player, false);
					incidents.add(freeThrow);
					dispose();
				}
			});
			
			panelMissed.add(buttonMissed);
			
			panelData.add(panelTeam);
			panelData.add(panelName2);
			panelData.add(panelScored);
			panelData.add(panelMissed);
			add(panelData, BorderLayout.NORTH);
			
			setLocationRelativeTo(null);
			setAlwaysOnTop(true);
			setVisible(true);
		}
	}
	
	public static class WindowFreeThrowCheck extends JFrame {
		
		private static final long serialVersionUID = 1L;

		public WindowFreeThrowCheck(Team home, Team guest) {
			setTitle("Comprobación de tiros libres");
			setSize(300, 100);
			setLayout(new GridLayout(2, 1));
			 
			JPanel panelText = new JPanel();
			JLabel labelScore = new JLabel("¿Se han causado tiros libres?");
			panelText.add(labelScore);
			 
			JPanel panelButtons = new JPanel();
			JButton buttonNo = new JButton("NO");
		    JButton button1 = new JButton("1");
		    JButton button2 = new JButton("2");
		    JButton button3 = new JButton("3");
		     
		    panelButtons.add(buttonNo);
		    panelButtons.add(button1);
		    panelButtons.add(button2);
		    panelButtons.add(button3);
		     
		    buttonNo.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					 dispose();
				}
			});
		     
			button1.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					displayFreeThrow(1, home, guest);
				}
			});
			 
			button2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					displayFreeThrow(2, home, guest);
				}
			});
			 
			button3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					displayFreeThrow(3, home, guest);
				}
			});
			
		    add(panelText);
		    add(panelButtons);
		    
		    setLocationRelativeTo(null);
		    setAlwaysOnTop(true);
			setVisible(true);
		}
		
		public static void displayFreeThrow(int reps, Team home, Team guest) {
			for (int i = 0; i < reps; i++) {
				new WindowFreeThrow(home, guest);
			}
		}
	}
}
