package domain;
import java.util.Date;

public class User {
	public String name;
	public String password;
	public String id;
	public Date birthDay;
	public String country;
	
	public User(String name, String password, String id, Date birthDay, String country) {
		super();
		this.name = name;
		this.password = password;
		this.id = id;
		this.birthDay = birthDay;
		this.country = country;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
		return birthDay;
	}

	public void setBirthDay(Date birthDay) {
		this.birthDay = birthDay;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}

