package db;

import java.util.GregorianCalendar;

import domain.Game;
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
			
			Team team1 = new Team("team1", "Bilbao", "Bilbao Basket", "Este es el equipo de Bilbao.", League.A);
			Team team2 = new Team("team2", "Trapaga", "Trapaga Basket", "Este es el equipo de Trapaga.", League.B);
			Team team3 = new Team("team3", "Barakaldo", "Barakaldo Basket", "Este es el equipo de Barakaldo.", League.C);
			Team team4 = new Team("team4", "Bilbao 2", "Bilbao Basket 2", "Este es el equipo de Bilbao 2.", League.C);
			
			dbManager.storeTeam(team1);
			dbManager.storeTeam(team2);
			dbManager.storeTeam(team3);
			dbManager.storeTeam(team4);
			
			System.out.println(dbManager.getAllTeams());
			

			
			Player erikPlayer = new Player("erik.player", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", null, 170, 60.4f);
			Player anderPlayer = new Player("ander.player", "Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", team2, 196, 76.2f);
			Player ikerPlayer = new Player("iker.player", "Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", team3, 175, 65.7f);
			Player hugoPlayer = new Player("hugo.player", "Hugo", "Rey", "Insausti", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", team4, 171, 63.3f);
			
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
			
			
			
			Trainer erikTrainer = new Trainer("erik.trainer", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", null);
			Trainer anderTrainer = new Trainer("ander.trainer", "Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", team2);
			Trainer ikerTrainer = new Trainer("iker.trainer", "Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", team3);
			Trainer hugoTrainer = new Trainer("hugo.trainer", "Hugo", "Rey", "Insausti", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", team4);
			
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
			
			
			
			Game game1 = new Game("Bilbao Basket", "Referee1", 1, 2, 50, 49, 6, 7);
			Game game2 = new Game("Bilbao Basket", "Referee1", 1, 3, 55, 40, 4, 8);
			Game game3 = new Game("Bilbao Basket", "Referee1", 2, 3, 51, 60, 7, 9);
			Game game4 = new Game("Bilbao Basket", "Referee1", 1, 3, 70, 40, 2, 1);
			Game game5 = new Game("Bilbao Basket", "Referee1", 1, 2, 71, 41, 2, 1);
			Game game6 = new Game("Bilbao Basket", "Referee1", 1, 3, 72, 42, 2, 1);
			Game game7 = new Game("Bilbao Basket", "Referee1", 1, 2, 73, 43, 2, 1);
			
			dbManager.storeGame(game1);
			dbManager.storeGame(game2);
			dbManager.storeGame(game3);
			dbManager.storeGame(game4);
			dbManager.storeGame(game5);
			dbManager.storeGame(game6);
			dbManager.storeGame(game7);
			
			System.out.println(dbManager.getGame(1));
			System.out.println(dbManager.getAllGames());
			
			dbManager.disconnect();
		} catch (UserRepositoryException e) {
			System.out.println("Error intentando acceder a la base de datos.");
			e.printStackTrace();
		}
	}
}
