package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import domain.IUserRepository;
import domain.User;
import domain.UserRepositoryException;

// Por el momento solo hemos creado una base de datos con SQLite, pero en un futuro pueden integrarse más bases de datos
// como PostgresSQL, y por eso está implementada la interfaz IUserRepository.
public class SQLiteDBManager implements IUserRepository {
	// https://github.com/unaguil/prog3-ejemplos-codigo/tree/master/src/tema7/manager
	// Se han modificado algunas líneas respecto al código.
	private Connection connection = null;
	private SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	private GregorianCalendar stringToCalendar(String str) {
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(isoDateFormat.parse(str));
		} catch (ParseException e) {
			calendar.setTime(null);
		}
		return calendar;
	}
	
	private String calendarToString(GregorianCalendar calendar) {
		return isoDateFormat.format(calendar);
	}
	
	public void connect(String dbPath) throws UserRepositoryException {
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (ClassNotFoundException e) {
			throw new UserRepositoryException("Error cargando el driver de la base de datos.", e);
		} catch (SQLException e) {
			throw new UserRepositoryException("Error conectando a la base de datos.", e);
		}
	}
	
	public void disconnect() throws UserRepositoryException {
		try {
			connection.close();
		} catch (SQLException e) {
			throw new UserRepositoryException("Error cerrando la conexión con la base de datos.");
		}
	}
	
	public void createUserTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS user (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR)");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error creando la tabla 'user' en la base de datos.");
		}
	}
	
	public void dropUserTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("DROP TABLE IF EXISTS user");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error borrando la tabla 'user' de la base de datos.");
		}
	}
	
	@Override
	public void storeUser(User user) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user (name, firstSurname,"
				+ " secondSurname, password, birthDate, country) VALUES (?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getFirstSurname());
			preparedStatement.setString(3, user.getSecondSurname());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, calendarToString(user.getBirthDate()));
			preparedStatement.setString(6, user.getCountry());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM user");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				user.setId(newId);
			} else {
				throw new UserRepositoryException("Error generando el id en la base de datos.");
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido guardar el usuario en la base de datos.", e);
		}
	}
	
	@Override
	public User getUser(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, firstSurname, secondSurname,"
				+ " password, birthDate, country FROM user WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				User user = new User();
				user.setId(resultSet.getInt("id"));
				user.setName(resultSet.getString("name"));
				user.setFirstSurname(resultSet.getString("firstSurname"));
				user.setSecondSurname(resultSet.getString("secondSurname"));
				user.setPassword(resultSet.getString("password"));
				user.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				user.setCountry(resultSet.getString("country"));
				return user;
			} else {
				return new User();
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo el usuario con ese id.", e);
		}
	}
	
	@Override
	public List<User> getAllUsers() throws UserRepositoryException {
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
				user.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				user.setCountry(resultSet.getString("country"));
				users.add(user);
			}
			
			return users;
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo los usuarios de la base de datos.", e);
		}
	}
	
	@Override
	public void deleteUser(User user) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM user WHERE id = ?")) {
			preparedStatement.setInt(1, user.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido eliminar el usuario " + user.getId() + " de la base de datos.", e);
		}
	}
	
	@Override
	public void updateUser(User user) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE user SET name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ? WHERE id = ?")) {
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getFirstSurname());
			preparedStatement.setString(3, user.getSecondSurname());
			preparedStatement.setString(4, user.getPassword());
			preparedStatement.setString(5, calendarToString(user.getBirthDate()));
			preparedStatement.setString(6, user.getCountry());
			preparedStatement.setInt(7, user.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido actualizar el usuario en la base de datos.", e);
		}
	}
}
