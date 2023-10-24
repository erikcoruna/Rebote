import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class Register extends JFrame {

	private static final long serialVersionUID = 1L;

	public Register() {
		setSize(480, 560);
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

		JLabel nombreUsuario = new JLabel("Introduzca un nombre de usuario:");
		JTextField nombre = new JTextField();
		nombre.setPreferredSize(new Dimension(300, 20));
		JLabel nombreContrasenia = new JLabel("Introduzca su contraseña:");
		JPasswordField contrasenia = new JPasswordField();
		contrasenia.setPreferredSize(new Dimension(300, 20));
		JLabel nombreNContrasenia = new JLabel("Introduzca de nuevo su contraseña:");
		JPasswordField Ncontrasenia = new JPasswordField();
		Ncontrasenia.setPreferredSize(new Dimension(300, 20));
		JLabel eleccion = new JLabel("Elija su rol:");
		JRadioButton entrenador = new JRadioButton("Entrenador");
		JRadioButton jugador = new JRadioButton("Jugador");

		ButtonGroup grupo = new ButtonGroup();
		grupo.add(entrenador);
		grupo.add(jugador);

		registro.add(nombreUsuario, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(nombre, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(nombreContrasenia, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(contrasenia, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(nombreNContrasenia, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(Ncontrasenia, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(eleccion, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(entrenador, gbc);
		registro.add(jugador, gbc);
		registro.add(new JLabel(" "), gbc);
		registro.add(new JLabel(" "), gbc);

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
}