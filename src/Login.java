import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
//import java.awt.GridLayout;
//import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame {

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args) {
		new Login();
	}

    public Login() {
		setSize(480, 480);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Log In");
	
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.NORTH;
		gbc.anchor = GridBagConstraints.CENTER;
		gbc.fill = GridBagConstraints.HORIZONTAL;
	
		JPanel registro = new JPanel(new GridBagLayout());
		
		JLabel lname = new JLabel("Introduzca un nombre de usuario:");
		JTextField tname = new JTextField();
		tname.setPreferredSize(new Dimension(300, 20));
		JLabel lpass = new JLabel("Introduzca su contraseña:");
		JPasswordField ppass = new JPasswordField();
		ppass.setPreferredSize(new Dimension(300, 20));
		JLabel forget = new JLabel("¿Ha olvidado su contraseña?");
		
		registro.add(lname, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(tname, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(lpass, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(ppass, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(forget, gbc);
		registro.add(new JLabel(" "), gbc);
		
		for (int i = 0; i < 4; i++) {
			registro.add(new JLabel(" "), gbc);
		}
		
	
		GridBagConstraints gbc2 = new GridBagConstraints();
		gbc2.anchor = GridBagConstraints.SOUTH;
		gbc2.anchor = GridBagConstraints.EAST;
	
		JPanel acept = new JPanel(new GridBagLayout());
		JButton confirmar = new JButton("Confirmar");
		JButton cancelar = new JButton("Cancelar");
	
		acept.add(confirmar, gbc2);
		acept.add(cancelar, gbc2);
		panel.add(registro, gbc);
		panel.add(acept, gbc2);
		add(panel);
		
		setVisible(true);
    }
    
//  botonIniciarSesion.addActionListener(new ActionListener() {
//  @Override
//  public void actionPerformed(ActionEvent e) {
//      String usuario = usuarioField.getText();
//      char[] contrasena = contrasenaField.getPassword();
//      // Aquí puedes agregar la lógica de autenticación
//      
//      if (usuario.equals("usuarioEjemplo") && String.valueOf(contrasena).equals("contrasenaEjemplo")) {
//          JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso");
//      } else {
//          JOptionPane.showMessageDialog(frame, "Inicio de sesión fallido");
//      }
//      
//      // Limpia los campos después del intento de inicio de sesión
//      usuarioField.setText("");
//      contrasenaField.setText("");
//  }
//});
}
