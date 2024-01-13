package domain;

import java.nio.file.Path;

public interface IFileManager {
	public void importPlayersFromFile(Path path, Path dbPath) throws UserRepositoryException;
	public void importTrainersFromFile(Path path, Path dbPath) throws UserRepositoryException;
	public void importTeamsFromFile(Path path, Path dbPath) throws UserRepositoryException;
	public void importGamesFromFile(Path path, Path dbPath) throws UserRepositoryException;
	public void exportPlayersToFile(Path path, Path dbPath) throws UserRepositoryException;
	public void exportTrainersToFile(Path path, Path dbPath) throws UserRepositoryException;
	public void exportTeamsToFile(Path path, Path dbPath) throws UserRepositoryException;
	public void exportGamesToFile(Path path, Path dbPath) throws UserRepositoryException;
}
