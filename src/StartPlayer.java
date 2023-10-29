import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class StartPlayer extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public StartPlayer() {
		setSize(600, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Inicio Jugador");

		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout());
		
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("¡Bienvenido a REBOTE!");
		Font font = new Font("Arial", Font.BOLD, 30);
		title.setFont(font);
		titlePanel.add(title);
		
		JPanel principalPanel = new JPanel();
		principalPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.weightx = 1.0;
		
		JLabel text = new JLabel("Seleccione al jugador que quieres buscar \n");
		JTextField searcher = new JTextField(50);
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		principalPanel.add(text, gbc);
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		principalPanel.add(searcher, gbc);
		
		
		JPanel ImagePanel = new JPanel();
        ImageIcon image = new ImageIcon("img/basket.jpg");
        JLabel labelBasket = new JLabel(image);
        JLabel blankLabel = new JLabel();
        ImagePanel.add(labelBasket);
        
        gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		principalPanel.add(blankLabel, gbc);
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.EAST;
        principalPanel.add(labelBasket, gbc);

		//Añadir Buscador
		
		JPanel buttonPanel = new JPanel();
		JButton select = new JButton("Seleccionar");
		JButton newPlayer = new JButton("Crear usario");
		JButton editPlayer = new JButton("Editar usuario");
		JButton eliminate = new JButton("Eliminar tu usuario");
		buttonPanel.add(eliminate);
		buttonPanel.add(editPlayer);
		buttonPanel.add(newPlayer);
		buttonPanel.add(select);
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		add(titlePanel, BorderLayout.NORTH);
		add(principalPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
}