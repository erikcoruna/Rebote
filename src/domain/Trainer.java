package domain;

import java.util.GregorianCalendar;

public class Trainer extends User {
	public String[] teams;

	public Trainer(int id, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate,
			String country, String[] teams) {
		super(id, name, firstSurname, secondSurname, password, birthDate, country);
		this.teams = teams;
	}
	
	public Trainer() {
		super();
	}

	public String[] getTeams() {
		return teams;
	}

	public void setTeams(String[] teams) {
		this.teams = teams;
	}
}
