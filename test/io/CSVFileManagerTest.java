package io;

import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.GregorianCalendar;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import db.SQLiteDBManager;
import domain.Game;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;

public class CSVFileManagerTest {
	
	private static CSVFileManager fileManager;
	private static SQLiteDBManager dbManager;
	private static Path tempFile;
	private static Path tempDB;
	private static Team testTeam;
	private static Player testPlayer;
	private static Trainer testTrainer;
	private static Player testPlayerWithTeam;
	private static Trainer testTrainerWithTeam;
	private static Game testGame;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		fileManager = new CSVFileManager();
		dbManager = new SQLiteDBManager();
		String tempDir = System.getProperty("java.io.tmpdir");
		tempFile = Paths.get(tempDir, "test.csv");
		tempDB = Paths.get(tempDir, "test.db");
		testTeam = new Team("Test", "Test", "Test", "Test", League.A);
		testPlayer = new Player("Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", null, 0, 0.0f);
		testTrainer = new Trainer("Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", null);
		testPlayerWithTeam = new Player("Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", testTeam, 0, 0.0f);
		testTrainerWithTeam = new Trainer("Test", "Test", "Test", "Test", "Test", new GregorianCalendar(2004, 4 - 1, 22), "Test", testTeam);
		testGame = new Game("Test", "Test", 0, 0, 0, 0, 0, 0);
	}
	
	@Before
	public void setUpBefore() throws Exception {
		dbManager.connect(tempDB.toString());
	}
	
	@Test
	public void testImportPlayersFromFile() throws Exception {
		dbManager.storePlayer(testPlayer);
		dbManager.storePlayer(testPlayerWithTeam);
		fileManager.exportPlayersToFile(tempFile, tempDB);
		dbManager.deletePlayer(testPlayer);
		dbManager.deletePlayer(testPlayerWithTeam);
		fileManager.importPlayersFromFile(tempFile, tempDB);
		assertNotEquals(dbManager.getAllPlayers().size(), 0);
	}
	
	@Test
	public void testImportTrainersFromFile() throws Exception {
		dbManager.storeTrainer(testTrainer);
		dbManager.storeTrainer(testTrainerWithTeam);
		fileManager.exportTrainersToFile(tempFile, tempDB);
		dbManager.deleteTrainer(testTrainer);
		dbManager.deleteTrainer(testTrainerWithTeam);
		fileManager.importTrainersFromFile(tempFile, tempDB);
		assertNotEquals(dbManager.getAllTrainers().size(), 0);
	}
	
	@Test
	public void testImportTeamsFromFile() throws Exception {
		dbManager.storeTeam(testTeam);
		fileManager.exportTeamsToFile(tempFile, tempDB);
		dbManager.deleteTeam(testTeam);
		fileManager.importTeamsFromFile(tempFile, tempDB);
		assertNotEquals(dbManager.getAllTeams().size(), 0);
	}
	
	@Test
	public void testImportGamesFromFile() throws Exception {
		dbManager.storeGame(testGame);
		fileManager.exportGamesToFile(tempFile, tempDB);
		dbManager.deleteGame(testGame);
		fileManager.importGamesFromFile(tempFile, tempDB);
		assertNotEquals(dbManager.getAllGames().size(), 0);
	}
	
	@Test
	public void testExportPlayersToFile() throws Exception {
		dbManager.storePlayer(testPlayer);
		dbManager.storePlayer(testPlayerWithTeam);
		fileManager.exportPlayersToFile(tempFile, tempDB);
		assertTrue(Files.exists(tempFile));
		assertTrue(Files.probeContentType(tempFile) != null);
	}

	@Test
	public void testExportTrainersToFile() throws Exception {
		dbManager.storeTrainer(testTrainer);
		dbManager.storeTrainer(testTrainerWithTeam);
		fileManager.exportTrainersToFile(tempFile, tempDB);
		assertTrue(Files.exists(tempFile));
		assertTrue(Files.probeContentType(tempFile) != null);
	}
	
	@Test
	public void testExportTeamsToFile() throws Exception {
		dbManager.storeTeam(testTeam);
		fileManager.exportTeamsToFile(tempFile, tempDB);
		assertTrue(Files.exists(tempFile));
		assertTrue(Files.probeContentType(tempFile) != null);
	}
	
	@Test
	public void testExportGamesToFile() throws Exception {
		dbManager.storeGame(testGame);
		fileManager.exportGamesToFile(tempFile, tempDB);
		assertTrue(Files.exists(tempFile));
		assertTrue(Files.probeContentType(tempFile) != null);
	}
	
	@After
	public void tearDownAfter() throws Exception {
		Files.delete(tempFile);
		dbManager.dropTable("player");
		dbManager.dropTable("trainer");
		dbManager.dropTable("team");
		dbManager.dropTable("game");
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dbManager.disconnect();
		new File(tempDB.toString()).delete();
	}
}
