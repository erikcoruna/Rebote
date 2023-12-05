package domain;

import java.util.GregorianCalendar;

public class User {
	public int id = -1;
	public String username;
	public String name;
	public String firstSurname;
	public String secondSurname;
	public String password;
	public GregorianCalendar birthDate;
	public String country;
	
	public User(int id, String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country) {
		super();
		this.id = id;
		this.username = username;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.password = password;
		this.birthDate = birthDate;
		this.country = country;
	}
	
	public User(String username, String name, String firstSurname, String secondSurname, String password, GregorianCalendar birthDate, String country) {
		super();
		this.username = username;
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.password = password;
		this.birthDate = birthDate;
		this.country = country;
	}
	
	public User() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", firstSurname=" + firstSurname + ", secondSurname="
				+ secondSurname + ", password=" + password + ", birthDate=" + birthDate + ", country=" + country + "]";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFirstSurname() {
		return firstSurname;
	}

	public void setFirstSurname(String firstSurname) {
		this.firstSurname = firstSurname;
	}

	public String getSecondSurname() {
		return secondSurname;
	}

	public void setSecondSurname(String secondSurname) {
		this.secondSurname = secondSurname;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public GregorianCalendar getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(GregorianCalendar birthDate) {
		this.birthDate = birthDate;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}

