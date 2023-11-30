package db;

public class DataBase {
	public static void main(String[] args) {
		DBManager dbManager = new DBManager();
		
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			dbManager.createUserTable();
			
			dbManager.disconnect();
		} catch (DBException e) {
			System.out.println("Error intentando acceder a la base de datos.");
			e.printStackTrace();
		}
	}
}
