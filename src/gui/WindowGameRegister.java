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

		//Vamos a poner una botonera en la parte inferior y luego dividir el resto de la ventana en dos, para que
		//en una mitad tengamos un campo de baloncesto en el que interactuar y en la otra, una tabla en la que se
		//van registrando todas las acciones que se registran
public class WindowGameRegister extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static JComboBox<String> comboTipe;
	private static JComboBox<String> comboNumber;
	private static JPanel panelScore;
	private static JPanel panelNumber;
	static ArrayList<Player> homeTeam;
	static ArrayList<String> homeNumbers;
	static HashMap<String, Player> homeNumberPlayer;
	static ArrayList<Player> guestTeam;
	static ArrayList<String> guestNumbers;
	static HashMap<String, Player> guestNumberPlayer;
	static ArrayList<GameScore> incidents = new ArrayList<GameScore>();
	
	
	public static void main(String[] args) {
		//antes de comenzar el partido habrá que establecerlos manualmente desde la app
		//Team home = new Team("HOME");
		//Team guest = new Team("GUEST");
		Team team1 = new Team("team1", "Bilbao", "Bilbao Basket", "Este es el equipo de Bilbao.", League.A);
		Team team2 = new Team("team2", "Trapaga", "Trapaga Basket", "Este es el equipo de Trapaga.", League.B);
		new WindowGameRegister(team1,team2);
		}
	//Inicializar listas de equipos con los jugadores correspondientes
	
	
	public WindowGameRegister(Team home, Team guest) {
		setSize(960, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Registro de partido");
		
		homeTeam = initTeam(home);
		homeNumbers = numbers(homeTeam);
		homeNumberPlayer = initNumberPlayer(homeTeam);
		guestTeam = initTeam(guest);
		guestNumbers = numbers(guestTeam);
		guestNumberPlayer = initNumberPlayer(guestTeam);
		
		JPanel panelWest = new JPanel();
		panelWest.setLayout(new BorderLayout());
		
		//Panel en el que se visualiza un campo de baloncesto y un label con el nombre de cada equipo
		JPanel panelGame = new JPanel();
		ImageIcon field = new ImageIcon("src/img/paneGame.png");
		JLabel labelGame = new JLabel(field);
		panelGame.add(labelGame);
		panelGame.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseClicked(MouseEvent e) {
				new RegisterAction(home,guest);
				Class<Basket> classBasket = Basket.class;
				if (classBasket.isInstance(incidents.get(0))) {
					Basket b = (Basket) incidents.get(0);
					b.setCoordinates(getLocation());
				}
			}
		});
		panelWest.add(panelGame, BorderLayout.WEST);
		
		JPanel panelNames = new JPanel();
		panelNames.setLayout(new GridLayout(2,1));
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
		
		//Botonera con opciones en la parte de abajo a la derecha
		JPanel panelButtonBox = new JPanel();
		panelButtonBox.setLayout(new FlowLayout(FlowLayout.RIGHT));
	
		JButton buttonFinish = new JButton();
		buttonFinish.setText("Finalizar");
		
		//https://chuidiang.org/index.php?title=Botones_con_icono_y_texto
		//De esta página hemos obtenido el código para insertar la imagen de icono en un botón
		JButton buttonHelp = new JButton();
		buttonHelp.setIcon(new ImageIcon("src/img/buttonHelp.png")); 
		buttonHelp.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Se encuentra usted en la ventana de registro de partidos.\n\n A continuación, haga clic sobre las coordenadas del campo en las que esté ocurriendo la acción a registrar. \nEn ese momento, se desplegará una ventana en la que podrá meter los datos necesarios de dicha acción y \nésta quedará registrada.\n\n Cuando termine, podrá usted ver en la parte derecha de la ventana las acciones que ya han sido registradas \ny después de hacer clic sobre ellas, podrá eliminarlas pulsando delete mientras clicas la acción a borrar o \nhaciendo ctrl+z para eliminar la más reciente.");
				
			}
		});
		
		
		panelButtonBox.add(buttonHelp);
		panelButtonBox.add(buttonFinish);
		//panelCenter.add(panelGame);
		//panelCenter.add(panelTable);
		add(panelTable, BorderLayout.CENTER);
		add(panelWest, BorderLayout.WEST);
		add(panelButtonBox, BorderLayout.SOUTH);
		setVisible(true);
	}
	public static ArrayList<Player> initTeam(Team team){
		ArrayList<Player> l = new ArrayList<Player>();
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			dbManager.connect("src/db/rebote.db");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + "src/db/rebote.db");
			PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, name, firstSurname, secondSurname,"
					+ " password, birthDate, country, team_id, height, weight FROM player WHERE team_id = ?");
				preparedStatement.setInt(1, team.getId());
				
				ResultSet resultSet = preparedStatement.executeQuery();
				
				if (resultSet.next()) {
					Player player = new Player();
					player.setId(resultSet.getInt("id"));
					player.setUsername(resultSet.getString("username"));
					player.setName(resultSet.getString("name"));
					player.setFirstSurname(resultSet.getString("firstSurname"));
					player.setSecondSurname(resultSet.getString("secondSurname"));
					player.setPassword(resultSet.getString("password"));
					player.setBirthDate(dbManager.stringToCalendar(resultSet.getString("birthDate")));
					player.setCountry(resultSet.getString("country"));
					player.setTeam(dbManager.getTeam(resultSet.getInt("team_id")));
					player.setHeight(resultSet.getInt("height"));
					player.setWeight(resultSet.getFloat("weight"));
					l.add(player);
				}
			
			dbManager.disconnect();
		} catch (UserRepositoryException e) {
			System.out.println("Error intentando acceder a la base de datos.");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return l; 
	}
	
	//Crear un mapa en el que asociamos a cada jugador con su numero
	public static HashMap<String, Player> initNumberPlayer(ArrayList<Player> playerList){
		HashMap<String, Player> numberPlayer = new HashMap<String, Player>();
		for (Player p: playerList) {
			numberPlayer.put(p.getName(), p);
		}
		return numberPlayer;
	}
	
	public static ArrayList<String> numbers(ArrayList<Player> playerList){
		ArrayList<String> numb = new ArrayList<String>();
		for (Player p : playerList) {
			numb.add(p.getName());
		}
		return numb;
	}
	
	public static void changeComboBoxContent(ArrayList<String> str) {
		 comboNumber.removeAllItems();

	        // Agregar nuevos elementos al JComboBox
	        for (String add : str) {
	            comboNumber.addItem(add);
	        }
	}
	
	//Cambiar visibilidad del panel para elegir autor
	private static void ScoreVisible() {
        String action = (String) comboTipe.getSelectedItem();
        panelScore.setVisible("Canasta".equals(action));
    }
	
	public static class RegisterAction extends JFrame{
		public static String selectedTipe;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public RegisterAction(Team home, Team guest) {
			setTitle("Datos de la acción");
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setSize(300, 300);
			
			
			JPanel panelData = new JPanel(new GridLayout(2, 2));
			
			
			//Cuadrante superior izquierda para poner el tipo de acción
			JPanel panelTipe = new JPanel(new GridLayout(2, 1));
			JLabel labelTipe = new JLabel("Tipo de acción:");
			String[] arrayTipe = {"Seleccione", "Canasta", "Falta", "Expulsión"};
			comboTipe = new JComboBox<>(arrayTipe);
			panelTipe.add(labelTipe);
			panelTipe.add(comboTipe);
			comboTipe.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	            	selectedTipe = (String) comboTipe.getSelectedItem();
	                ScoreVisible();
	            }
	        });
			panelTipe.add(labelTipe);
			panelTipe.add(comboTipe);
			
			//Cuadrante inferior izquierda para indicar equipo
			JPanel panelTeam = new JPanel(new GridLayout(3, 1));
			JLabel labelTeam = new JLabel("Seleccione equipo:");
			
			JRadioButton radioButtonHome = new JRadioButton(home.getName());
			JRadioButton radioButtonGuest = new JRadioButton(guest.getName());
				
			radioButtonHome.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (radioButtonHome.isSelected()) {
	                	if (!panelNumber.isVisible()) {
	                		panelNumber.setVisible(true);
	                	}
	                	changeComboBoxContent(homeNumbers);
	                }
	            }
	        });
			radioButtonGuest.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
	                if (radioButtonGuest.isSelected()) {
	                	if (!panelNumber.isVisible()) {
	                		panelNumber.setVisible(true);
	                	}
	                    changeComboBoxContent(guestNumbers);
	                }
	            }
	        });
	        
	        ButtonGroup BGTeam = new ButtonGroup();
	        BGTeam.add(radioButtonHome);
	        BGTeam.add(radioButtonGuest);
	        panelTeam.add(labelTeam);
	        panelTeam.add(radioButtonHome);
	        panelTeam.add(radioButtonGuest);
	        
	        
	        //Cuadrante superior derecha para seleccionar jugador
	        panelNumber = new JPanel(new GridLayout(2, 1));
	        panelNumber.setVisible(false);
	        JLabel labelNumber = new JLabel("Elija autor:");
	        comboNumber = new JComboBox<String>(homeNumbers.toArray(new String[0]));
	        panelNumber.add(labelNumber);
	        panelNumber.add(comboNumber);
	        
	        
	        
	        //Cuadrante inferior derecha para marcar puntuación
	        panelScore = new JPanel(new GridLayout(2, 1));
	        panelScore.setVisible(false);
			JLabel labelScore = new JLabel("Puntuación:");
			JComboBox<Integer> comboScore = new JComboBox<>(new Integer[]{1, 2, 3});
			panelScore.add(labelScore);
			panelScore.add(comboScore);
	        
	        panelData.add(panelTipe);
	        panelData.add(panelTeam);
	        panelData.add(panelNumber);
	        panelData.add(panelScore);
	        
	        //Boton de aceptar
	        JPanel buttons = new JPanel();
	        JButton buttonConfirm = new JButton("Confirmar");
	        buttonConfirm.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					if ((radioButtonGuest.isSelected()||radioButtonHome.isSelected())
							&& !(comboTipe.getSelectedItem()=="Seleccione")) {
						Player p;
						if(radioButtonHome.isSelected()) {
							p = homeNumberPlayer.get(comboNumber.getSelectedItem());
						} else {
							p = guestNumberPlayer.get(comboNumber.getSelectedItem());
						}
						switch(selectedTipe) {
							case "Canasta":
								Basket b = new Basket(p, (int)comboScore.getSelectedItem());
								incidents.add(0,b);
								dispose();
		                        break;
		                    case "Falta":
		                    	Foul f = new Foul(p);
		                    	incidents.add(0,f);
		                    	dispose();
		                    	new WindowFreeThrowCheck(home, guest);
		                        break;
		                    case "Expulsión":
		                    	Expulsion ex = new Expulsion(p);
		                    	incidents.add(0,ex);
		                    	dispose();
		                        break;
						}
					}
				}});
	        buttons.add(buttonConfirm);
			
	        add(panelData, BorderLayout.EAST);
	        add(buttons, BorderLayout.SOUTH);
			pack();
			
			setLocationRelativeTo(null);
			setAlwaysOnTop(true);
			setVisible(true);
		}
		
	}
	
	public static class WindowFreeThrow extends JFrame{
		public JPanel panelScored;
		public JPanel panelMissed;
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		public WindowFreeThrow(Team home, Team guest) {
			JPanel panelData = new JPanel();
			panelData.setLayout(new GridLayout(2,2));
			JPanel panelTeam = new JPanel(new GridLayout(3, 1));
			JLabel labelTeam = new JLabel("Seleccione equipo:");
			JRadioButton radioButtonHome = new JRadioButton(home.getName());
			JRadioButton radioButtonGuest = new JRadioButton(guest.getName());
				
				radioButtonHome.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                if (radioButtonHome.isSelected()) {
		                	if (!panelNumber.isVisible()) {
		                		panelNumber.setVisible(true);
		                	}
		                	changeComboBoxContent(homeNumbers);
		                }
		            }
		        });
				radioButtonGuest.addActionListener(new ActionListener() {
		            @Override
		            public void actionPerformed(ActionEvent e) {
		                if (radioButtonGuest.isSelected()) {
		                	if (!panelNumber.isVisible()) {
		                		panelNumber.setVisible(true);
		                	}
		                    changeComboBoxContent(guestNumbers);
		                }
		            }
		        });
	        ButtonGroup BGTeam = new ButtonGroup();
	        BGTeam.add(radioButtonHome);
	        BGTeam.add(radioButtonGuest);
	        panelTeam.add(labelTeam);
	        panelTeam.add(radioButtonHome);
	        panelTeam.add(radioButtonGuest);
	        
	        //Cuadrante superior derecha para seleccionar jugador
	        panelNumber = new JPanel(new GridLayout(2, 1));
	        panelNumber.setVisible(false);
	        JLabel labelNumber = new JLabel("Elija autor:");
	        comboNumber = new JComboBox<String>(homeNumbers.toArray(new String[0]));
	        comboNumber.addActionListener(new ActionListener() {
	            @Override
	            public void actionPerformed(ActionEvent e) {
                	if (!(panelMissed.isVisible() && panelScored.isVisible())) {
                		panelMissed.setVisible(true);
                		panelScored.setVisible(true);
                	}
                	changeComboBoxContent(homeNumbers);
	            }
	        });
	        panelNumber.add(labelNumber);
	        panelNumber.add(comboNumber);
			
			panelScored = new JPanel();
			panelScored.setVisible(false);
			JButton buttonScored = new JButton("Acierto");
			buttonScored.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Player p;
					if(radioButtonHome.isSelected()) {
						p = homeNumberPlayer.get(comboNumber.getSelectedItem());
					} else {
						p = guestNumberPlayer.get(comboNumber.getSelectedItem());
					}
					FreeThrow ft = new FreeThrow(p, true);
					incidents.add(ft);
					dispose();
				}});
			panelScored.add(buttonScored);
			
			panelMissed = new JPanel();
			panelMissed.setVisible(false);
			JButton buttonMissed = new JButton("Fallo");
			buttonMissed.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					Player p;
					if(radioButtonHome.isSelected()) {
						p = homeNumberPlayer.get(comboNumber.getSelectedItem());
					} else {
						p = guestNumberPlayer.get(comboNumber.getSelectedItem());
					}
					FreeThrow ft = new FreeThrow(p, false);
					incidents.add(ft);
					dispose();
				}});
			panelMissed.add(buttonMissed);
			
			panelData.add(panelTeam);
			panelData.add(panelNumber);
			panelData.add(panelScored);
			panelData.add(panelMissed);
			add(panelData, BorderLayout.NORTH);
			
		}
		
		

	}
	public static class WindowFreeThrowCheck extends JFrame {
		 /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public WindowFreeThrowCheck(Team home, Team guest) {
			 setTitle("Comprobación de tiros libres");
			 setSize(300, 100);
			 setLayout(new GridLayout(2, 1));
			 
			 JPanel paneltext = new JPanel();
			 JLabel labelScore = new JLabel("¿Se han causado tiros libres?");
			 paneltext.add(labelScore);
			 
			 JPanel panelButtons = new JPanel();
			 panelButtons.setLayout(new FlowLayout());
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
				}});
			 button1.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						displayFreeThrow(1, home, guest);
					}});
			 
			 button2.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						displayFreeThrow(2, home, guest);
					}});
			 
			 button3.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						dispose();
						displayFreeThrow(3, home, guest);
					}});
		     this.add(paneltext);
		     this.add(panelButtons);
		 }
		public static void displayFreeThrow(int reps, Team home, Team guest) {
			for (int i = 0; i < reps ; i++) {
				new WindowFreeThrow(home, guest);
			}
		}
	}
	
}
