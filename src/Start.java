import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.html.ImageView;

public class Start extends JFrame {
	
    private static final long serialVersionUID = 1L;

	public Start() {
    	setSize(480, 560);
    	setLocationRelativeTo(null);
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Bienvenido");
        
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        panel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.NORTH;
        
        panel.add(new JLabel("<html><h1><strong>REBOTE</strong></h1></html>"), gbc);
        
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JPanel buttons = new JPanel(new GridBagLayout());
        JButton login = new JButton("Iniciar Sesión");
        JButton register = new JButton("Registrarse");
        login.setPreferredSize(new Dimension(200, 50));
        register.setPreferredSize(new Dimension(200, 50));
        register.setBackground(Color.MAGENTA);
        buttons.add(login, gbc);
        buttons.add(new JLabel(" "), gbc);
        buttons.add(register, gbc);
        
        
        panel.add(buttons, gbc);
        
//        login.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				new Login();
//			}
//        });
//        
//        register.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				new Register();
//			}
//		});
        
        add(panel);
        setVisible(true);
    }
}
