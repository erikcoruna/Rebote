package domain;

import java.util.List;

public interface IUserRepository {
	public void createPlayerTable() throws UserRepositoryException;
	public void createTrainerTable() throws UserRepositoryException;
	public void createRefereeTable() throws UserRepositoryException;
	public void createTeamTable() throws UserRepositoryException;
	public void createGameTable() throws UserRepositoryException;
	public void dropTable(String tableName) throws UserRepositoryException;
	public void storePlayer(Player player) throws UserRepositoryException;
	public void storeTrainer(Trainer trainer) throws UserRepositoryException;
	public void storeTeam(Team team) throws UserRepositoryException;
	public void storeGame(Game game) throws UserRepositoryException;
	public void deletePlayer(Player player) throws UserRepositoryException;
	public void deleteTrainer(Trainer trainer) throws UserRepositoryException;
	public void deleteTeam(Team team) throws UserRepositoryException;
	public void deleteGame(Game game) throws UserRepositoryException;
	public void updatePlayer(Player player) throws UserRepositoryException;
	public void updateTrainer(Trainer trainer) throws UserRepositoryException;
	public void updateTeam(Team team) throws UserRepositoryException;
	public void updateGame(Game game) throws UserRepositoryException;
	public Player getPlayer(int id) throws UserRepositoryException;
	public Trainer getTrainer(int id) throws UserRepositoryException;
	public Team getTeam(int id) throws UserRepositoryException;
	public Game getGame(int id) throws UserRepositoryException;
	public List<Player> getAllPlayers() throws UserRepositoryException;
	public List<Trainer> getAllTrainers() throws UserRepositoryException;
	public List<Team> getAllTeams() throws UserRepositoryException;
	public List<Game> getAllGames() throws UserRepositoryException;
}
