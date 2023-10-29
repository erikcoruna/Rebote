import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateTrainer extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public CreateTrainer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Creacion de Jugador");
			
		JPanel titlePanel = new JPanel();
		JLabel labelTitle = new JLabel("Creación de Entrenador");
		Font font = new Font("Arial", Font.BOLD, 30);
		labelTitle.setFont(font);
		titlePanel.add(labelTitle);
		
		JPanel informationPanel = new JPanel();
		informationPanel.setLayout(new GridBagLayout());
		
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.weightx = 1.0;
		
		JLabel nameLabel = new JLabel("Nombre:");
		JTextField nameText = new JTextField(50);
		JLabel surname1Label = new JLabel("Primer apellido:");
		JTextField surname1Text = new JTextField(50);
		JLabel surname2Label = new JLabel("Segundo apellido:");
		JTextField surname2Text = new JTextField(50);
		//posible mejora con JList
		JLabel birthLabel = new JLabel("Fecha nacimiento (dd/mm/aaaa): "); 
		JTextField birthText = new JTextField(50);
		
		
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameLabel, gbc);
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameText,gbc);
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname1Label,gbc);
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname1Text,gbc);
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname2Label,gbc);
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(surname2Text,gbc);
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(birthLabel,gbc);
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(birthText,gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Guardar"));
		buttonPanel.add(new JButton("Cancelar"));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		
		
		add(titlePanel, BorderLayout.NORTH);
		add(informationPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
}
