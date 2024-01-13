package domain;

public class Game {
	private int id = -1;
	private String stadium;
	private String referee;
	private int team1;
	private int team2;
	private int teamScore1;
	private int teamScore2;
	private int teamFoults1;
	private int teamFoults2;
	
	public Game(int id, String stadium, String referee, int team1, int team2,
			int teamScore1, int teamScore2,
			int teamFoults1, int teamFoults2) {
		super();
		this.id = id;
		this.stadium = stadium;
		this.referee = referee;
		this.team1 = team1;
		this.team2 = team2;
		this.teamScore1 = teamScore1;
		this.teamScore2 = teamScore2;
		this.teamFoults1 = teamFoults1;
		this.teamFoults2 = teamFoults2;
	}
	
	public Game(String stadium, String referee, int team1, int team2,
			int teamScore1, int teamScore2,
			int teamFoults1, int teamFoults2) {
		super();
		this.stadium = stadium;
		this.referee = referee;
		this.team1 = team1;
		this.team2 = team2;
		this.teamScore1 = teamScore1;
		this.teamScore2 = teamScore2;
		this.teamFoults1 = teamFoults1;
		this.teamFoults2 = teamFoults2;
	}

	public Game() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStadium() {
		return stadium;
	}

	public void setStadium(String stadium) {
		this.stadium = stadium;
	}

	public String getReferee() {
		return referee;
	}

	public void setReferee(String referee) {
		this.referee = referee;
	}

	public int getTeam1() {
		return team1;
	}

	public void setTeam1(int team1) {
		this.team1 = team1;
	}

	public int getTeam2() {
		return team2;
	}

	public void setTeam2(int team2) {
		this.team2 = team2;
	}

	public int getTeamScore1() {
		return teamScore1;
	}

	public void setTeamScore1(int teamScore1) {
		this.teamScore1 = teamScore1;
	}

	public int getTeamScore2() {
		return teamScore2;
	}

	public void setTeamScore2(int teamScore2) {
		this.teamScore2 = teamScore2;
	}

	public int getTeamFoults1() {
		return teamFoults1;
	}

	public void setTeamFoults1(int teamFoults1) {
		this.teamFoults1 = teamFoults1;
	}

	public int getTeamFoults2() {
		return teamFoults2;
	}

	public void setTeamFoults2(int teamFoults2) {
		this.teamFoults2 = teamFoults2;
	}
}
