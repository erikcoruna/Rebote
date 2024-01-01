package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Vector;

import domain.IUserRepository;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
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
			e.printStackTrace();
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
			createPlayerTable();
			createTrainerTable();
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
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR, team VARCHAR, category VARCHAR,"
					+ " height INT, weight DECIMAL)");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error creando la tabla 'player' en la base de datos.");
		}
	}
	
	public void createTrainerTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS trainer (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR, team VARCHAR)");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error creando la tabla 'trainer' en la base de datos.");
		}
	}
	
	public void createTeamTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS team (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR,"
					+ " city VARCHAR, stadium VARCHAR, description VARCHAR, league VARCHAR)");
		} catch (SQLException e) {
			throw new UserRepositoryException("Error creando la tabla 'team' en la base de datos.");
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
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player (username, name, firstSurname,"
				+ " secondSurname, password, birthDate, country, team, category, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, player.getUsername());
			preparedStatement.setString(2, player.getName());
			preparedStatement.setString(3, player.getFirstSurname());
			preparedStatement.setString(4, player.getSecondSurname());
			preparedStatement.setString(5, player.getPassword());
			preparedStatement.setString(6, calendarToString(player.getBirthDate()));
			preparedStatement.setString(7, player.getCountry());
			preparedStatement.setString(8, player.getTeam().toString());
			preparedStatement.setString(9, player.getCategory());
			preparedStatement.setInt(10, player.getHeight());
			preparedStatement.setFloat(11, player.getWeight());
			
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
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trainer (username, name, firstSurname,"
				+ " secondSurname, password, birthDate, country, team) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, trainer.getUsername());
			preparedStatement.setString(2, trainer.getName());
			preparedStatement.setString(3, trainer.getFirstSurname());
			preparedStatement.setString(4, trainer.getSecondSurname());
			preparedStatement.setString(5, trainer.getPassword());
			preparedStatement.setString(6, calendarToString(trainer.getBirthDate()));
			preparedStatement.setString(7, trainer.getCountry());
			preparedStatement.setString(8, trainer.getTeam().toString());
			
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
	public void storeTeam(Team team) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO team (name, city,"
				+ " stadium, description, league) VALUES (?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, team.getName());
			preparedStatement.setString(2, team.getCity());
			preparedStatement.setString(3, team.getStadium());
			preparedStatement.setString(4, team.getDescription());
			preparedStatement.setString(5, team.getLeague().toString());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM team");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				team.setId(newId);
			} else {
				throw new UserRepositoryException("Error generando el id en la base de datos.");
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido guardar el equipo en la base de datos.", e);
		}
	}
	
	@Override
	public Player getPlayer(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, team, category, height, weight FROM player WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Player player = new Player();
				player.setId(resultSet.getInt("id"));
				player.setUsername(resultSet.getString("username"));
				player.setName(resultSet.getString("name"));
				player.setFirstSurname(resultSet.getString("firstSurname"));
				player.setSecondSurname(resultSet.getString("secondSurname"));
				player.setPassword(resultSet.getString("password"));
				player.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				player.setCountry(resultSet.getString("country"));
				String teamStr = resultSet.getString("team").replaceAll("[\\[\\]]", "");
				String[] teamSplit = teamStr.split(", ");
				Team team = new Team();
				if (!teamSplit[0].toString().equals("null")) {
					team.setName(teamSplit[0]);
					team.setCity(teamSplit[1]);
					team.setStadium(teamSplit[2]);
					team.setDescription(teamSplit[3]);
					team.setLeague(League.valueOf(teamSplit[4]));
				}
				player.setTeam(team);
				player.setCategory(resultSet.getString("category"));
				player.setHeight(resultSet.getInt("height"));
				player.setWeight(resultSet.getFloat("weight"));
				
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
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, team FROM trainer WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Trainer trainer = new Trainer();
				trainer.setId(resultSet.getInt("id"));
				trainer.setUsername(resultSet.getString("username"));
				trainer.setName(resultSet.getString("name"));
				trainer.setFirstSurname(resultSet.getString("firstSurname"));
				trainer.setSecondSurname(resultSet.getString("secondSurname"));
				trainer.setPassword(resultSet.getString("password"));
				trainer.setBirthDate(stringToCalendar(resultSet.getString("birthDate")));
				trainer.setCountry(resultSet.getString("country"));
				String teamStr = resultSet.getString("team").replaceAll("[\\[\\]]", "");
				String[] teamSplit = teamStr.split(", ");
				Team team = new Team();
				if (!teamSplit[0].toString().equals("null")) {
					team.setName(teamSplit[0]);
					team.setCity(teamSplit[1]);
					team.setStadium(teamSplit[2]);
					team.setDescription(teamSplit[3]);
					team.setLeague(League.valueOf(teamSplit[4]));
				}
				trainer.setTeam(team);
				
				return trainer;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo el entrenador con ese id.", e);
		}
	}
	
	@Override
	public Team getTeam(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, name, city, stadium,"
				+ " description, league FROM team WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Team team = new Team();
				team.setId(resultSet.getInt("id"));
				team.setName(resultSet.getString("name"));
				team.setCity(resultSet.getString("city"));
				team.setStadium(resultSet.getString("stadium"));
				team.setDescription(resultSet.getString("description"));
				team.setLeague(League.valueOf(resultSet.getString("league")));
				
				return team;
			} else {
				return null;
			}
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo el equipo con ese id.", e);
		}
	}
	
	@Override
	public List<Player> getAllPlayers() throws UserRepositoryException {
		List<Player> players = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, username, name, firstSurname, secondSurname,"
					+ " password, birthDate, country, team, category, height, weight FROM player");
			
			while (resultSet.next()) {
				players.add(getPlayer(resultSet.getInt("id")));
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
			ResultSet resultSet = statement.executeQuery("SELECT id, username, name, firstSurname, secondSurname,"
					+ " password, birthDate, country, team FROM trainer");
			
			while (resultSet.next()) {
				trainers.add(getTrainer(resultSet.getInt("id")));
			}
			return trainers;
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo los usuarios de la base de datos.", e);
		}
	}
	
	@Override
	public List<Team> getAllTeams() throws UserRepositoryException {
		List<Team> teams = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, name, city, stadium, description, league FROM team");
			
			while (resultSet.next()) {
				teams.add(getTeam(resultSet.getInt("id")));
			}
			return teams;
		} catch (SQLException e) {
			throw new UserRepositoryException("Error obteniendo los equipos de la base de datos.", e);
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
			throw new UserRepositoryException("No se ha podido eliminar el entrenador " + trainer.getId() + " de la base de datos.", e);
		}
	}
	
	@Override
	public void deleteTeam(Team team) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM team WHERE id = ?")) {
			preparedStatement.setInt(1, team.getId());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido eliminar el equipo " + team.getId() + " de la base de datos.", e);
		}
	}
	
	@Override
	public void updatePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET username = ?, name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ?, team = ?, category = ?, height = ?, weight = ?  WHERE id = ?")) {
			preparedStatement.setString(1, player.getUsername());
			preparedStatement.setString(2, player.getName());
			preparedStatement.setString(3, player.getFirstSurname());
			preparedStatement.setString(4, player.getSecondSurname());
			preparedStatement.setString(5, player.getPassword());
			preparedStatement.setString(6, calendarToString(player.getBirthDate()));
			preparedStatement.setString(7, player.getCountry());
			preparedStatement.setString(8, player.getTeam().toString());
			preparedStatement.setString(9, player.getCategory());
			preparedStatement.setInt(10, player.getHeight());
			preparedStatement.setFloat(11, player.getWeight());
			preparedStatement.setInt(12, player.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido actualizar el jugador en la base de datos.", e);
		}
	}

	@Override
	public void updateTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE trainer SET username = ?, name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ?, team = ? WHERE id = ?")) {
			preparedStatement.setString(1, trainer.getUsername());
			preparedStatement.setString(2, trainer.getName());
			preparedStatement.setString(3, trainer.getFirstSurname());
			preparedStatement.setString(4, trainer.getSecondSurname());
			preparedStatement.setString(5, trainer.getPassword());
			preparedStatement.setString(6, calendarToString(trainer.getBirthDate()));
			preparedStatement.setString(7, trainer.getCountry());
			preparedStatement.setString(8, trainer.getTeam().toString());
			preparedStatement.setInt(9, trainer.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido actualizar el jugador en la base de datos.", e);
		}
	}
	
	@Override
	public void updateTeam(Team team) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE team SET name = ?, city = ?, stadium = ?, description = ?,"
				+ " league = ? WHERE id = ?")) {
			preparedStatement.setString(1, team.getName());
			preparedStatement.setString(2, team.getCity());
			preparedStatement.setString(3, team.getStadium());
			preparedStatement.setString(4, team.getDescription());
			preparedStatement.setString(5, team.getLeague().toString());
			preparedStatement.setInt(6, team.getId());
			
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			throw new UserRepositoryException("No se ha podido actualizar el jugador en la base de datos.", e);
		}
	}
}
