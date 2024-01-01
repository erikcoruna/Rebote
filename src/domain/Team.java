package domain;

public class Team {
	
	public int id = -1;
	public String name;
	public String city;
	public String stadium;
	public String description;
	public League league;
	
	public Team(int id, String name, String city, String stadium, String description, League league) {
		super();
		this.id = id;
		this.name = name;
		this.city = city;
		this.stadium = stadium;
		this.description = description;
		this.league = league;
	}
	
	public Team(String name, String city, String stadium, String description, League league) {
		super();
		this.name = name;
		this.city = city;
		this.stadium = stadium;
		this.description = description;
		this.league = league;
	}
	
	public Team() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	@Override
	public String toString() {
		return "[" + id + ", " + name + ", " + city + ", " + stadium + ", "
				+ description + ", " + league + "]";
	}
}
