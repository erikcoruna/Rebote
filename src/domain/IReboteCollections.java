package domain;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface IReboteCollections {
	public static Map<Integer, Integer> gamesPlayedPerTeam(Path dbPath) throws Exception {
		return null;
	}
	public static List<Game> gamesWin(Team team, Path dbPath) throws Exception {
		return null;
	}
	public static List<Game> gamesLoseOrTie(Team team, Path dbPath) throws Exception {
		return null;
	}
	public static int teamDoneFoults(Team team, Path dbPath) throws Exception {
		return 0;
	}
	public static int teamBeenFoults(Team team, Path dbPath) throws Exception {
		return 0;
	}
	public static List<List<Team>> createLeague(Path dbPath) throws Exception {
		return null;
	}
	public static void createLeagueAux(List<List<List<Team>>> games, List<List<Team>> weekListGames, List<Team> teams, List<Team> remainingTeams) throws Exception {
	}
}
