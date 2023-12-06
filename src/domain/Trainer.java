package domain;

import java.util.GregorianCalendar;
import java.util.Vector;

public class Trainer extends User {
	public Vector<Team> teams;

	public Trainer(int id, String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate,
			String country, Vector<Team> teams) {
		super(id, username, name, firstSurname, secondSurname, password, birthDate, country);
		this.teams = teams;
	}
	
	public Trainer(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate,
			String country, Vector<Team> teams) {
		super(username, name, firstSurname, secondSurname, password, birthDate, country);
		this.teams = teams;
	}
	
	public Trainer(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate,
			String country) {
		super(username, name, firstSurname, secondSurname, password, birthDate, country);
	}
	
	public Trainer() {
		super();
	}

	public Vector<Team> getTeams() {
		return teams;
	}

	public void setTeams(Vector<Team> teams) {
		this.teams = teams;
	}

	@Override
	public String toString() {
		return "Trainer [teams=" + teams + "]";
	}
}
