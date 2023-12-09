package db;

import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Vector;

import domain.League;
import domain.Player;
import domain.Team;
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
			
			Player erikPlayer = new Player("erik.player", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", "A1", 170, 60.4f, new Team("team1", "Bilbao", "Bilbao Basket", "Este es el equipo de Bilbao.", League.A));
			Player anderPlayer = new Player("ander.player", "Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", "A2", 196, 76.2f, new Team("team2", "Trapaga", "Trapaga Basket", "Este es el equipo de Trapaga.", League.B));
			Player ikerPlayer = new Player("iker.player", "Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", "B1", 175, 65.7f, new Team("team3", "Barakaldo", "Barakaldo Basket", "Este es el equipo de Barakaldo.", League.C));
			Player hugoPlayer = new Player("hugo.player", "Hugo", "Rey", "Rodríguez", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", "B2", 171, 63.3f, new Team("team4", "Bilbao", "Bilbao Basket 2", "Este es el equipo de Bilbao 2.", League.C));
			
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
			
			Team team1 = new Team("team1", "Bilbao", "Bilbao Basket", "Este es el equipo de Bilbao.", League.A);
			Team team2 = new Team("team2", "Trapaga", "Trapaga Basket", "Este es el equipo de Trapaga.", League.B);
			Team team3 = new Team("team3", "Barakaldo", "Barakaldo Basket", "Este es el equipo de Barakaldo.", League.C);
			Team team4 = new Team("team4", "Bilbao 2", "Bilbao Basket 2", "Este es el equipo de Bilbao 2.", League.C);
			Trainer erikTrainer = new Trainer("erik.trainer", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", new Vector<Team>(Arrays.asList(team1, team2)));
			Trainer anderTrainer = new Trainer("ander.trainer", "Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", new Vector<Team>(Arrays.asList(team2, team3)));
			Trainer ikerTrainer = new Trainer("iker.trainer", "Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", new Vector<Team>(Arrays.asList(team1, team3, team4)));
			Trainer hugoTrainer = new Trainer("hugo.trainer", "Hugo", "Rey", "Rodríguez", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", new Vector<Team>(Arrays.asList(team4)));
			
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
