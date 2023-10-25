
public class Player extends User{
	protected boolean authorized;

	public boolean isAuthorized() {
		return authorized;
	}
	public void setAuthorized(boolean authorized) {
		this.authorized = authorized;
	}

	public Player(boolean authorized, String ID, String name, String passw) {
		super(ID, name, passw);
		authorized = false;
	}
}
