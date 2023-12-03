package db;

import java.util.Date;
import java.util.GregorianCalendar;

import domain.User;
import domain.UserRepositoryException;

public class DataBase {
	// https://github.com/unaguil/prog3-ejemplos-codigo/tree/master/src/tema7/manager
	// Se han modificado algunas líneas respecto al código.
	public static void main(String[] args) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			dbManager.createUserTable();
			
			User erik = new User("Erik", "Coruña", "Rodríguez", "prueba", new GregorianCalendar(2004, 22, 4), "España");
			User ander = new User("Ander", "Herrero", "Pascual", "test", new GregorianCalendar(2004, 21, 5), "España");
			User iker = new User("Iker", "Larrinaga", "Fortuna", "pruebatest", new GregorianCalendar(2004, 20, 6), "España");
			dbManager.storeUser(erik);
			dbManager.storeUser(ander);
			dbManager.storeUser(iker);
			
			System.out.println(dbManager.getUser(1));
			
			System.out.println(dbManager.getAllUsers());
			
			dbManager.deleteUser(erik);
			
			System.out.println(dbManager.getAllUsers());
			
			iker = dbManager.getUser(3);
			iker.setName("Iker2");
			iker.setCountry("Francia");
			dbManager.updateUser(iker);
			
			System.out.println(dbManager.getAllUsers());
			
			dbManager.disconnect();
		} catch (UserRepositoryException e) {
			System.out.println("Error intentando acceder a la base de datos.");
			e.printStackTrace();
		}
	}
}
