package domain;

import java.util.GregorianCalendar;

public class Referee extends User{
	
	public Referee(int id, String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country, Team team) {
		super(id, username, name, firstSurname, secondSurname, password, birthDate, country, null);
	}
	
	public Referee(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country, Team team) {
		super(username, name, firstSurname, secondSurname, password, birthDate, country, null);
	}
	
	public Referee() {
		super();
	}

}
