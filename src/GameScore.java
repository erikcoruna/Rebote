import java.awt.Point;
import java.util.Date;

public class GameScore extends Player {
	public int score;
	public Point coordinates;
	
	public GameScore(String name, String password, String id, Date birthDay, String country, String category,
			float height, float weight, String team, int score, Point coordinates) {
		super(name, password, id, birthDay, country, category, height, weight, team);
		this.score = score;
		this.coordinates = coordinates;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Point getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(Point coordinates) {
		this.coordinates = coordinates;
	}
}
