package db;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Vector;

import domain.Player;
import domain.Trainer;
import domain.UserRepositoryException;

public class DataBase {
	// https://github.com/unaguil/prog3-ejemplos-codigo/tree/master/src/tema7/manager
	// Se han modificado algunas líneas respecto al código.
	public static void main(String[] args) {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		
		try {
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			dbManager.createPlayerTable();
			
			Player erikPlayer = new Player("Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", "A1", 170, 60.4f, "team1");
			Player anderPlayer = new Player("Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", "A2", 196, 76.2f, "team8");
			Player ikerPlayer = new Player("Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", "B1", 175, 65.7f, "team3");
			Player hugoPlayer = new Player("Hugo", "Rey", "Rodríguez", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", "B2", 171, 63.3f, "team5");
			
			dbManager.storePlayer(erikPlayer);
			dbManager.storePlayer(anderPlayer);
			dbManager.storePlayer(ikerPlayer);
			dbManager.storePlayer(hugoPlayer);
			
			System.out.println(dbManager.getPlayer(1));
			System.out.println(dbManager.getAllPlayers());
			dbManager.deletePlayer(ikerPlayer);
			hugoPlayer.setCountry("Francia");
			hugoPlayer.setSecondSurname("García");
			dbManager.updatePlayer(hugoPlayer);
			System.out.println(dbManager.getPlayer(4));
			
			dbManager.createTrainerTable();
			
			Trainer erikTrainer = new Trainer("Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", new Vector<String>(Arrays.asList("team1", "team3", "team4", "team5")));
			Trainer anderTrainer = new Trainer("Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", new Vector<String>(Arrays.asList("team2", "team6")));
			Trainer ikerTrainer = new Trainer("Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", new Vector<String>(Arrays.asList("team7", "team8", "team9")));
			Trainer hugoTrainer = new Trainer("Hugo", "Rey", "Rodríguez", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", new Vector<String>(Arrays.asList("team10")));
			
			dbManager.storeTrainer(erikTrainer);
			dbManager.storeTrainer(anderTrainer);
			dbManager.storeTrainer(ikerTrainer);
			dbManager.storeTrainer(hugoTrainer);
			
			System.out.println(dbManager.getTrainer(1));
			System.out.println(dbManager.getAllTrainers());
			dbManager.deleteTrainer(ikerTrainer);
			hugoTrainer.setCountry("Francia");
			hugoTrainer.setSecondSurname("García");
			dbManager.updateTrainer(hugoTrainer);
			System.out.println(dbManager.getTrainer(4));
			
			dbManager.disconnect();
		} catch (UserRepositoryException e) {
			System.out.println("Error intentando acceder a la base de datos.");
			e.printStackTrace();
		}
	}
}
