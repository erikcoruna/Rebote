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

public class CreateTeam extends JFrame {
	
	private static final long serialVersionUID = 1L;

	public CreateTeam() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Creacion de Equipo");
			
		JPanel titlePanel = new JPanel();
		JLabel labelTitle = new JLabel("Creación de Equipo");
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
		JLabel locationLabel = new JLabel("Ciudad:");
		JTextField locationText = new JTextField(50);
		JLabel stadiumLabel = new JLabel("Nombre de la cancha/estadio:");
		JTextField stadiumtext = new JTextField(50);
		JLabel descriptionLabel = new JLabel("Descripción:");
		JTextField descriptionText = new JTextField(100);
		
		
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameLabel, gbc);
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(nameText,gbc);
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(locationLabel,gbc);
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(locationText,gbc);
		gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(stadiumLabel,gbc);
		gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(stadiumtext,gbc);
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(descriptionLabel,gbc);
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.CENTER;
		informationPanel.add(descriptionText,gbc);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(new JButton("Cancelar"));
		buttonPanel.add(new JButton("Guardar"));
		buttonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
		
		
		
		add(titlePanel, BorderLayout.NORTH);
		add(informationPanel, BorderLayout.CENTER);
		add(buttonPanel, BorderLayout.SOUTH);
		setVisible(true);
	}
}
