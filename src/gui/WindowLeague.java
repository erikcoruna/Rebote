package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import collections.ReboteCollections;
import domain.Team;
import io.ConfigReader;
import io.FileManager;

public class WindowLeague extends JFrame {
	
	static Logger logger = Logger.getLogger(WindowStart.class.getName());
	private static final long serialVersionUID = 1L;
	
	public WindowLeague(List<List<List<Team>>> league) throws Exception {
		setSize(480, 560);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Liga generada");
        
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        JPanel panelCenter = new JPanel();
        panelCenter.setLayout(new BoxLayout(panelCenter, BoxLayout.Y_AXIS));
        
        JLabel labelLeague = new JLabel("Liga generada");
        labelLeague.setFont(new Font("Arial", Font.BOLD, 30));
        labelLeague.setForeground(Color.ORANGE);
        panelCenter.add(labelLeague);
        
        for (int i = 0; i < league.size(); i++) {
        	JLabel labelWeek = new JLabel(String.format("    Semana %d", i + 1));
        	labelWeek.setFont(new Font("Arial", Font.BOLD, 20));
        	panelCenter.add(labelWeek);
        	for (List<Team> game : league.get(i)) {
        		JLabel labelGame = new JLabel(String.format("        * %s VS %s", game.get(0).getName(), game.get(1).getName()));
        		labelGame.setFont(new Font("Arial", Font.ITALIC, 15));
        		labelGame.addMouseListener(new MouseAdapter() {
					
					@Override
					public void mouseReleased(MouseEvent e) {
						new WindowGameRegister(game.get(0), game.get(1));
					}
				});
        		panelCenter.add(labelGame);
        	}
        }
        
        JLabel labelSeparator = new JLabel(" ");
        JPanel panelButtons = new JPanel(new GridLayout(1, 2));
        JButton buttonRegenerate = new JButton("Generar de nuevo");
        JButton buttonBack = new JButton("Atrás");
        
        buttonRegenerate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					List<List<List<Team>>> league = ReboteCollections.createLeague(Paths.get("resources/db/rebote.db"));
					FileManager.writeLeagueToFile(league);
					new WindowLeague(league);
					dispose();
				} catch (Exception e1) {
					logger.warning(ConfigReader.dbConnectError);
				}
			}
		});
        
        buttonBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowStart();
				dispose();
			}
		});
        
        panelCenter.add(labelSeparator);
        panelButtons.add(buttonRegenerate);
        panelButtons.add(buttonBack);
        panel.add(panelCenter, BorderLayout.CENTER);
        panel.add(panelButtons, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane);
        
        setVisible(true);
	}
}
