package domain;

public class UserRepositoryException extends Exception {
	private static final long serialVersionUID = 1L;

	public UserRepositoryException(String message) {
		super(message);
	}
	
	public UserRepositoryException(String message, Throwable e) {
		super(message, e);
	}
}
