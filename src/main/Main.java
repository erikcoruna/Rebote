package main;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Logger;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import db.DataBase;
import gui.WindowStart;
import io.ConfigReader;

public class Main extends JFrame {
	
	static Logger logger = Logger.getLogger(WindowStart.class.getName());
	private static final long serialVersionUID = 1L;
	
	// https://www.geeksforgeeks.org/java-swing-jprogressbar/
	// Se han hecho algunas modificaciones para adecuarlo a nuestra aplicación
	static JFrame frame = new JFrame("Cargando...");
	static JPanel panel = new JPanel();
	static JProgressBar progressBar = new JProgressBar();
	
    public static void main(String[] args) {
    	frame.setSize(300, 70);
    	frame.setLocationRelativeTo(null);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	
    	ConfigReader.loadConfiguration();
    	
    	if (ConfigReader.projectTestData == true) {
    		DataBase.loadTestData();
    		logger.info("Iniciando aplicación con datos de prueba.");
    	} else {
    		logger.info("Iniciando aplicación sin datos de prueba.");
    	}
    	
    	progressBar.setValue(0);
    	progressBar.setStringPainted(true);
    	panel.add(progressBar);
    	frame.add(panel);
    	
    	frame.setVisible(true);
    	
    	fill();

    	frame.dispose();

        // Ventana inicial
    	new WindowStart();
    }
    
    public static void fill() {
    	int i = 0;
    	while (i <= 100) {
    		progressBar.setValue(i + 1);
    		try {
				Thread.sleep(30);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
    		i += 1;
    	}
    }
}
