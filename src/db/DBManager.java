package db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import domain.User;

public class DBManager {
	// https://github.com/unaguil/prog3-ejemplos-codigo/tree/master/src/tema7/manager
	// Se han modificado algunas líneas respecto al código.
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
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate VARCHAR, country VARCHAR)");
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
	
	// Añadir un usuario
	public void storeUser(User user) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, firstSurname, secondSurname, password, birthDate, country) VALUES (?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getFirstSurname());
			preparedStatement.setString(3, user.getSecondSurname());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getBirthDate());
			preparedStatement.setString(6, user.getCountry());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM user");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				user.setId(newId);
			} else {
				throw new DBException("Error generando el id.");
			}
		} catch (SQLException e) {
			throw new DBException("No se ha podido guardar el usuario en la base de datos.", e);
		}
	}
	
	// Obtener usuario
	public User getUser(int id) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, firstSurname, secondSurname, password, birthDate, country FROM user WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setFirstSurname(resultSet.getString("firstSurname"));
				user.setSecondSurname(resultSet.getString("secondSurname"));
				user.setPassword(resultSet.getString("password"));
				user.setBirthDate(resultSet.getString("birthDate"));
				user.setCountry(resultSet.getString("country"));
				return user;
			} else {
				return new User();
			}
		} catch (SQLException e) {
			throw new DBException("Error obteniendo el usuario con ese id.", e);
		}
	}
	
	// Obtener todos los usuarios
	public List<User> getAllUsers() throws DBException {
		List<User> users = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, name, firstSurname, secondSurname, password, birthDate, country FROM user");
			
			while (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setFirstSurname(resultSet.getString("firstSurname"));
				user.setSecondSurname(resultSet.getString("secondSurname"));
				user.setPassword(resultSet.getString("password"));
				user.setBirthDate(resultSet.getString("birthDate"));
				user.setCountry(resultSet.getString("country"));
				users.add(user);
			}
			
			return users;
		} catch (SQLException e) {
			throw new DBException("Error obteniendo los usuarios de la base de datos.", e);
		}
	}
	
	// Elimina un usuario
	public void deleteUser(int id) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("No se ha podido eliminar el usuario " + id + " de la base de datos.", e);
		}
	}
	
	// Actualiza un usuario
	public void updateUser(User user) throws DBException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET name = ?, firstSurname = ?, secondSurname = ?, password = ?, birthDate = ?, country = ? WHERE id = ?")) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getFirstSurname());
			preparedStatement.setString(3, user.getSecondSurname());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, user.getBirthDate());
			preparedStatement.setString(6, user.getCountry());
			preparedStatement.setInt(7, user.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new DBException("No se ha podido actualizar el usuario en la base de datos.", e);
		}
	}
}
