package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
		JLabel stuffLabel = new JLabel(String.format("<html>Ciudad: %s&#9;&#9;Estadio: %s<br/><br/>Descripción: %s",
				team.getCity(),
				team.getStadium(),
				team.getDescription()));
		stuffLabel.setVerticalAlignment(JLabel.NORTH);
		stuffLabel.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
		panel.add(stuffLabel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel(new BorderLayout());
		southPanel.setBackground(Color.PINK);
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
				List<Game> gamesPlayed = new ArrayList<>();
				for (Game game : dbManager.getAllGames()) {
					if (game.getTeam1() == team.getId() || game.getTeam2() == team.getId()) {
						gamesPlayed.add(game);
						if (gamesPlayed.size() >= 5) {
							break;
						}
					}
				}
				if (gamesPlayed.size() >= 1) {
					JPanel gamesPanel = new JPanel(new BorderLayout());
					southPanel.add(gamesPanel, BorderLayout.CENTER);
					JPanel team1Panel = new JPanel();
					JPanel team2Panel = new JPanel();
					gamesPanel.add(team1Panel, BorderLayout.WEST);
					gamesPanel.add(team2Panel, BorderLayout.EAST);
					
					JLabel team1NameLabel = new JLabel(team.getName());
					JLabel team1LeagueLabel = new JLabel();
					ImageIcon team1Icon = new ImageIcon("src/img/" + team.getLeague() + ".png");
					team1LeagueLabel.setIcon(team1Icon);
					JLabel team1PointsLabel = new JLabel("Puntos: " + gamesPlayed.get(gamesPlayed.size() - 1).getTeamScore1());
					
					Team team2 = dbManager.getTeam(gamesPlayed.get(gamesPlayed.size() - 1).getTeam2());
					JLabel team2NameLabel = new JLabel(team2.getName());
					JLabel team2LeagueLabel = new JLabel();
					ImageIcon team2Icon = new ImageIcon("src/img/" + team2.getLeague() + ".png");
					team2LeagueLabel.setIcon(team2Icon);
					JLabel team2PointsLabel = new JLabel("Puntos: " + gamesPlayed.get(gamesPlayed.size() - 1).getTeamScore2());
					
					team1Panel.add(team1NameLabel);
					team1Panel.add(team1LeagueLabel);
					team1Panel.add(team1PointsLabel);
					team2Panel.add(team2NameLabel);
					team2Panel.add(team2LeagueLabel);
					team2Panel.add(team2PointsLabel);
					
					JButton previousButton = new JButton("<<");
					JButton nextButton = new JButton(">>");
					JPanel buttonsPanel = new JPanel();
					
					southPanel.add(buttonsPanel, BorderLayout.SOUTH);
					buttonsPanel.add(previousButton);
					buttonsPanel.add(nextButton);
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
