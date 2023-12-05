package domain;

import java.util.List;

public interface IUserRepository {
	public void storePlayer(Player player) throws UserRepositoryException;
	public void storeTrainer(Trainer trainer) throws UserRepositoryException;
	public void deletePlayer(Player player) throws UserRepositoryException;
	public void deleteTrainer(Trainer trainer) throws UserRepositoryException;
	public void updatePlayer(Player player) throws UserRepositoryException;
	public void updateTrainer(Trainer trainer) throws UserRepositoryException;
	public Player getPlayer(int id) throws UserRepositoryException;
	public Trainer getTrainer(int id) throws UserRepositoryException;
	public List<Player> getAllPlayers() throws UserRepositoryException;
	public List<Trainer> getAllTrainers() throws UserRepositoryException;
}
