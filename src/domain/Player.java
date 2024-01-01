package domain;

import java.util.GregorianCalendar;

public class Player extends User {
	public int height;
	public float weight;
	
	public Player(int id, String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country, Team team, int height,
			float weight) {
		super(id, username, name, firstSurname, secondSurname, password, birthDate, country, team);
		this.height = height;
		this.weight = weight;
	}
	
	public Player(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country, Team team, int height,
			float weight) {
		super(username, name, firstSurname, secondSurname, password, birthDate, country, team);
		this.height = height;
		this.weight = weight;
	}
	
	public Player(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country, Team team) {
		super(username, name, firstSurname, secondSurname, password, birthDate, country, team);
	}
	
	public Player() {
		super();
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}

	@Override
	public String toString() {
		return "[" + height + ", " + weight + "]";
	}
}
