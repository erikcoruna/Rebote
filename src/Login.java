import java.awt.GridLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame {

	private static final long serialVersionUID = 1L;

    public Login() {
    	setTitle("Inicio de sesión");
    	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	setSize(480, 560);
    	
    	
    	
    	
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 150);
        setLayout(new GridLayout(3, 2));
        
        JLabel usuarioLabel = new JLabel("Usuario:");
        JTextField usuarioField = new JTextField();
        
        JLabel contrasenaLabel = new JLabel("Contraseña:");
        JPasswordField contrasenaField = new JPasswordField();
        
        JButton botonIniciarSesion = new JButton("Iniciar Sesión");
        
//        botonIniciarSesion.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                String usuario = usuarioField.getText();
//                char[] contrasena = contrasenaField.getPassword();
//                // Aquí puedes agregar la lógica de autenticación
//                
//                if (usuario.equals("usuarioEjemplo") && String.valueOf(contrasena).equals("contrasenaEjemplo")) {
//                    JOptionPane.showMessageDialog(frame, "Inicio de sesión exitoso");
//                } else {
//                    JOptionPane.showMessageDialog(frame, "Inicio de sesión fallido");
//                }
//                
//                // Limpia los campos después del intento de inicio de sesión
//                usuarioField.setText("");
//                contrasenaField.setText("");
//            }
//        });
//        
//        frame.add(usuarioLabel);
//        frame.add(usuarioField);
//        frame.add(contrasenaLabel);
//        frame.add(contrasenaField);
//        frame.add(botonIniciarSesion);
//        
//        frame.setVisible(true);
    }
}
