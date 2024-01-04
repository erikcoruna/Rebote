package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import db.SQLiteDBManager;
import domain.Game;
import domain.League;
import domain.Team;
import domain.UserRepositoryException;

public class WindowTeam extends JFrame {
	public static void main(String[] args) {
		new WindowTeam(new Team(1, "Team1", "Bilbao", "Bilbao basket", "Este es el equipo de baloncesto de Bilbao.", League.A));
	}
	private static final long serialVersionUID = 1L;

	private List<Game> gamesPlayedList = new ArrayList<>();
	private int gameIndex;
	
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
	
	private void updateGame(Team team1) {
		if (gamesPlayedList.size() > 0 && gameIndex >= 0 && gameIndex < gamesPlayedList.size()) {
			SQLiteDBManager dbManager = new SQLiteDBManager();
			try {
				System.out.println("Conectando con la base de datos...");
				dbManager.connect("src/db/rebote.db");
				Game currentGame = gamesPlayedList.get(gameIndex);
				team1NameLabel.setText(team1.getName().toUpperCase());
				team1LeagueLabel.setIcon(new ImageIcon("src/img/" + team1.getLeague() + ".png"));
				team1PointsLabel.setText("P: " + currentGame.getTeamScore1());
				team1FoultsLabel.setText("F: " + currentGame.getTeamFoults1());
				team2 = dbManager.getTeam(currentGame.getTeam2());
				team2NameLabel.setText(team2.getName().toUpperCase());
				team2LeagueLabel.setIcon(new ImageIcon("src/img/" + team2.getLeague() + ".png"));
				team2PointsLabel.setText("P: " + currentGame.getTeamScore2());
				team2FoultsLabel.setText("F: " + currentGame.getTeamFoults2());
			} catch (UserRepositoryException e) {
				System.out.println("No se ha podido acceder a la base de datos.");
				e.printStackTrace();
			}
		}
	}
	
	private void showPreviousGame(Team team1) {
		if (gameIndex < gamesPlayedList.size() - 1) {
			nextButton.setEnabled(true);
			gameIndex++;
			updateGame(team1);
			if (gameIndex == gamesPlayedList.size() - 1) {
				previousButton.setEnabled(false);
			}
		}
	}
	
	private void showNextGame(Team team1) {
		if (gameIndex > 0) {
			previousButton.setEnabled(true);
			gameIndex--;
			updateGame(team1);
			if (gameIndex == 0) {
				nextButton.setEnabled(false);
			}
		}
	}
	
	public WindowTeam(Team team) {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle(team.getName());
		setIconImage(new ImageIcon("src/img/" + team.getLeague() + ".png").getImage());
		
		JPanel panel = new JPanel(new BorderLayout());
		
		JLabel nameLabel = new JLabel(team.getName());
		// https://chuidiang.org/index.php?title=Fuentes_de_texto_en_Java
		// Para coger la fuente.
		nameLabel.setFont(new Font("Agency FB", Font.BOLD, 30));
		nameLabel.setHorizontalAlignment(JLabel.CENTER);
		panel.add(nameLabel, BorderLayout.NORTH);
		
		// https://stackoverflow.com/questions/9660987/how-to-get-a-tab-character
		// Para coger el caracter tab en html.
		JLabel stuffLabel = new JLabel(String.format("<html>Ciudad: %s&#9;Estadio: %s<br/><br/>Descripción: %s",
				team.getCity(),
				team.getStadium(),
				team.getDescription()));
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
		SQLiteDBManager dbManager = new SQLiteDBManager();
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
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
					
					team1NameLabel = new JLabel(team.getName().toUpperCase());
					team1NameLabel.setHorizontalAlignment(JLabel.CENTER);
					team1LeagueLabel = new JLabel();
					ImageIcon team1Icon = new ImageIcon("src/img/" + team.getLeague() + ".png");
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
					ImageIcon team2Icon = new ImageIcon("src/img/" + team2.getLeague() + ".png");
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
						JPanel buttonsPanel = new JPanel();
						
						southPanel.add(buttonsPanel, BorderLayout.SOUTH);
						previousButton.setEnabled(false);
						buttonsPanel.add(previousButton);
						buttonsPanel.add(nextButton);
					
					
						previousButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								showPreviousGame(team);
							}
						});
						
						nextButton.addActionListener(new ActionListener() {
							
							@Override
							public void actionPerformed(ActionEvent e) {
								showNextGame(team);
							}
						});
					}
				} else {
					southPanel.add(noGamesLabel, BorderLayout.CENTER);
				}
			} else {
				southPanel.add(noGamesLabel, BorderLayout.CENTER);
			}
		} catch (UserRepositoryException e) {
			System.out.println("No se ha podido acceder a la base de datos.");
			e.printStackTrace();
		}
		
		panel.add(southPanel, BorderLayout.SOUTH);
		
		add(panel);
		
		setVisible(true);
	}
}
