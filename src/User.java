import java.util.Date;

public class User {
	protected String name;
	protected String passw;
	protected String ID;
	protected Date BDay;
	protected String team;
	
	//Getters y Setters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassw() {
		return passw;
	}

	public void setPassw(String passw) {
		this.passw = passw;
	}

	public String getID() {
		return ID;
	}

	public void setID(String ID) {
		this.ID = ID;
	}

	public Date getBDay() {
		return BDay;
	}

	public void setBDay(Date bDay) {
		BDay = bDay;
	}
	
	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}
	
	//es un constructor que va a crear un objeto user, que se añade a una lista de usuarios, sin tener en cuenta ningún dato además del nombre de usuario, contraseña y DNI
	//esto lo hacemos para que el login posterior sea más rápido y sencillo
	public User(String ID, String name, String passw) {
		this.ID = ID;
		this.name = name;
		this.passw = passw;
	}
	
	public String logger() {
		return getID()+"/t"+getName()+"/t"+getPassw();
	}
	
	

	
	
	
}

