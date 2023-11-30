package domain;
import java.util.Date;

public class Player extends User {
	public String category;
	public float height;
	public float weight;
	public String team;
	
	public Player(String name, String firstSurname, String secondSurname, String password, String id, Date birthDay, String country, String category, float height,
			float weight, String team) {
		super(name, firstSurname, secondSurname, password, id, birthDay, country);
		this.category = category;
		this.height = height;
		this.weight = weight;
		this.team = team;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public float getHeight() {
		return height;
	}

	public void setHeight(float height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
}
