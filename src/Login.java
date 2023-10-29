import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

    public Login() {
		setSize(480, 560);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Iniciar Sesión");
	
		// https://stackoverflow.com/questions/42964669/placing-button-panel-in-center-java-swing
        // Se ha modificado parte del código para adecuarlo a nuestro proyecto con los botones y textos necesarios.
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		JLabel labelName = new JLabel("Introduzca un nombre de usuario:");
		JTextField textFieldName = new JTextField();
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword = new JLabel("Introduzca su contraseña:");
		JPasswordField passwordFieldPassword = new JPasswordField();
		passwordFieldPassword.setPreferredSize(new Dimension(300, 20));
		JLabel labelForget = new JLabel("¿Ha olvidado su contraseña?");
		// https://stackoverflow.com/questions/2715118/how-to-change-the-size-of-the-font-of-a-jlabel-to-take-the-maximum-size
		// Para cambiar el tamaño de la fuente.
		labelForget.setFont(new Font(labelForget.getFont().getName(), Font.PLAIN, 10));
		
		panel.add(labelName, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(textFieldName, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(passwordFieldPassword, gbc);
		panel.add(new JLabel(" "), gbc);
		panel.add(labelForget, gbc);
		
		for (int i = 0; i < 3; i++) {
			panel.add(new JLabel(" "), gbc);
		}
		
		JButton confirm = new JButton("Confirmar");
		JButton cancel = new JButton("Cancelar");

		panel.add(confirm, gbc);
		panel.add(cancel, gbc);
		
		add(panel);
		setVisible(true);
		
		// https://github.com/andoni-eguiluz/ud-progII-2023/blob/master/Clase2023-2/src/tema5/resueltos/ej5b8/VentanaPrincipal.java
        // Cogido la parte de los ActionListener y modificado para nuestro código.
//		confirm.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//			}
//		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Start windowStart = new Start();
				windowStart.setVisible(true);
				setVisible(false);
			}
		});
//		labelForget.addMouseListener(new MouseAdapter() {
//			@Override
//			public void mouseClicked(MouseEvent e) {
//				
//			}
//			@Override
//			public void mouseEntered(MouseEvent e) {
//				labelForget.setForeground(Color.BLUE);
//			}
//			@Override
//			public void mouseExited(MouseEvent e) {
//				labelForget.setForeground(null);
//			}
//		});
    }
}
