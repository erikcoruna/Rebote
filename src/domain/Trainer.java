package domain;

import java.util.GregorianCalendar;

public class Trainer extends User {
	public Trainer(int id, String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate,
			String country, Team team) {
		super(id, username, name, firstSurname, secondSurname, password, birthDate, country, team);
	}
	
	public Trainer(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate,
			String country, Team team) {
		super(username, name, firstSurname, secondSurname, password, birthDate, country, team);
	}
	
	public Trainer() {
		super();
	}
}
