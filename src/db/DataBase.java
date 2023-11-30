package db;

import domain.User;

public class DataBase {
	public static void main(String[] args) {
		DBManager dbManager = new DBManager();
		
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			dbManager.createUserTable();
			
			User erik = new User("Erik", "Coruña", "Rodríguez", "prueba", "2004-04-22", "España");
			User ander = new User("Ander", "Herrero", "Pascual", "test", "2004-06-20", "España");
			User iker = new User("Iker", "Larrinaga", "Fortuna", "pruebatest", "2004-09-10", "España");
			dbManager.storeUser(erik);
			dbManager.storeUser(ander);
			dbManager.storeUser(iker);
			
			System.out.println(dbManager.getUser(1));
			
			System.out.println(dbManager.getAllUsers());
			
			dbManager.deleteUser(2);
			
			System.out.println(dbManager.getAllUsers());
			
			iker = dbManager.getUser(3);
			iker.setName("Iker2");
			iker.setCountry("Francia");
			dbManager.updateUser(iker);
			
			System.out.println(dbManager.getAllUsers());
			
			dbManager.disconnect();
		} catch (DBException e) {
			System.out.println("Error intentando acceder a la base de datos.");
			e.printStackTrace();
		}
	}
}
