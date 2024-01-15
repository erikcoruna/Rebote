package collections;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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
	
	public static void main(String[] args) throws Exception {
		System.out.println(createLeague(Paths.get("resources/db/rebote.db")));
		
	}
	
	public static List<List<List<Team>>> createLeague(Path dbPath) throws Exception {
		dbManager.connect(dbPath.toString());
		List<Team> teams = dbManager.getAllTeams();
		
		if (teams.size() % 2 == 0) {
			List<List<List<Team>>> result = new ArrayList<>();
			List<Team> remainingTeams = new ArrayList<>(teams);
			createLeagueAux(result, new ArrayList<>(), teams, remainingTeams);
			addReturnGames(result);
			return result;
		} else {
			System.out.println("El número de equipos no es par.");
		}
		return null;
	}
	
	public static void createLeagueAux(List<List<List<Team>>> games, List<List<Team>> weekListGames, List<Team> teams, List<Team> remainingTeams) throws Exception {
		if (games.size() < teams.size() - 1) {
			if (weekListGames.size() < (teams.size() / 2)) {
				while (true) {
					Team team1 = remainingTeams.remove(new Random().nextInt(remainingTeams.size()));
					Team team2 = remainingTeams.remove(new Random().nextInt(remainingTeams.size()));
					List<Team> game = new ArrayList<>(Arrays.asList(team1, team2));
					List<Team> gameSwap = new ArrayList<>(Arrays.asList(team2, team1));
					boolean containsGame = false;
					for (List<List<Team>> weekGames : games) {
						if (weekGames.contains(game) || weekGames.contains(gameSwap)) {
							containsGame = true;
							remainingTeams.addAll(game);
							break;
						}
					}
					if (!containsGame) {
						weekListGames.add(game);
						break;
					}
				}
				createLeagueAux(games, weekListGames, teams, remainingTeams);
			} else {
				games.add(weekListGames);
				remainingTeams = new ArrayList<>(teams);
				createLeagueAux(games, new ArrayList<>(), teams, remainingTeams);
			}
		}
	}
	
	public static void addReturnGames(List<List<List<Team>>> games) {
		List<List<List<Team>>> resultAux = new ArrayList<>();
		for (List<List<Team>> weekGames : games) {
			List<List<Team>> weekGamesResult = new ArrayList<>();
			for (List<Team> game : weekGames) {
				List<Team> swappedGame = new ArrayList<>(game);
				Collections.swap(swappedGame, 0, 1);
				weekGamesResult.add(swappedGame);
			}
			resultAux.add(weekGamesResult);
		}
		games.addAll(resultAux);
	}
}
