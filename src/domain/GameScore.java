package domain;


public class GameScore {
	private static int contadorID = 0;
	
	public int id;
	public Team team;
	public Player author;
	
	public GameScore(Player author) {
		this.id = ++contadorID;
		this.author = author;
		this.team = author.getTeam();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Player getAuthor() {
		return author;
	}

	public void setAuthor(Player author) {
		this.author = author;
	}
	
	
	
}
