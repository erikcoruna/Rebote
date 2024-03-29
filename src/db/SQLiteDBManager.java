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
import java.util.logging.Logger;

import domain.Game;
import domain.IUserRepository;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
import domain.UserRepositoryException;
import gui.WindowStart;
import io.ConfigReader;

// Por el momento solo hemos creado una base de datos con SQLite, pero en un futuro pueden integrarse más bases de datos
// como PostgresSQL, y por eso está implementada la interfaz IUserRepository.
public class SQLiteDBManager implements IUserRepository {
	
	static Logger logger = Logger.getLogger(WindowStart.class.getName());
	
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
			createTeamTable();
			createGameTable();
			logger.info("Conectado a la base de datos.");
		} catch (ClassNotFoundException e) {
			logger.warning(ConfigReader.dbConnectError);
			throw new UserRepositoryException(ConfigReader.dbConnectError, e);
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbConnectError);
			throw new UserRepositoryException(ConfigReader.dbConnectError, e);
		}
	}
	
	public void disconnect() throws UserRepositoryException {
		try {
			connection.close();
			logger.info("Desconectado de la base de datos.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbDisconnectError);
			throw new UserRepositoryException(ConfigReader.dbDisconnectError);
		}
	}
	
	public void createPlayerTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS player (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR, team_id INTEGER, "
					+ " height INT, weight DECIMAL, FOREIGN KEY(team_id) REFERENCES team(id))");
			logger.info("Tabla player creada.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbCreateTableError);
			throw new UserRepositoryException(ConfigReader.dbCreateTableError);
		}
	}
	
	public void createTrainerTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS trainer (id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR, name VARCHAR,"
					+ " firstSurname VARCHAR, secondSurname VARCHAR, password VARCHAR, birthDate TEXT, country VARCHAR, team_id INTEGER, FOREIGN KEY(team_id) REFERENCES team(id))");
			logger.info("Tabla trainer creada.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbCreateTableError);
			throw new UserRepositoryException(ConfigReader.dbCreateTableError);
		}
	}
	
	public void createTeamTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS team (id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR,"
					+ " city VARCHAR, stadium VARCHAR, description VARCHAR, league VARCHAR)");
			logger.info("Tabla team creada.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbCreateTableError);
			throw new UserRepositoryException(ConfigReader.dbCreateTableError);
		}
	}
	
	public void createGameTable() throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("CREATE TABLE IF NOT EXISTS game (id INTEGER PRIMARY KEY AUTOINCREMENT, stadium VARCHAR,"
					+ " referee VARCHAR, team1 INTEGER, team2 INTEGER, teamScore1 INTEGER, teamScore2 INTEGER,"
					+ " teamFoults1 INTEGER, teamFoults2 INTEGER)");
			logger.info("Tabla game creada.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbCreateTableError);
			throw new UserRepositoryException(ConfigReader.dbCreateTableError);
		}
	}
	
	public void dropTable(String tableName) throws UserRepositoryException {
		try (Statement statement = connection.createStatement()) {
			statement.executeUpdate("DROP TABLE IF EXISTS " + tableName);
			logger.info("Tabla " + tableName + " eliminada.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbDropTableError);
			throw new UserRepositoryException(ConfigReader.dbDropTableError);
		}
	}
	
	@Override
	public void storePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO player (username, name, firstSurname,"
				+ " secondSurname, password, birthDate, country, team_id, height, weight) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, player.getUsername());
			preparedStatement.setString(2, player.getName());
			preparedStatement.setString(3, player.getFirstSurname());
			preparedStatement.setString(4, player.getSecondSurname());
			preparedStatement.setString(5, player.getPassword());
			preparedStatement.setString(6, calendarToString(player.getBirthDate()));
			preparedStatement.setString(7, player.getCountry());
			try {
				preparedStatement.setInt(8, player.getTeam().getId());
			} catch (NullPointerException e) {
				logger.info("Jugador sin equipo: " + player.getName());
			}
			preparedStatement.setInt(9, player.getHeight());
			preparedStatement.setFloat(10, player.getWeight());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM player");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				player.setId(newId);
				logger.info("Jugador " + player.getName() + " guardado en la base de datos.");
			} else {
				logger.warning(ConfigReader.dbStoreError);
				throw new UserRepositoryException(ConfigReader.dbStoreError);
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbStoreError);
			throw new UserRepositoryException(ConfigReader.dbStoreError, e);
		}
	}
	
	@Override
	public void storeTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO trainer (username, name, firstSurname,"
				+ " secondSurname, password, birthDate, country, team_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, trainer.getUsername());
			preparedStatement.setString(2, trainer.getName());
			preparedStatement.setString(3, trainer.getFirstSurname());
			preparedStatement.setString(4, trainer.getSecondSurname());
			preparedStatement.setString(5, trainer.getPassword());
			preparedStatement.setString(6, calendarToString(trainer.getBirthDate()));
			preparedStatement.setString(7, trainer.getCountry());
			try {
				preparedStatement.setInt(8, trainer.getTeam().getId());
			} catch (NullPointerException e) {
				logger.info("Entrenador sin equipo: " + trainer.getName());
			}
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM trainer");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				trainer.setId(newId);
				logger.info("Entrenador " + trainer.getName() + " guardado.");
			} else {
				logger.warning(ConfigReader.dbStoreError);
				throw new UserRepositoryException(ConfigReader.dbStoreError);
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbStoreError);
			throw new UserRepositoryException(ConfigReader.dbStoreError, e);
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
				logger.info("Equipo " + team.getName() + " guardado.");
			} else {
				logger.warning(ConfigReader.dbStoreError);
				throw new UserRepositoryException(ConfigReader.dbStoreError);
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbStoreError);
			throw new UserRepositoryException(ConfigReader.dbStoreError, e);
		}
	}
	
	@Override
	public void storeGame(Game game) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO game (stadium, referee,"
				+ " team1, team2, teamScore1, teamScore2, teamFoults1, teamFoults2) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
				Statement statement = connection.createStatement()) {
			preparedStatement.setString(1, game.getStadium());
			preparedStatement.setString(2, game.getReferee());
			preparedStatement.setInt(3, game.getTeam1());
			preparedStatement.setInt(4, game.getTeam2());
			preparedStatement.setInt(5, game.getTeamScore1());
			preparedStatement.setInt(6, game.getTeamScore2());
			preparedStatement.setInt(7, game.getTeamFoults1());
			preparedStatement.setInt(8, game.getTeamFoults2());
			
			preparedStatement.executeUpdate();
			
			ResultSet resultSet = statement.executeQuery("SELECT last_insert_rowid() AS id FROM game");
			if (resultSet.next()) {
				int newId = resultSet.getInt("id");
				game.setId(newId);
				logger.info("Partido " + game.getId() + " guardado.");
			} else {
				logger.warning(ConfigReader.dbStoreError);
				throw new UserRepositoryException(ConfigReader.dbStoreError);
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbStoreError);
			throw new UserRepositoryException(ConfigReader.dbStoreError, e);
		}
	}
	
	@Override
	public Player getPlayer(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, team_id, height, weight FROM player WHERE id = ?")) {
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
				player.setTeam(getTeam(resultSet.getInt("team_id")));
				player.setHeight(resultSet.getInt("height"));
				player.setWeight(resultSet.getFloat("weight"));
				
				logger.info("Jugador " + player.getName() + " obtenido.");
				return player;
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
		}
	}
	
	@Override
	public Trainer getTrainer(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, team_id FROM trainer WHERE id = ?")) {
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
				trainer.setTeam(getTeam(resultSet.getInt("team_id")));
				
				logger.info("Entrenador " + trainer.getName() + " obtenido.");
				return trainer;
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
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
				
				logger.info("Equipo " + team.getName() + " obtenido.");
				return team;
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
		}
	}
	
	@Override
	public Game getGame(int id) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, stadium, referee, team1,"
				+ " team2, teamScore1, teamScore2, teamFoults1, teamFoults2 FROM game WHERE id = ?")) {
			preparedStatement.setInt(1, id);
			
			ResultSet resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				Game game = new Game();
				game.setId(resultSet.getInt("id"));
				game.setStadium(resultSet.getString("stadium"));
				game.setReferee(resultSet.getString("referee"));
				game.setTeam1(resultSet.getInt("team1"));
				game.setTeam2(resultSet.getInt("team2"));
				game.setTeamScore1(resultSet.getInt("teamScore1"));
				game.setTeamScore2(resultSet.getInt("teamScore2"));
				game.setTeamFoults1(resultSet.getInt("teamFoults1"));
				game.setTeamFoults2(resultSet.getInt("teamFoults2"));
				
				logger.info("Partido " + game.getId() + " obtenido.");
				return game;
			} else {
				return null;
			}
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
		}
	}
	
	@Override
	public List<Player> getAllPlayers() throws UserRepositoryException {
		List<Player> players = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, username, name, firstSurname, secondSurname,"
					+ " password, birthDate, country, team_id, height, weight FROM player");
			
			while (resultSet.next()) {
				players.add(getPlayer(resultSet.getInt("id")));
			}
			
			logger.info("Jugadores obtenidos.");
			return players;
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
		}
	}
	
	@Override
	public List<Trainer> getAllTrainers() throws UserRepositoryException {
		List<Trainer> trainers = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, username, name, firstSurname, secondSurname,"
					+ " password, birthDate, country, team_id FROM trainer");
			
			while (resultSet.next()) {
				trainers.add(getTrainer(resultSet.getInt("id")));
			}
			
			logger.info("Entrenadores obtenidos.");
			return trainers;
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
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
			
			logger.info("Equipos obtenidos.");
			return teams;
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
		}
	}
	
	@Override
	public List<Game> getAllGames() throws UserRepositoryException {
		List<Game> games = new ArrayList<>();
		try (Statement statement = connection.createStatement()) {
			ResultSet resultSet = statement.executeQuery("SELECT id, stadium, referee, team1, team2, teamScore1, teamScore2,"
					+ " teamFoults1, teamFoults2 FROM game");
			
			while (resultSet.next()) {
				games.add(getGame(resultSet.getInt("id")));
			}
			
			logger.info("Partidos obtenidos.");
			return games;
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbGetError);
			throw new UserRepositoryException(ConfigReader.dbGetError, e);
		}
	}
	
	@Override
	public void deletePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM player WHERE id = ?")) {
			preparedStatement.setInt(1, player.getId());
			preparedStatement.executeUpdate();
			logger.info("Jugador " + player.getName() + " eliminado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbDeleteError);
			throw new UserRepositoryException(ConfigReader.dbDeleteError, e);
		}
	}
	
	@Override
	public void deleteTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM trainer WHERE id = ?")) {
			preparedStatement.setInt(1, trainer.getId());
			preparedStatement.executeUpdate();
			logger.info("Entrenador " + trainer.getName() + " eliminado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbDeleteError);
			throw new UserRepositoryException(ConfigReader.dbDeleteError, e);
		}
	}
	
	@Override
	public void deleteTeam(Team team) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM team WHERE id = ?")) {
			preparedStatement.setInt(1, team.getId());
			preparedStatement.executeUpdate();
			logger.info("Equipo " + team.getName() + " eliminado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbDeleteError);
			throw new UserRepositoryException(ConfigReader.dbDeleteError, e);
		}
	}
	
	@Override
	public void deleteGame(Game game) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM game WHERE id = ?")) {
			preparedStatement.setInt(1, game.getId());
			preparedStatement.executeUpdate();
			logger.info("Partido " + game.getId() + " eliminado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbDeleteError);
			throw new UserRepositoryException(ConfigReader.dbDeleteError, e);
		}
	}
	
	@Override
	public void updatePlayer(Player player) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE player SET username = ?, name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ?, team_id = ?, height = ?, weight = ?  WHERE id = ?")) {
			preparedStatement.setString(1, player.getUsername());
			preparedStatement.setString(2, player.getName());
			preparedStatement.setString(3, player.getFirstSurname());
			preparedStatement.setString(4, player.getSecondSurname());
			preparedStatement.setString(5, player.getPassword());
			preparedStatement.setString(6, calendarToString(player.getBirthDate()));
			preparedStatement.setString(7, player.getCountry());
			try {
				preparedStatement.setInt(8, player.getTeam().getId());
			} catch (NullPointerException e) {
				logger.info("Jugador sin equipo: " + player.getName());
			}
			preparedStatement.setInt(9, player.getHeight());
			preparedStatement.setFloat(10, player.getWeight());
			preparedStatement.setInt(11, player.getId());
			
			preparedStatement.executeUpdate();
			logger.info("Jugador " + player.getName() + " actualizado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbUpdateError);
			throw new UserRepositoryException(ConfigReader.dbUpdateError, e);
		}
	}

	@Override
	public void updateTrainer(Trainer trainer) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE trainer SET username = ?, name = ?, firstSurname = ?, secondSurname = ?,"
				+ " password = ?, birthDate = ?, country = ?, team_id = ? WHERE id = ?")) {
			preparedStatement.setString(1, trainer.getUsername());
			preparedStatement.setString(2, trainer.getName());
			preparedStatement.setString(3, trainer.getFirstSurname());
			preparedStatement.setString(4, trainer.getSecondSurname());
			preparedStatement.setString(5, trainer.getPassword());
			preparedStatement.setString(6, calendarToString(trainer.getBirthDate()));
			preparedStatement.setString(7, trainer.getCountry());
			try {
				preparedStatement.setInt(8, trainer.getTeam().getId());
			} catch (NullPointerException e) {
				logger.info("Entrenador sin equipo: " + trainer.getName());
			}
			preparedStatement.setInt(9, trainer.getId());
			
			preparedStatement.executeUpdate();
			logger.info("Entrenador " + trainer.getName() + " actualizado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbUpdateError);
			throw new UserRepositoryException(ConfigReader.dbUpdateError, e);
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
			logger.info("Equipo " + team.getName() + " actualizado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbUpdateError);
			throw new UserRepositoryException(ConfigReader.dbUpdateError, e);
		}
	}
	
	@Override
	public void updateGame(Game game) throws UserRepositoryException {
		try (PreparedStatement preparedStatement = connection.prepareStatement("UPDATE game SET stadium = ?, referee = ?, team1 = ?, team2 = ?,"
				+ " teamScore1 = ?, teamScore2 = ?, teamFoults1 = ?, teamFoults2 = ? WHERE id = ?")) {
			preparedStatement.setString(1, game.getStadium());
			preparedStatement.setString(2, game.getReferee());
			preparedStatement.setInt(3, game.getTeam1());
			preparedStatement.setInt(4, game.getTeam2());
			preparedStatement.setInt(5, game.getTeamScore1());
			preparedStatement.setInt(6, game.getTeamScore2());
			preparedStatement.setInt(7, game.getTeamFoults1());
			preparedStatement.setInt(8, game.getTeamFoults2());
			preparedStatement.setInt(9, game.getId());
			
			preparedStatement.executeUpdate();
			logger.info("Partido " + game.getId() + " actualizado.");
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbUpdateError);
			throw new UserRepositoryException(ConfigReader.dbUpdateError, e);
		}
	}
	
	@Override
	public List<Player> getPlayersFromTeam(Team team) throws UserRepositoryException {
		List<Player> result = new ArrayList<>();
		try (PreparedStatement preparedStatement = connection.prepareStatement("SELECT id, username, name, firstSurname, secondSurname,"
				+ " password, birthDate, country, team_id, height, weight FROM player WHERE team_id = ?")) {
			preparedStatement.setInt(1, team.getId());
			
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
				player.setTeam(getTeam(resultSet.getInt("team_id")));
				player.setHeight(resultSet.getInt("height"));
				player.setWeight(resultSet.getFloat("weight"));
				result.add(player);
			}
		} catch (UserRepositoryException e) {
			logger.warning(ConfigReader.dbConnectError);
		} catch (SQLException e) {
			logger.warning(ConfigReader.dbConnectError);
		}
		return result;
	}
}
