package domain;

import java.nio.file.Path;

public interface IFileManager {
	public void importPlayersFromFile(Path path) throws UserRepositoryException;
	public void importTrainersFromFile(Path path) throws UserRepositoryException;
	public void importTeamsFromFile(Path path) throws UserRepositoryException;
	public void exportPlayersToFile(Path path) throws UserRepositoryException;
	public void exportTrainersToFile(Path path) throws UserRepositoryException;
	public void exportTeamsToFile(Path path) throws UserRepositoryException;
}
