package gui;

import java.awt.BorderLayout;

import javax.swing.JFrame;
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
		
		JPanel panel = new JPanel(new BorderLayout());
		
		
		
		add(panel);
		
		setVisible(true);
	}
}
