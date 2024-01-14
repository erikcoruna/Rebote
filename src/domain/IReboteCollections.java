package domain;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

public interface IReboteCollections {
	public Map<Integer, Integer> gamesPlayedPerTeam(Path dbPath) throws Exception;
	public List<Game> gamesWin(Team team, Path dbPath) throws Exception;
	public List<Game> gamesLoseOrTie(Team team, Path dbPath) throws Exception;
	public int teamDoneFoults(Team team, Path dbPath) throws Exception;
	public int teamBeenFoults(Team team, Path dbPath) throws Exception;
}
