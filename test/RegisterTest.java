import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

public class RegisterTest {

	private Register register;
	
    @Test
    public void testButtonConfirmPressed() {
        register = new Register();

        // Test 1: Crear un usuario correcto.
        register.textFieldName.setText("testUser");
        register.passwordFieldPassword.setText("password");
        register.passwordFieldPassword2.setText("password");
        register.coach.setSelected(true);

        register.buttonConfirmPressed();

        assertTrue("Archivo CSV debe contener el usuario", readFile("files\\register.csv").contains("testUser"));
        assertEquals("El nombre de usuario debería estar borrado", "", register.textFieldName.getText());
        assertEquals("La contraseña del usuario debería estar borrada", "", new String(register.passwordFieldPassword.getPassword()));
        assertEquals("La contraseña repetida del usuario debería estar borrada", "", new String(register.passwordFieldPassword2.getPassword()));
        

        // Test 2: Contraseñas diferentes.
//        register.textFieldName.setText("testUser2");
//        register.passwordFieldPassword.setText("password1");
//        register.passwordFieldPassword2.setText("password2");
//        
//        register.buttonConfirmPressed();
//        
//        assertFalse("Archivo CSV no debe contener el usuario", readFile("files\\register.csv").contains("testUser2"));
//        assertEquals("La contraseña está borrada", "", new String(register.passwordFieldPassword.getPassword()));
//        assertEquals("La contraseña repetida está borrada", "", new String(register.passwordFieldPassword2.getPassword()));
    }
    
    private String readFile(String filePath) {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllLines(path).toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}