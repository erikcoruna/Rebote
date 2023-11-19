import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Login extends JFrame {
	
	public static void main(String[] args) {
		new Login();
	}

	// https://www.digitalocean.com/community/tutorials/logger-in-java-logging-example
	// Cogido para tener un ejemplo de Logger y adecuado a nuestro código.
	Logger logger = Logger.getLogger(Start.class.getName());
	private static final long serialVersionUID = 1L;
	
	JTextField textFieldName = new JTextField();
	JPasswordField passwordFieldPassword = new JPasswordField();

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
		textFieldName.setPreferredSize(new Dimension(300, 20));
		JLabel labelPassword = new JLabel("Introduzca su contraseña:");
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
		confirm.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				confirmButtonPressed();
				logger.info("Pulsado el botón confirm.");
			}
		});
		cancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new Start();
				dispose();
				logger.info("Pulsado el botón cancel.");
			}
		});
		labelForget.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				logger.info("Pulsado el botón labelForget.");
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				labelForget.setForeground(Color.BLUE);
			}
			@Override
			public void mouseExited(MouseEvent e) {
				labelForget.setForeground(null);
			}
		});
		textFieldName.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto.");
			}
		});
		passwordFieldPassword.addFocusListener(new FocusListener() {
			@Override
			public void focusLost(FocusEvent e) {
				logger.info("Salido del campo de texto.");
			}
			@Override
			public void focusGained(FocusEvent e) {
				logger.info("Entrado al campo de texto.");
			}
		});
    }
    
    //https://youtu.be/ScUJx4aWRi0
  	//Cogido parte de lectura en csv y adapatado a nuestro proyecto
    private void confirmButtonPressed() {
    	String username = textFieldName.getText();
    	String password = new String(passwordFieldPassword.getPassword());
    	
    	if (checkValues(username, password)) {
    		PlayerOrTrainer();
    	} else {
    		JOptionPane.showMessageDialog(this, "El usuario y/o contraseña no coincide", "ERROR", JOptionPane.ERROR_MESSAGE);
    	}
    		
    	
    	
    }
    
    private boolean checkValues(String username, String password) {
    	
    	try (BufferedReader reader = new BufferedReader(new FileReader("files\\register.csv"))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] divided = line.split(",");
				String sameUsername = divided[1].trim();
				String samePassword = divided[2].trim();
				
				if (sameUsername.equals(username) && samePassword.equals(password)) {
					return true;
				}
			}
			
			reader.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
    	
    	return false;
    }
    
    private void PlayerOrTrainer() {
    	try (BufferedReader reader = new BufferedReader(new FileReader("files\\register.csv"))) {
			String line;
			
			while ((line = reader.readLine()) != null) {
				String[] divided = line.split(",");
				String samePlayerOrTrainer = divided[0].trim();
				
				if (samePlayerOrTrainer.equals("0")) {
					new HomeTrainer(); 
				} else if (samePlayerOrTrainer.equals("1")) {
					new HomePlayer();
					dispose();
				}
			}
			reader.readLine();
		} catch (IOException e) {
			JOptionPane.showMessageDialog(this, "No se ha encontrado el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}
    }
}
