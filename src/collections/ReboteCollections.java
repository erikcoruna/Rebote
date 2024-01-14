package collections;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import db.SQLiteDBManager;
import domain.Game;
import domain.Team;

public class ReboteCollections {

	private static SQLiteDBManager dbManager = new SQLiteDBManager();
	
	public static Map<Integer, Integer> gamesPlayedPerTeam() throws Exception {
		dbManager.connect("resources/db/rebote.db");
		Map<Integer, Integer> result = new HashMap<>();
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
		
		Comparator<Integer> comparator = (teamId1, teamId2) -> {return Integer.compare(result.get(teamId1), result.get(teamId2)) * -1;};
		Map<Integer, Integer> resultOrdered = new TreeMap<>(comparator);
		resultOrdered.putAll(result);
		
		return resultOrdered;
	}
	
	public static List<Game> gamesWin(Team team) throws Exception {
		dbManager.connect("resources/db/rebote.db");
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
	
	public static List<Game> gamesLoseOrTie(Team team) throws Exception {
		dbManager.connect("resources/db/rebote.db");
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
}
