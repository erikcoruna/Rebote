package collections;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import db.SQLiteDBManager;
import domain.Game;
import domain.IReboteCollections;
import domain.Team;

public class ReboteCollections implements IReboteCollections {

	private static SQLiteDBManager dbManager = new SQLiteDBManager();
	
	public static Map<Integer, Integer> gamesPlayedPerTeam(Path dbPath) throws Exception {
		dbManager.connect(dbPath.toString());
		Map<Integer, Integer> result = new LinkedHashMap<>();
		List<Team> teams = dbManager.getAllTeams();
		List<Game> games = dbManager.getAllGames();
		
		for (Team team : teams) {
			result.putIfAbsent(team.getId(), 0);
		}
		
		for (Game game : games) {
			if (result.containsKey(game.getTeam1())) {
				result.put(game.getTeam1(), result.get(game.getTeam1()) + 1);
			}

			if (result.containsKey(game.getTeam2())) {
				result.put(game.getTeam2(), result.get(game.getTeam2()) + 1);
			}
		}
		
		// Erik Coruña Rodríguez_2024-01-14_16-20.txt
		// Se ha utilizado exactamente el código proporcionado por ChatGPT.
		Comparator<Integer> comparator = (teamId1, teamId2) -> {
			int compareResult = Integer.compare(result.get(teamId1), result.get(teamId2));
			if (compareResult == 0) {
				return Integer.compare(teamId1, teamId2);
			}
			return compareResult * -1;
		};
		Map<Integer, Integer> resultOrdered = new TreeMap<>(comparator);
		resultOrdered.putAll(result);
		
		return resultOrdered;
	}
	
	public static List<Game> gamesWin(Team team, Path dbPath) throws Exception {
		dbManager.connect(dbPath.toString());
		List<Game> result = new ArrayList<>();
		List<Game> games = dbManager.getAllGames();
		
		games.forEach(game -> {
			if (game.getTeam1() == team.getId()) {
				if (game.getTeamScore1() > game.getTeamScore2()) {
					result.add(game);
				}
			} else if (game.getTeam2() == team.getId()) {
				if (game.getTeamScore2() > game.getTeamScore1()) {
					result.add(game);
				}
			}
		});
		
		return result;
	}
	
	public static List<Game> gamesLoseOrTie(Team team, Path dbPath) throws Exception {
		dbManager.connect(dbPath.toString());
		List<Game> result = new ArrayList<>();
		List<Game> games = dbManager.getAllGames();
		
		games.forEach(game -> {
			if (game.getTeam1() == team.getId()) {
				if (game.getTeamScore1() <= game.getTeamScore2()) {
					result.add(game);
				}
			} else if (game.getTeam2() == team.getId()) {
				if (game.getTeamScore2() <= game.getTeamScore1()) {
					result.add(game);
				}
			}
		});
		
		return result;
	}
	
	public static int teamDoneFoults(Team team, Path dbPath) throws Exception {
		int result = 0;
		dbManager.connect(dbPath.toString());
		List<Game> games = dbManager.getAllGames();
		
		for (Game game : games) {
			if (game.getTeam1() == team.getId()) {
				result += game.getTeamFoults1();
			} else if (game.getTeam2() == team.getId()) {
				result += game.getTeamFoults2();
			}
		}
		
		return result;
	}
	
	public static int teamBeenFoults(Team team, Path dbPath) throws Exception {
		int result = 0;
		dbManager.connect(dbPath.toString());
		List<Game> games = dbManager.getAllGames();
		
		for (Game game : games) {
			if (game.getTeam1() == team.getId()) {
				result += game.getTeamFoults2();
			} else if (game.getTeam2() == team.getId()) {
				result += game.getTeamFoults1();
			}
		}
		
		return result;
	}
}
