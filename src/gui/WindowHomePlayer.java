package gui;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import com.toedter.calendar.JDateChooser;

import javax.swing.JLabel;

public class WindowHomePlayer extends JFrame {

	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new WindowHomePlayer();
	}
	
	public WindowHomePlayer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		
		//https://www.discoduroderoer.es/como-crear-pestanas-con-la-clase-jtabbedpane-en-java/
		//Un ejemplo de JTabbedPane, para saber como implementarlo en nuestro código
		JTabbedPane tabbedPanel = new JTabbedPane(); 
		
		JPanel paneProfile = new JPanel(new GridBagLayout());
		
        JTextField nameTextField = new JTextField(20);
        JTextField surname1TextField = new JTextField(20);
        JTextField surname2TextField = new JTextField(20);
        JTextField dorsalTextField = new JTextField(20);
        SpinnerModel valuesSpinnerNumberModel = new SpinnerNumberModel(150, 100, 250, 1);
        JSpinner heightSpinner = new JSpinner(valuesSpinnerNumberModel);
    	JDateChooser birthDateChooser = new JDateChooser();
       
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 10, 0); 
        
        JLabel nameLabel = new JLabel("Nombre:");
		JLabel surname1Label = new JLabel("Primer apellido:");
		JLabel surname2Label = new JLabel("Segundo apellido:");
		JLabel dorsalNumber = new JLabel("Numero de dorsal:");
		JLabel heightLabel = new JLabel("Altura (cm):");
		
		JLabel birthLabel = new JLabel("Fecha nacimiento: ");
		birthDateChooser.setDateFormatString("MMM. dd, yyyy");		
        
		gbc.anchor = GridBagConstraints.WEST;
        paneProfile.add(nameLabel, gbc);
        gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
        paneProfile.add(nameTextField, gbc);       
        gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
        paneProfile.add(surname1Label, gbc);       
        gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.WEST;
        paneProfile.add(surname1TextField, gbc);
        gbc.gridy = 4;
		gbc.anchor = GridBagConstraints.WEST;
        paneProfile.add(surname2Label, gbc);
        gbc.gridy = 5;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(surname2TextField, gbc);
		gbc.gridy = 6;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(dorsalNumber, gbc);
		gbc.gridy = 7;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(dorsalTextField, gbc);
		gbc.gridy = 8;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(heightLabel, gbc);
		gbc.gridy = 9;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(heightSpinner, gbc);
		gbc.gridy = 10;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(birthLabel, gbc);
		gbc.gridy = 11;
		gbc.anchor = GridBagConstraints.WEST;
		paneProfile.add(birthDateChooser, gbc);        
                
        tabbedPanel.addTab("Tu perfil", paneProfile);
        
        //https://docs.oracle.com/javase/tutorial/uiswing/components/scrollpane.html
        //Para hacer que haya solo scroll vertical
        JPanel panelTeam = new JPanel();
        JScrollPane scrollPaneTeam = new JScrollPane(panelTeam);
        scrollPaneTeam.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPaneTeam.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
       // for(Player p : players) {
       //	JTable tableTeammates = new JTable(tableModel);
       // 	String[] columnNames = {"ID", "Categoría", "Autor", "Coordenadas"};
       //	DefaultTableModel tableModel = new DefaultTableModel(null, columnNames);
       //	panelTeam.add(tableTeammates);
       // }
		tabbedPanel.addTab("Tu equipo", scrollPaneTeam);

        JPanel panelSearch = new JPanel();
        panelSearch.add(new JLabel("Buscador de otros jugadores y equipos"));
        tabbedPanel.addTab("Buscador", panelSearch);

        JPanel panelGame = new JPanel();
        panelGame.add(new JLabel("Registro de partidos que habéis jugado"));
        tabbedPanel.addTab("Partidos", panelGame);
        add(tabbedPanel);
 
		setVisible(true);
	}
	
}
