package gui;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class WindowStart extends JFrame {
	
	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(WindowStart.class.getName());
    private static final long serialVersionUID = 1L;

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
        
        panel.add(new JLabel("<html><h1><strong>REBOTE</strong></h1></html>"), gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JButton login = new JButton("Iniciar Sesión");
        JButton register = new JButton("Registrarse");
        login.setPreferredSize(new Dimension(200, 50));
        register.setPreferredSize(new Dimension(200, 50));
        register.setBackground(Color.RED);
        panel.add(login, gbc);
        panel.add(new JLabel(" "), gbc);
        panel.add(register, gbc);
        
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
    }
}
