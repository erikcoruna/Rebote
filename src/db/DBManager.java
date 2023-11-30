package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBManager {
	private Connection connection = null;
	
	// Establece conexión con la base de datos
	public void connect(String dbPath) throws DBException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (ClassNotFoundException e) {
			throw new DBException("Error cargando el driver de la base de datos.", e);
		} catch (SQLException e) {
			throw new DBException("Error conectando a la base de datos.", e);
		}
	}
	
	// Cierra la conexión con la base de datos
	public void disconnect() throws DBException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new DBException("Error cerrando la conexión con la base de datos.");
		}
	}
	
	// Crea la tabla 'user' si no existe
	public void createUserTable() throws DBException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthdate DATE, country VARCHAR)");
		} catch (SQLException e) {
			throw new DBException("Error creando la tabla 'user' en la base de datos.");
		}
	}
	
	// Elimina la tabla 'user' si existe
	public void dropUserTable() throws DBException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("DROP TABLE IF EXISTS user");
		} catch (SQLException e) {
			throw new DBException("Error borrando la tabla 'user' de la base de datos.");
		}
	}
}
