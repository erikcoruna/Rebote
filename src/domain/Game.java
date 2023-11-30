package domain;
import java.util.LinkedHashMap;

public class Game {
	private String id;
	private String location;
	private String referee;
	private String teamName1;
	private String teamName2;
	private LinkedHashMap<String, Integer> teamScore1;
	private LinkedHashMap<String, Integer> teamScore2;
	private LinkedHashMap<String, Integer> teamFoults1;
	private LinkedHashMap<String, Integer> teamFoults2;
	
	public Game(String id, String location, String referee, String teamName1, String teamName2,
			LinkedHashMap<String, Integer> teamScore1, LinkedHashMap<String, Integer> teamScore2,
			LinkedHashMap<String, Integer> teamFoults1, LinkedHashMap<String, Integer> teamFoults2) {
		super();
		this.id = id;
		this.location = location;
		this.referee = referee;
		this.teamName1 = teamName1;
		this.teamName2 = teamName2;
		this.teamScore1 = teamScore1;
		this.teamScore2 = teamScore2;
		this.teamFoults1 = teamFoults1;
		this.teamFoults2 = teamFoults2;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public String getTeamName1() {
		return teamName1;
	}

	public void setTeamName1(String teamName1) {
		this.teamName1 = teamName1;
	}

	public String getTeamName2() {
		return teamName2;
	}

	public void setTeamName2(String teamName2) {
		this.teamName2 = teamName2;
	}

	public LinkedHashMap<String, Integer> getTeamScore1() {
		return teamScore1;
	}

	public void setTeamScore1(LinkedHashMap<String, Integer> teamScore1) {
		this.teamScore1 = teamScore1;
	}

	public LinkedHashMap<String, Integer> getTeamScore2() {
		return teamScore2;
	}

	public void setTeamScore2(LinkedHashMap<String, Integer> teamScore2) {
		this.teamScore2 = teamScore2;
	}

	public LinkedHashMap<String, Integer> getTeamFoults1() {
		return teamFoults1;
	}

	public void setTeamFoults1(LinkedHashMap<String, Integer> teamFoults1) {
		this.teamFoults1 = teamFoults1;
	}

	public LinkedHashMap<String, Integer> getTeamFoults2() {
		return teamFoults2;
	}

	public void setTeamFoults2(LinkedHashMap<String, Integer> teamFoults2) {
		this.teamFoults2 = teamFoults2;
	}
}
