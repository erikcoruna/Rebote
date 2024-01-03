package domain;

import java.nio.file.Path;
import java.util.List;

public interface IFileManager {
	public void importPlayersFromFile(Path path) throws UserRepositoryException;
	public void importTrainersFromFile(Path path) throws UserRepositoryException;
	public void exportPlayersToFile(List<Player> players, Path path) throws UserRepositoryException;
	public void exportTrainersToFile(List<Trainer> trainers, Path path) throws UserRepositoryException;
}
