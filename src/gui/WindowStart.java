package gui;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.file.Paths;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import domain.UserRepositoryException;
import io.CSVFileManager;
import io.ConfigReader;

public class WindowStart extends JFrame {
	
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(WindowStart.class.getName());
    private static final long serialVersionUID = 1L;
    
    private CSVFileManager csvManager = new CSVFileManager();

	public WindowStart() {
    	setSize(480, 560);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bienvenido");
        
        // https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
        // Se ha modificado parte del código para adecuarlo a nuestro proyecto con los botones y textos necesarios.
        JPanel panel = new JPanel();
        ImageIcon image = new ImageIcon("resources/images/basket.jpg");
        JLabel labelBasket = new JLabel(image);
        panel.add(labelBasket);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        panel.add(new JLabel(" "), gbc);
        
        panel.add(new JLabel(String.format("<html><h1><strong>%s</strong></h1></html>", ConfigReader.projectName)), gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton login = new JButton("Iniciar Sesión");
        JButton register = new JButton("Registrarse");
        JButton exportToFile = new JButton("Exportar a fichero");
        JButton importFromFile = new JButton("Importar desde fichero");
        login.setPreferredSize(new Dimension(200, 50));
        register.setPreferredSize(new Dimension(200, 50));
        exportToFile.setPreferredSize(new Dimension(200, 50));
        importFromFile.setPreferredSize(new Dimension(200, 50));
        login.setBackground(new Color(240, 196, 170));
        register.setBackground(new Color(240, 183, 170));
        exportToFile.setBackground(new Color(236, 242, 201));
        importFromFile.setBackground(new Color(201, 240, 242));
        exportToFile.setToolTipText("Exportar los datos de la base de datos a ficheros.");
        importFromFile.setToolTipText("Importar los datos de los ficheros a la base de datos.");
        panel.add(login, gbc);
        panel.add(new JLabel(" "), gbc);
        panel.add(register, gbc);
        panel.add(new JLabel(" "), gbc);
        panel.add(exportToFile, gbc);
        panel.add(new JLabel(" "), gbc);
        panel.add(importFromFile, gbc);
        
        add(panel);
        setVisible(true);
        
        // https://github.com/andoni-eguiluz/ud-progII-2023/blob/master/Clase2023-2/src/tema5/resueltos/ej5b8/VentanaPrincipal.java
        // Cogido la parte de los ActionListener y modificado para nuestro código.
        login.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		new WindowLogin();
        		dispose();
        		logger.info("Pulsado el botón Login.");
			}
		});
        
        register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new WindowRegister();
        		dispose();
        		logger.info("Pulsado el botón Register.");
			}
		});
        
        exportToFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					csvManager.exportPlayersToFile(Paths.get("resources/data/players.csv"), Paths.get("resources/db/rebote.db"));
					csvManager.exportTrainersToFile(Paths.get("resources/data/trainers.csv"), Paths.get("resources/db/rebote.db"));
					csvManager.exportTeamsToFile(Paths.get("resources/data/teams.csv"), Paths.get("resources/db/rebote.db"));
					csvManager.exportGamesToFile(Paths.get("resources/data/games.csv"), Paths.get("resources/db/rebote.db"));
				} catch (UserRepositoryException e1) {
					logger.warning(ConfigReader.csvWriteError);
				}
			}
		});
        
        importFromFile.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					csvManager.importPlayersFromFile(Paths.get("resources/data/players.csv"), Paths.get("resources/db/rebote.db"));
					csvManager.importTrainersFromFile(Paths.get("resources/data/trainers.csv"), Paths.get("resources/db/rebote.db"));
					csvManager.importTeamsFromFile(Paths.get("resources/data/teams.csv"), Paths.get("resources/db/rebote.db"));
					csvManager.importGamesFromFile(Paths.get("resources/data/games.csv"), Paths.get("resources/db/rebote.db"));
				} catch (UserRepositoryException e1) {
					logger.warning(ConfigReader.csvReadError);
				}
			}
		});
    }
}
