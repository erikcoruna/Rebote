package db;

import java.sql.Array;
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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import domain.IUserRepository;
import domain.Player;
import domain.Trainer;
import domain.User;
import domain.UserRepositoryException;

// Por el momento solo hemos creado una base de datos con SQLite, pero en un futuro pueden integrarse más bases de datos
// como PostgresSQL, y por eso está implementada la interfaz IUserRepository.
public class SQLiteDBManager implements IUserRepository {
	// https://github.com/unaguil/prog3-ejemplos-codigo/tree/master/src/tema7/manager
	// Se han modificado algunas líneas respecto al código.
	private Connection connection = null;
	private SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public GregorianCalendar stringToCalendar(String str) {
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(isoDateFormat.parse(str));
		} catch (ParseException e) {
			calendar.setTime(null);
		}
		return calendar;
	}
	
	public String calendarToString(GregorianCalendar calendar) {
		return isoDateFormat.format(calendar.getTime());
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
	
	public void createPlayerTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR, category VARCHAR,"
					+ " height INT, weight DECIMAL, team VARCHAR)");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error creando la tabla 'player' en la base de datos.");
		}
	}
	
	public void createTrainerTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS trainer (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR, teams VARCHAR)");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error creando la tabla 'trainer' en la base de datos.");
		}
	}
	
	public void dropTable(String tableName) throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
		} catch (SQLException e) {
			throw new UserRepositoryException("Error borrando la tabla '" + tableName + "' de la base de datos.");
		}
	}
	
	@Override
	public void storePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player (name, firstSurname,"
				+ " secondSurname, password, birthDate, country, category, height, weight, team) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, player.getName());
			preparedStatement.setString(2, player.getFirstSurname());
			preparedStatement.setString(3, player.getSecondSurname());
			preparedStatement.setString(4, player.getPassword());
			preparedStatement.setString(5, calendarToString(player.getBirthDate()));
			preparedStatement.setString(6, player.getCountry());
			preparedStatement.setString(7, player.getCategory());
			preparedStatement.setInt(8, player.getHeight());
			preparedStatement.setFloat(9, player.getWeight());
			preparedStatement.setString(10, player.getTeam());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM player");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				player.setId(newId);
			} else {
				throw new UserRepositoryException("Error generando el id en la base de datos.");
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido guardar el jugador en la base de datos.", e);
		}
	}
	
	@Override
	public void storeTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trainer (name, firstSurname,"
				+ " secondSurname, password, birthDate, country, teams) VALUES (?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, trainer.getName());
			preparedStatement.setString(2, trainer.getFirstSurname());
			preparedStatement.setString(3, trainer.getSecondSurname());
			preparedStatement.setString(4, trainer.getPassword());
			preparedStatement.setString(5, calendarToString(trainer.getBirthDate()));
			preparedStatement.setString(6, trainer.getCountry());
			preparedStatement.setString(7, trainer.getTeams().toString());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM trainer");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				trainer.setId(newId);
			} else {
				throw new UserRepositoryException("Error generando el id en la base de datos.");
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido guardar el entrenador en la base de datos.", e);
		}
	}
	
	@Override
	public Player getPlayer(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, category, height, weight, team FROM player WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Player player = new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(resultSet.getString("name"));
				player.setFirstSurname(resultSet.getString("firstSurname"));
				player.setSecondSurname(resultSet.getString("secondSurname"));
				player.setPassword(resultSet.getString("password"));
				player.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				player.setCountry(resultSet.getString("country"));
				player.setCategory(resultSet.getString("category"));
				player.setHeight(resultSet.getInt("height"));
				player.setWeight(resultSet.getFloat("weight"));
				player.setTeam(resultSet.getString("team"));
				return player;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo el jugador con ese id.", e);
		}
	}
	
	@Override
	public Trainer getTrainer(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, teams FROM trainer WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Trainer trainer = new Trainer();
				trainer.setId(resultSet.getInt("id"));
				trainer.setName(resultSet.getString("name"));
				trainer.setFirstSurname(resultSet.getString("firstSurname"));
				trainer.setSecondSurname(resultSet.getString("secondSurname"));
				trainer.setPassword(resultSet.getString("password"));
				trainer.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				trainer.setCountry(resultSet.getString("country"));
				Vector<String> teams = new Vector<String>();
				for (String team : resultSet.getString("teams").split(",")) {
					teams.add(team.replaceAll("[\\[\\]]", ""));
				}
				trainer.setTeams(teams);
				return trainer;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo el entrenador con ese id.", e);
		}
	}
	
	@Override
	public List<Player> getAllPlayers() throws UserRepositoryException {
		List<Player> players = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, name, firstSurname, secondSurname, password, birthDate, country, category, height, weight, team FROM player");
			
			while (resultSet.next()) {
				Player player = new Player();
				player.setId(resultSet.getInt("id"));
				player.setName(resultSet.getString("name"));
				player.setFirstSurname(resultSet.getString("firstSurname"));
				player.setSecondSurname(resultSet.getString("secondSurname"));
				player.setPassword(resultSet.getString("password"));
				player.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				player.setCountry(resultSet.getString("country"));
				player.setCategory(resultSet.getString("category"));
				player.setHeight(resultSet.getInt("height"));
				player.setWeight(resultSet.getFloat("weight"));
				player.setTeam(resultSet.getString("team"));
				players.add(player);
			}
			
			return players;
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo los usuarios de la base de datos.", e);
		}
	}
	
	@Override
	public List<Trainer> getAllTrainers() throws UserRepositoryException {
		List<Trainer> trainers = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, name, firstSurname, secondSurname, password, birthDate, country, teams FROM trainer");
			
			while (resultSet.next()) {
				Trainer trainer = new Trainer();
				trainer.setId(resultSet.getInt("id"));
				trainer.setName(resultSet.getString("name"));
				trainer.setFirstSurname(resultSet.getString("firstSurname"));
				trainer.setSecondSurname(resultSet.getString("secondSurname"));
				trainer.setPassword(resultSet.getString("password"));
				trainer.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				trainer.setCountry(resultSet.getString("country"));
				Vector<String> teams = new Vector<String>();
				for (String team : resultSet.getString("teams").split(",")) {
					teams.add(team.replaceAll("[\\[\\]]", ""));
				}
				trainer.setTeams(teams);
				trainers.add(trainer);
			}
			
			return trainers;
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo los usuarios de la base de datos.", e);
		}
	}
	
	@Override
	public void deletePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM player WHERE id = ?")) {
			preparedStatement.setInt(1, player.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido eliminar el jugador " + player.getId() + " de la base de datos.", e);
		}
	}
	
	@Override
	public void deleteTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trainer WHERE id = ?")) {
			preparedStatement.setInt(1, trainer.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido eliminar el jugador " + trainer.getId() + " de la base de datos.", e);
		}
	}
	
	@Override
	public void updatePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ?, category = ?, height = ?, weight = ?, team = ? WHERE id = ?")) {
			preparedStatement.setString(1, player.getName());
			preparedStatement.setString(2, player.getFirstSurname());
			preparedStatement.setString(3, player.getSecondSurname());
			preparedStatement.setString(4, player.getPassword());
			preparedStatement.setString(5, calendarToString(player.getBirthDate()));
			preparedStatement.setString(6, player.getCountry());
			preparedStatement.setString(7, player.getCategory());
			preparedStatement.setInt(8, player.getHeight());
			preparedStatement.setFloat(9, player.getHeight());
			preparedStatement.setString(10, player.getTeam());
			preparedStatement.setInt(11, player.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido actualizar el jugador en la base de datos.", e);
		}
	}

	@Override
	public void updateTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE trainer SET name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ?, teams = ? WHERE id = ?")) {
			preparedStatement.setString(1, trainer.getName());
			preparedStatement.setString(2, trainer.getFirstSurname());
			preparedStatement.setString(3, trainer.getSecondSurname());
			preparedStatement.setString(4, trainer.getPassword());
			preparedStatement.setString(5, calendarToString(trainer.getBirthDate()));
			preparedStatement.setString(6, trainer.getCountry());
			preparedStatement.setString(7, trainer.getTeams().toString());
			preparedStatement.setInt(8, trainer.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido actualizar el jugador en la base de datos.", e);
		}
	}
}
