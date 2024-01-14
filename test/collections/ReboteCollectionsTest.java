package collections;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import db.SQLiteDBManager;
import domain.Game;
import domain.League;
import domain.Team;

public class ReboteCollectionsTest {

	private static SQLiteDBManager dbManager;
	private static Path tempDB;
	private static Team testTeam1;
	private static Team testTeam2;
	private static Team testTeam3;
	private static Game testGame1;
	private static Game testGame2;
	private static Game testGame3;
	private static Game testGame4;
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		dbManager = new SQLiteDBManager();
		String tempDir = System.getProperty("java.io.tmpdir");
		tempDB = Paths.get(tempDir, "test.db");
		dbManager.connect(tempDB.toString());
		testTeam1 = new Team("Test1", "Test1", "Test1", "Test1", League.A);
		testTeam2 = new Team("Test2", "Test2", "Test2", "Test2", League.A);
		testTeam3 = new Team("Test3", "Test3", "Test3", "Test3", League.A);
		testGame1 = new Game("Test", "Test", 1, 2, 0, 0, 0, 0);
		testGame2 = new Game("Test", "Test", 1, 2, 50, 60, 1, 2);
		testGame3 = new Game("Test", "Test", 1, 2, 60, 50, 2, 1);
		testGame4 = new Game("Test", "Test", 2, 1, 50, 50, 2, 2);
		dbManager.storeTeam(testTeam1);
		dbManager.storeTeam(testTeam2);
		dbManager.storeTeam(testTeam3);
		dbManager.storeGame(testGame1);
		dbManager.storeGame(testGame2);
		dbManager.storeGame(testGame3);
		dbManager.storeGame(testGame4);
	}
	
	@Test
	public void testGamesPlayedPerTeam() throws Exception {
		Map<Integer, Integer> result = ReboteCollections.gamesPlayedPerTeam(tempDB);
		assertNotNull(result);
		assertEquals(3, result.keySet().size());
		result.values().forEach(value -> {
			assertNotNull(value);
		});
	}
	
	@Test
	public void testGamesWin() throws Exception {
		List<Game> result = ReboteCollections.gamesWin(testTeam1, tempDB);
		assertNotNull(result);
		assertEquals(1, result.size());
	}
	
	@Test
	public void testGamesLoseOrTie() throws Exception {
		List<Game> result = ReboteCollections.gamesLoseOrTie(testTeam1, tempDB);
		assertNotNull(result);
		assertEquals(3, result.size());
	}
	
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		dbManager.disconnect();
		new File(tempDB.toString()).delete();
	}
}
