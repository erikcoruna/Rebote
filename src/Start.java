import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Start extends JFrame {
	
    private static final long serialVersionUID = 1L;

	public Start() {
    	setSize(480, 560);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bienvenido");
        
        // https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
        // Se ha modificado parte del código para adecuarlo a nuestro proyecto con los botones y textos necesarios.
        JPanel panel = new JPanel();
        ImageIcon image = new ImageIcon("img/basket.jpg");
        JLabel labelBasket = new JLabel(image);
        panel.add(labelBasket);
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        
        panel.add(new JLabel("<html><h1><strong>REBOTE</strong></h1></html>"), gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel buttons = new JPanel(new GridBagLayout());
        JButton login = new JButton("Iniciar Sesión");
        JButton register = new JButton("Registrarse");
        login.setPreferredSize(new Dimension(200, 50));
        register.setPreferredSize(new Dimension(200, 50));
        register.setBackground(Color.RED);
        buttons.add(login, gbc);
        buttons.add(new JLabel(" "), gbc);
        buttons.add(register, gbc);
        
        panel.add(buttons, gbc);
        
        add(panel);
        setVisible(true);
        
        // https://github.com/andoni-eguiluz/ud-progII-2023/blob/master/Clase2023-2/src/tema5/resueltos/ej5b8/VentanaPrincipal.java
        // Cogido la parte de los ActionListener y modificado para nuestro código.
        login.addActionListener(new ActionListener() {
        	@Override
        	public void actionPerformed(ActionEvent e) {
        		Login windowLogin = new Login();
        		windowLogin.setVisible(true);
        		setVisible(false);
			}
		});
        register.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Register windowRegister = new Register();
        		windowRegister.setVisible(true);
        		setVisible(false);
			}
		});
    }
}
