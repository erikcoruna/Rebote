package db;


import static org.junit.Assert.assertTrue;

import java.util.GregorianCalendar;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import domain.Game;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
import domain.UserRepositoryException;

public class SQLiteDBManagerTest {
	
	private static SQLiteDBManager dbManager;
	private static Team testTeam;
	private static Player testPlayer;
	private static Trainer testTrainer;
	private static Player testPlayerWithTeam;
	private static Trainer testTrainerWithTeam;
	private static Game testGame;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbManager = new SQLiteDBManager();
		dbManager.connect("src/db/rebote.db");
		testTeam = new Team(0, "Test", "Test", "Test", "Test", League.A);
		testPlayer = new Player(0, "Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", null, 0, 0.0f);
		testTrainer = new Trainer(0, "Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", null);
		testPlayerWithTeam = new Player(0, "Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", testTeam, 0, 0.0f);
		testTrainerWithTeam = new Trainer(0, "Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", testTeam);
		testGame = new Game(0, "Test", "Test", 0, 0, 0, 0, 0, 0);
	}
	
	@Test
	public void testStorePlayer() throws UserRepositoryException {
		int playersBefore = dbManager.getAllPlayers().size();
		dbManager.storePlayer(testPlayer);
		dbManager.storePlayer(testPlayerWithTeam);
		int playersAfter = dbManager.getAllPlayers().size();
		assertTrue(playersBefore < playersAfter);
	}
	
	@Test
	public void testStoreTrainer() throws UserRepositoryException {
		int trainersBefore = dbManager.getAllTrainers().size();
		dbManager.storeTrainer(testTrainer);
		dbManager.storeTrainer(testTrainerWithTeam);
		int trainersAfter = dbManager.getAllTrainers().size();
		assertTrue(trainersBefore < trainersAfter);
	}
	
	@Test
	public void testStoreTeam() throws UserRepositoryException {
		int teamsBefore = dbManager.getAllTeams().size();
		dbManager.storeTeam(testTeam);
		int teamsAfter = dbManager.getAllTeams().size();
		assertTrue(teamsBefore < teamsAfter);
	}
	
	@Test
	public void testStoreGame() throws UserRepositoryException {
		int gamesBefore = dbManager.getAllGames().size();
		dbManager.storeGame(testGame);
		int gamesAfter = dbManager.getAllGames().size();
		assertTrue(gamesBefore < gamesAfter);
	}
	
	@Test
	public void testDeletePlayer() throws UserRepositoryException {
		int playersBefore = dbManager.getAllPlayers().size();
		dbManager.deletePlayer(testPlayer);
		dbManager.deletePlayer(testPlayerWithTeam);
		int playersAfter = dbManager.getAllPlayers().size();
		assertTrue(playersAfter < playersBefore);
	}
	
	@Test
	public void testDeleteTrainer() throws UserRepositoryException {
		int trainersBefore = dbManager.getAllTrainers().size();
		dbManager.deleteTrainer(testTrainer);
		dbManager.deleteTrainer(testTrainerWithTeam);
		int trainersAfter = dbManager.getAllTrainers().size();
		assertTrue(trainersBefore < trainersAfter);
	}
	
	@Test
	public void testDeleteTeam() throws UserRepositoryException {
		int teamsBefore = dbManager.getAllTeams().size();
		dbManager.deleteTeam(testTeam);
		int teamsAfter = dbManager.getAllTeams().size();
		assertTrue(teamsBefore < teamsAfter);
	}
	
	@Test
	public void testDeleteGame() throws UserRepositoryException {
		int gamesBefore = dbManager.getAllGames().size();
		dbManager.deleteGame(testGame);
		int gamesAfter = dbManager.getAllGames().size();
		assertTrue(gamesBefore < gamesAfter);
	}
	
	@AfterClass
	public static void logOut() throws Exception {
		dbManager.disconnect();
	}
}
