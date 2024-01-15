package domain;

public class FreeThrow extends GameScore{

	public boolean success;
	public FreeThrow(Player author, boolean success) {
		super(author);
		this.success = success;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	
}
