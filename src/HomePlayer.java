import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JLabel;

public class HomePlayer extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static void main(String[] args) {
		new HomePlayer();
	}
	
	public HomePlayer() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Home");
		
		//https://www.discoduroderoer.es/como-crear-pestanas-con-la-clase-jtabbedpane-en-java/
		//Un ejemplo de JTabbedPane, para saber como implementarlo en nuestro código
		JTabbedPane tabbedPanel = new JTabbedPane(); 
		
		JPanel paneProfile = new JPanel();
		paneProfile.add(new JLabel("tus datos"));
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
