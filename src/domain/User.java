package domain;
import java.util.Date;

public class User {
	public String name;
	public String firstSurname;
	public String secondSurname;
	public String password;
	public String id;
	public Date birthDate;
	public String country;
	
	public User(String name, String firstSurname, String secondSurname, String password, String id, Date birthDay, String country) {
		super();
		this.name = name;
		this.firstSurname = firstSurname;
		this.secondSurname = secondSurname;
		this.password = password;
		this.id = id;
		this.birthDate = birthDate;
		this.country = country;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getBirthDay() {
		return birthDate;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDate = birthDay;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}

