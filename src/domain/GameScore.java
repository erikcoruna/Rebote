package domain;
import java.awt.Point;;

public class GameScore {
	public String id;
	public String category;
	public String name;
	public int score;
	public Point coordinates;
	
	public GameScore(String id, String category, String name, int score, Point coordinates) {
		this.id = id;
		this.category = category;
		this.name = name;
		this.score = score;
		this.coordinates = coordinates;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
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
