package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import domain.League;
import domain.Team;

public class WindowTeam extends JFrame {
	public static void main(String[] args) {
		new WindowTeam(new Team("Team1", "Bilbao", "Bilbao basket", "Este es el equipo de baloncesto de Bilbao.", League.A));
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
		panel.add(southPanel, BorderLayout.SOUTH);
		
		add(panel);
		
		setVisible(true);
	}
}
