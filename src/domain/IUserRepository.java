package domain;

import java.util.List;

public interface IUserRepository {
	public void storeUser(User user) throws UserRepositoryException;
	public void deleteUser(User user) throws UserRepositoryException;
	public void updateUser(User user) throws UserRepositoryException;
	public User getUser(int id) throws UserRepositoryException;
	public List<User> getAllUsers() throws UserRepositoryException;
}
