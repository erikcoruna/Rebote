import java.awt.Point;;

public class GameScore {
	public String name;
	public int score;
	public Point coordinates;
	
	public GameScore(String name, int score, Point coordinates) {
		this.name = name;
		this.score = score;
		this.coordinates = coordinates;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
