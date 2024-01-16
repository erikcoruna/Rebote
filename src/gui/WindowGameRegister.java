package gui;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
import domain.Player;
import domain.Team;
import db.SQLiteDBManager;

// Vamos a poner una botonera en la parte inferior y luego dividir el resto de la ventana en dos, para que
// en una mitad tengamos un campo de baloncesto en el que interactuar y en la otra, una tabla en la que se
// van registrando todas las acciones que se registran

public class WindowGameRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static GameTableModel gameTableModel;
	private static JComboBox<String> comboType;
	private static JComboBox<String> comboName;
	private static Team homeObject;
	private static Team guestObject;
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
	private static Map<Player, Integer> mapHomeFouls;
	private static Map<Player, Integer> mapGuestFouls;
	private static List<Player> homeExpelled = new ArrayList<>();
	private static List<Player> guestExpelled = new ArrayList<>();
	
	// Inicializar listas de equipos con los jugadores correspondientes
	public WindowGameRegister(Team home, Team guest) {
		setSize(960, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Registro de partido");
		
		homeObject = home;
		guestObject = guest;
		
		homeTeam = getPlayerList(home);
		homeNames = getNamesList(homeTeam);
		homeNamePlayer = getNamesWithPlayers(homeTeam);
		mapHomeFouls = startPlayerWithFouls(homeTeam);
		guestTeam = getPlayerList(guest);
		guestNames = getNamesList(guestTeam);
		guestNamePlayer = getNamesWithPlayers(guestTeam);
		mapGuestFouls = startPlayerWithFouls(guestTeam);
		
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
		
		JTable tableActions = new JTable();
		gameTableModel = new GameTableModel(columnNames, incidents);
		tableActions.setModel(gameTableModel);
		tableActions.setDefaultRenderer(Object.class, new GameTableRenderer());
		tableActions.getTableHeader().setDefaultRenderer(new GameTableHeaderRenderer());
		JScrollPane scrollPane = new JScrollPane(tableActions);
		tableActions.setRowHeight(30);
		tableActions.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				 if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_Z) {
					 if (incidents.get(0) instanceof Expulsion) {
						 Player toReInsertPlayer = incidents.get(0).getAuthor();
						 reInsert(toReInsertPlayer);
					 }
					 incidents.remove(0);
					 gameTableModel.fireTableDataChanged();
				 }
			}
		});
		panelTable.add(scrollPane);
		
		// Botonera con opciones en la parte de abajo a la derecha
		JPanel panelButtonBox = new JPanel(new FlowLayout(FlowLayout.RIGHT));
	
		JButton buttonFinish = new JButton("Finalizar");
		
		buttonFinish.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
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
	
	// Devuelve un mapa que indica cada jugador con el número de faltas que lleva
	public static Map<Player, Integer> startPlayerWithFouls(List<Player> playerList) {
		Map<Player, Integer> result = new HashMap<>();
		for (Player player : playerList) {
			result.put(player, 0);
		}
		return result;
	}
	
	public static void checkExpulsion(Map<Player, Integer> playerFouls) {
		for (Player player : playerFouls.keySet()) {
			if (playerFouls.get(player) >= 5) {
				Expulsion expulsion = new Expulsion(player);
            	incidents.add(0, expulsion);
            	gameTableModel.fireTableDataChanged();
            	expell(player);
			}
		}
	}
	
	// Expulsar a un jugador eliminándolo de todos los colections
	public static void expell(Player player) {
		Team hisTeam = player.getTeam();
		if (hisTeam.getId() == homeObject.getId()) {
			homeTeam.remove(player);
			homeNames.remove(player.getName());
			homeNamePlayer.remove(player.getName());
			mapHomeFouls.remove(player);
			homeExpelled.add(0, player);
		} else if (hisTeam.getId() == guestObject.getId()) {
			guestTeam.remove(player);
			guestNames.remove(player.getName());
			guestNamePlayer.remove(player.getName());
			mapGuestFouls.remove(player);
			guestExpelled.add(0, player);
		}
	}
	
	// Volver a añadir un jugador eliminado
	public static void reInsert(Player player) {
		Team hisTeam = player.getTeam();
		if (hisTeam.getId() == homeObject.getId()) {
			homeTeam.add(player);
			homeNames.add(player.getName());
			homeNamePlayer.put(player.getName(), player);
			mapHomeFouls.put(player, 4);
			homeExpelled.remove(0);
		} else if (hisTeam.getId() == guestObject.getId()) {
			guestTeam.add(player);
			guestNames.add(player.getName());
			guestNamePlayer.put(player.getName(), player);
			mapGuestFouls.put(player, 4);
			guestExpelled.remove(0);
		}
	}
	
	public static void changeComboBoxContent(List<String> nameList) {
		 comboName.removeAllItems();
		 
	     // Agregar nuevos elementos al JComboBox
	     for (String name : nameList) {
	    	 comboName.addItem(name);
	     }
	}
	
	public static void changeComboBoxContent(JComboBox<String> comboBox, List<String> nameList) {
		comboBox.removeAllItems();
		 
	     // Agregar nuevos elementos al JComboBox
	     for (String name : nameList) {
	    	 comboBox.addItem(name);
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
								gameTableModel.fireTableDataChanged();
								dispose();
		                        break;
		                        
		                    case "Falta":
		                    	Foul foul = new Foul(player);
		                    	incidents.add(0, foul);
		                    	gameTableModel.fireTableDataChanged();
		                    	if (radioButtonHome.isSelected()) {
		                    		int numeroFaltas = mapHomeFouls.get(player) + 1;
		                    		mapHomeFouls.put(player, numeroFaltas);
		                    		checkExpulsion(mapHomeFouls);
		                    	} else {
		                    		int numeroFaltas = mapGuestFouls.get(player) + 1;
		                    		mapGuestFouls.put(player, numeroFaltas);
		                    		checkExpulsion(mapGuestFouls);
		                    	}
		                    	dispose();
		                    	new WindowFreeThrowCheck(home, guest, foul);
		                        break;
		                        
		                    case "Expulsión":
		                    	Expulsion expulsion = new Expulsion(player);
		                    	incidents.add(0, expulsion);
		                    	gameTableModel.fireTableDataChanged();
		                    	dispose();
		                    	expell(player);
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
		private JComboBox<String> comboName2;
		
		private static final long serialVersionUID = 1L;
		
		public WindowFreeThrow(Team home, Team guest, Foul foul) {
			setTitle("Tiro libre");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(300, 300);
			Player foulerPlayer = foul.getAuthor();
			Team foulerTeam = foulerPlayer.getTeam();
			
			JPanel panelData = new JPanel(new GridLayout(2, 1));
			
	        // Parte superior para seleccionar jugador
	        panelName2 = new JPanel(new GridLayout(2, 1));
	        panelName2.setVisible(true);
	        JLabel labelName = new JLabel("Elija autor:");
	        comboName2 = new JComboBox<>(new String[0]);
	        
	        if (foulerTeam.getId() == home.getId()) {
	        	changeComboBoxContent(comboName2, guestNames);
	        } else if (foulerTeam.getId() == guest.getId()){
	        	changeComboBoxContent(comboName2, homeNames);
	        }
	        
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
	        
	        
			JPanel panelButtons = new JPanel(new GridLayout(1,2));
			panelScored = new JPanel();
			panelScored.setVisible(false);
			JButton buttonScored = new JButton("Acierto");
			
			buttonScored.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Player player = new Player();
					
					if (foulerTeam.getId() == guest.getId()) {
						player = homeNamePlayer.get(comboName2.getSelectedItem());
					} else if (foulerTeam.getId() == home.getId()){
						player = guestNamePlayer.get(comboName2.getSelectedItem());
					}
					
					FreeThrow freeThrow = new FreeThrow(player, true);
					incidents.add(0,freeThrow);
					gameTableModel.fireTableDataChanged();
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
					Player player = new Player();
					
					if (foulerTeam.getId() == guest.getId()) {
						player = homeNamePlayer.get(comboName2.getSelectedItem());
					} else if (foulerTeam.getId() == home.getId()){
						player = guestNamePlayer.get(comboName2.getSelectedItem());
					}
					
					FreeThrow freeThrow = new FreeThrow(player, false);
					incidents.add(0, freeThrow);
					gameTableModel.fireTableDataChanged();
					dispose();
				}
			});
			
			panelMissed.add(buttonMissed);
			
			panelButtons.add(buttonScored);
			panelButtons.add(buttonMissed);
			panelData.add(panelName2);
			panelData.add(panelButtons);
			
			add(panelData, BorderLayout.NORTH);
			pack();
			setLocationRelativeTo(null);
			setAlwaysOnTop(true);
			setVisible(true);
		}
	}
	
	public static class WindowFreeThrowCheck extends JFrame {
		
		private static final long serialVersionUID = 1L;

		public WindowFreeThrowCheck(Team home, Team guest, Foul foul) {
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
					displayFreeThrow(1, home, guest, foul);
				}
			});
			 
			button2.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					displayFreeThrow(2, home, guest, foul);
				}
			});
			 
			button3.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
					displayFreeThrow(3, home, guest, foul);
				}
			});
			
		    add(panelText);
		    add(panelButtons);
		    
		    setLocationRelativeTo(null);
		    setAlwaysOnTop(true);
			setVisible(true);
		}
		
		public static void displayFreeThrow(int reps, Team home, Team guest, Foul foul) {
			for (int i = 0; i < reps; i++) {
				new WindowFreeThrow(home, guest, foul);
			}
		}
	}
}
