package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import collections.ReboteCollections;
import db.SQLiteDBManager;
import domain.Game;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
import domain.User;
import domain.UserRepositoryException;
import io.ConfigReader;

public class WindowTeam extends JFrame {

	static Logger logger = Logger.getLogger(WindowStart.class.getName());
	private static final long serialVersionUID = 1L;

	private Path dbPath = Paths.get("resources/db/rebote.db");
	private List<Game> gamesPlayedList = new ArrayList<>();
	private int gameIndex;
	
	private Team team1;
	private JLabel team1NameLabel;
	private JLabel team1LeagueLabel;
	private JLabel team1PointsLabel;
	private JLabel team1FoultsLabel;
	private JLabel vsLabel;
	private Team team2;
	private JLabel team2NameLabel;
	private JLabel team2LeagueLabel;
	private JLabel team2PointsLabel;
	private JLabel team2FoultsLabel;
	
	private JButton previousButton;
	private JButton nextButton;
	private JButton joinButton;
	private JButton backButton;
	
	private void updateGame() {
		if (gamesPlayedList.size() > 0 && gameIndex >= 0 && gameIndex < gamesPlayedList.size()) {
			SQLiteDBManager dbManager = new SQLiteDBManager();
			try {
				dbManager.connect(dbPath.toString());
				Game currentGame = gamesPlayedList.get(gameIndex);
				team1 = dbManager.getTeam(currentGame.getTeam1());
				team1NameLabel.setText(team1.getName().toUpperCase());
				team1LeagueLabel.setIcon(new ImageIcon("resources/images/" + team1.getLeague() + ".png"));
				team1PointsLabel.setText("P: " + currentGame.getTeamScore1());
				team1FoultsLabel.setText("F: " + currentGame.getTeamFoults1());
				team2 = dbManager.getTeam(currentGame.getTeam2());
				team2NameLabel.setText(team2.getName().toUpperCase());
				team2LeagueLabel.setIcon(new ImageIcon("resources/images/" + team2.getLeague() + ".png"));
				team2PointsLabel.setText("P: " + currentGame.getTeamScore2());
				team2FoultsLabel.setText("F: " + currentGame.getTeamFoults2());
			} catch (UserRepositoryException e) {
				logger.warning(ConfigReader.dbConnectError);
			}
		}
	}
	
	private void showPreviousGame() {
		if (gameIndex < gamesPlayedList.size() - 1) {
			nextButton.setEnabled(true);
			gameIndex++;
			updateGame();
			if (gameIndex == gamesPlayedList.size() - 1) {
				previousButton.setEnabled(false);
			}
		}
	}
	
	private void showNextGame() {
		if (gameIndex > 0) {
			previousButton.setEnabled(true);
			gameIndex--;
			updateGame();
			if (gameIndex == 0) {
				nextButton.setEnabled(false);
			}
		}
	}
	
	public WindowTeam(Team team, User user) throws Exception {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setTitle(team.getName());
		setIconImage(new ImageIcon("resources/images/" + team.getLeague() + ".png").getImage());
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel nameLabel = new JLabel(team.getName());
		// https://chuidiang.org/index.php?title=Fuentes_de_texto_en_Java
		// Para coger la fuente.
		nameLabel.setFont(new Font("Agency FB", Font.BOLD, 30));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(nameLabel, BorderLayout.NORTH);
		
		Map<Integer, Integer> gamesPerTeam = ReboteCollections.gamesPlayedPerTeam(dbPath);
		int gamesPlayed = gamesPerTeam.get(team.getId());
		Integer value = gamesPerTeam.values().iterator().next();
		int gamesPlayedTop1 = value;
		String isInTop1;
		if (gamesPlayed >= gamesPlayedTop1) {
			isInTop1 = "Sí";
		} else {
			isInTop1 = "No";
		}
		JLabel stuffLabel = new JLabel(String.format("<html>Ciudad: %s<br/>Estadio: %s<br/>Descripción: %s<br/><br/>Partidos jugados: %d<br/><br/>Partidos ganados: %d<br/><br/>Partidos perdidos: %d<br/><br/>¿Es el equipo con más partidos jugados? %s</html>",
				team.getCity(),
				team.getStadium(),
				team.getDescription(),
				ReboteCollections.gamesPlayedPerTeam(dbPath).get(team.getId()),
				ReboteCollections.gamesWin(team, dbPath).size(),
				ReboteCollections.gamesLoseOrTie(team, dbPath).size(),
				isInTop1));
		stuffLabel.setVerticalAlignment(JLabel.NORTH);
		stuffLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
		panel.add(stuffLabel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.setBackground(Color.WHITE);
		JLabel gamesPlayedLabel = new JLabel("Partidos jugados");
		gamesPlayedLabel.setHorizontalAlignment(JLabel.CENTER);
		gamesPlayedLabel.setFont(new Font("Agency FB", Font.BOLD, 20));
		southPanel.add(gamesPlayedLabel, BorderLayout.NORTH);
		
		JLabel noGamesLabel = new JLabel("No se han jugado partidos.");
		noGamesLabel.setHorizontalAlignment(JLabel.CENTER);
		noGamesLabel.setFont(new Font("Agency FB", Font.BOLD, 15));
		noGamesLabel.setBorder(BorderFactory.createEmptyBorder(50, 0, 50, 0));
		joinButton = new JButton("Unirse");
		backButton = new JButton("Atrás");
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			dbManager.connect(dbPath.toString());
			
			if (!dbManager.getAllGames().isEmpty()) {
				for (Game game : dbManager.getAllGames()) {
					if (game.getTeam1() == team.getId() || game.getTeam2() == team.getId()) {
						gamesPlayedList.add(game);
					}
				}
				if (gamesPlayedList.size() >= 1) {
					gameIndex = gamesPlayedList.size() - 1;
					JPanel gamesPanel = new JPanel(new BorderLayout());
					southPanel.add(gamesPanel, BorderLayout.CENTER);
					JPanel team1Panel = new JPanel(new GridLayout(2, 2));
					team1Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
					JPanel team2Panel = new JPanel(new GridLayout(2, 2));
					team2Panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
					gamesPanel.add(team1Panel, BorderLayout.WEST);
					gamesPanel.add(team2Panel, BorderLayout.EAST);
					JPanel buttonsPanel = new JPanel();
					buttonsPanel.add(joinButton);
					buttonsPanel.add(backButton);
					gamesPanel.add(buttonsPanel, BorderLayout.SOUTH);
					
					team1 = dbManager.getTeam(gamesPlayedList.get(gamesPlayedList.size() - 1).getTeam1());
					team1NameLabel = new JLabel(team1.getName().toUpperCase());
					team1NameLabel.setHorizontalAlignment(JLabel.CENTER);
					team1LeagueLabel = new JLabel();
					ImageIcon team1Icon = new ImageIcon("resources/images/" + team1.getLeague() + ".png");
					team1LeagueLabel.setIcon(team1Icon);
					team1PointsLabel = new JLabel("P: " + gamesPlayedList.get(gamesPlayedList.size() - 1).getTeamScore1());
					team1PointsLabel.setHorizontalAlignment(JLabel.CENTER);
					team1FoultsLabel = new JLabel("F: " + gamesPlayedList.get(gamesPlayedList.size() - 1).getTeamFoults1());
					team1FoultsLabel.setHorizontalAlignment(JLabel.CENTER);
					
					vsLabel = new JLabel("VS");
					vsLabel.setFont(new Font("Agency FB", Font.BOLD, 40));
					vsLabel.setHorizontalAlignment(JLabel.CENTER);
					gamesPanel.add(vsLabel, BorderLayout.CENTER);
					
					team2 = dbManager.getTeam(gamesPlayedList.get(gamesPlayedList.size() - 1).getTeam2());
					team2NameLabel = new JLabel(team2.getName().toUpperCase());
					team2LeagueLabel = new JLabel();
					ImageIcon team2Icon = new ImageIcon("resources/images/" + team2.getLeague() + ".png");
					team2LeagueLabel.setIcon(team2Icon);
					team2PointsLabel = new JLabel("P: " + gamesPlayedList.get(gamesPlayedList.size() - 1).getTeamScore2());
					team2FoultsLabel = new JLabel("F: " + gamesPlayedList.get(gamesPlayedList.size() - 1).getTeamFoults2());
					
					team1Panel.add(team1NameLabel);
					team1Panel.add(team1LeagueLabel);
					team1Panel.add(team1PointsLabel);
					team1Panel.add(team1FoultsLabel);
					team2Panel.add(team2NameLabel);
					team2Panel.add(team2LeagueLabel);
					team2Panel.add(team2PointsLabel);
					team2Panel.add(team2FoultsLabel);
					
					if (gamesPlayedList.size() > 1) {
						previousButton = new JButton("<<");
						nextButton = new JButton(">>");
						JPanel buttonsPanel2 = new JPanel();
						
						southPanel.add(buttonsPanel2, BorderLayout.SOUTH);
						previousButton.setEnabled(false);
						buttonsPanel2.add(joinButton);
						buttonsPanel2.add(previousButton);
						buttonsPanel2.add(nextButton);
						buttonsPanel2.add(backButton);
					
					
						previousButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								showPreviousGame();
							}
						});
						
						nextButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								showNextGame();
							}
						});
					}
				} else {
					southPanel.add(noGamesLabel, BorderLayout.CENTER);
					JPanel buttonsPanel3 = new JPanel();
					buttonsPanel3.add(joinButton);
					buttonsPanel3.add(backButton);
					southPanel.add(buttonsPanel3, BorderLayout.SOUTH);
				}
			} else {
				southPanel.add(noGamesLabel, BorderLayout.CENTER);
				JPanel buttonsPanel4 = new JPanel();
				buttonsPanel4.add(joinButton);
				buttonsPanel4.add(backButton);
				southPanel.add(buttonsPanel4, BorderLayout.SOUTH);
			}
		} catch (UserRepositoryException e) {
			logger.warning(ConfigReader.dbConnectError);
		}
		
		joinButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (user instanceof Player) {
					Player player = (Player) user;
					player.setTeam(team);
					try {
						dbManager.updatePlayer(player);
					} catch (UserRepositoryException e1) {
						logger.warning(ConfigReader.dbConnectError);
					}
				} else if (user instanceof Trainer) {
					Trainer trainer = (Trainer) user;
					trainer.setTeam(team);
					try {
						dbManager.updateTrainer(trainer);
					} catch (UserRepositoryException e2) {
						logger.warning(ConfigReader.dbConnectError);
					}
				}
				logger.info("Unido al equipo " + team.getName());
			}
		});
		
		backButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (user instanceof Player) {
					Player player = (Player) user;
					try {
						new WindowHomePlayer(player);
					} catch (Exception e1) {
						logger.warning("No se ha podido obtener el jugador.");
					}
					dispose();
				} else if (user instanceof Trainer) {
					Trainer trainer = (Trainer) user;
					try {
						new WindowHomeTrainer(trainer);
					} catch (Exception e1) {
						logger.warning("No se ha podido obtener el entrenador.");
					}
					dispose();
				}
			}
		});
		
		panel.add(southPanel, BorderLayout.SOUTH);
		
		add(panel);
		
		setVisible(true);
	}
}
