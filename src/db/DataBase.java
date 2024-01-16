package db;

import java.util.GregorianCalendar;
import java.util.logging.Logger;

import domain.Game;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
import domain.UserRepositoryException;
import gui.WindowStart;
import io.ConfigReader;

public class DataBase {
	
	static Logger logger = Logger.getLogger(WindowStart.class.getName());
	
	// https://github.com/unaguil/prog3-ejemplos-codigo/tree/master/src/tema7/manager
	// Se han modificado algunas líneas respecto al código.
	public static void loadTestData() {
		SQLiteDBManager dbManager = new SQLiteDBManager();
		
		try {
			dbManager.connect("resources/db/rebote.db");
			
			dbManager.dropTable("player");
			dbManager.dropTable("trainer");
			dbManager.dropTable("team");
			dbManager.dropTable("game");
			
			dbManager.connect("resources/db/rebote.db");
			
			Team team1 = new Team("team1", "Bilbao", "Bilbao Basket", "Este es el equipo de Bilbao.", League.A);
			Team team2 = new Team("team2", "Trapaga", "Trapaga Basket", "Este es el equipo de Trapaga.", League.B);
			Team team3 = new Team("team3", "Barakaldo", "Barakaldo Basket", "Este es el equipo de Barakaldo.", League.C);
			Team team4 = new Team("team4", "Bilbao 2", "Bilbao Basket 2", "Este es el equipo de Bilbao 2.", League.A);
			
			dbManager.storeTeam(team1);
			dbManager.storeTeam(team2);
			dbManager.storeTeam(team3);
			dbManager.storeTeam(team4);
			
			logger.info("Almacenados todos los equipos.");
			
			Player erikPlayer = new Player("erik.player", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", null, 170, 60.4f);
			Player anderPlayer = new Player("ander.player", "Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", team2, 196, 76.2f);
			Player ikerPlayer = new Player("iker.player", "Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", team3, 175, 65.7f);
			Player hugoPlayer = new Player("hugo.player", "Hugo", "Rey", "Insausti", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", team4, 171, 63.3f);
			Player unaiPlayer = new Player("unai.player", "Unai", "Aguilera", "Irazabal", "prueba5", new GregorianCalendar(1987, 7 - 1, 28), "España", team2, 173, 66.4f);
			Player alexPlayer = new Player("alex.player", "Alex", "Arandilla", "Rochas", "prueba6", new GregorianCalendar(2002, 5 - 1, 18), "España", team1, 185, 75.4f);
			Player guillermoPlayer = new Player("guillermo.player", "Guillermo", "Peralta", "Martín", "prueba7", new GregorianCalendar(2003, 2 - 1, 26), "España", team1, 188, 80.3f);
			Player jonPlayer = new Player("jon.player", "Jon", "Calleja", "Castaños", "prueba8", new GregorianCalendar(2000, 9 - 1, 23), "España", team2, 169, 62.2f);
			Player markelPlayer = new Player("markel.player", "Markel", "Gómez", "Pérez", "prueba9", new GregorianCalendar(1990, 4 - 1, 7), "España", team3, 177, 67.6f);
			Player gaizkaPlayer = new Player("gaizka.player", "Gaizka", "Carrasco", "Bastida", "prueba10", new GregorianCalendar(1995, 10 - 1, 17), "España", team4, 180, 70.1f);
			Player lucasPlayer = new Player("lucas.player", "Lucas", "Echeverría", "Madariaga", "prueba11", new GregorianCalendar(1999, 11 - 1, 3), "España", team2, 184, 72.4f);
			Player iñakiPlayer = new Player("iñaki.player", "Iñaki", "Etxegibel", "Arcocha", "prueba12", new GregorianCalendar(2005, 12 - 1, 2), "España", team1, 172, 63.8f);
			Player davidPlayer = new Player("david.player", "David", "Falcón", "Ruiz", "prueba13", new GregorianCalendar(2004, 6 - 1, 6), "España", team1, 176, 68.4f);
			Player marcoPlayer = new Player("marco.player", "Marco", "Fernández", "González", "prueba14", new GregorianCalendar(2000, 11 - 1, 13), "España", team2, 183, 74.5f);
			Player endikaPlayer = new Player("endika.player", "Endika", "Fernández", "Olabarrieta", "prueba15", new GregorianCalendar(2001, 10 - 1, 23), "España", team3, 182, 71.0f);
			Player aitorPlayer = new Player("aitor.player", "Aitor", "Peral", "Gallastegui", "prueba16", new GregorianCalendar(1999, 3 - 1, 17), "España", team4, 174, 69.7f);
			
			dbManager.storePlayer(erikPlayer);
			dbManager.storePlayer(anderPlayer);
			dbManager.storePlayer(ikerPlayer);
			dbManager.storePlayer(hugoPlayer);
			dbManager.storePlayer(unaiPlayer);
			dbManager.storePlayer(alexPlayer);
			dbManager.storePlayer(guillermoPlayer);
			dbManager.storePlayer(jonPlayer);
			dbManager.storePlayer(markelPlayer);
			dbManager.storePlayer(gaizkaPlayer);
			dbManager.storePlayer(lucasPlayer);
			dbManager.storePlayer(iñakiPlayer);
			dbManager.storePlayer(davidPlayer);
			dbManager.storePlayer(marcoPlayer);
			dbManager.storePlayer(endikaPlayer);
			dbManager.storePlayer(aitorPlayer);
			
			logger.info("Almacenados todos los jugadores");
			
			Trainer erikTrainer = new Trainer("erik.trainer", "Erik", "Coruña", "Rodríguez", "prueba1", new GregorianCalendar(2004, 4 - 1, 22), "España", null);
			Trainer anderTrainer = new Trainer("ander.trainer", "Ander", "Herrero", "Pascual", "prueba2", new GregorianCalendar(2004, 1 - 1, 20), "España", team2);
			Trainer ikerTrainer = new Trainer("iker.trainer", "Iker", "Larrinaga", "Fortuna", "prueba3", new GregorianCalendar(2004, 2 - 1, 18), "España", team3);
			Trainer hugoTrainer = new Trainer("hugo.trainer", "Hugo", "Rey", "Insausti", "prueba4", new GregorianCalendar(2004, 3 - 1, 16), "España", team4);
			Trainer unaiTrainer = new Trainer("unai.trainer", "Unai", "Aguilera", "Irazabal", "prueba5", new GregorianCalendar(1987, 7 - 1, 28), "España", null);
			Trainer alexTrainer = new Trainer("alex.trainer", "Alex", "Arandilla", "Rochas", "prueba6", new GregorianCalendar(2002, 5 - 1, 18), "España", team1);
			Trainer guillermoTrainer = new Trainer("guillermo.trainer", "Guillermo", "Peralta", "Martín", "prueba7", new GregorianCalendar(2003, 2 - 1, 26), "España", null);
			Trainer jonTrainer = new Trainer("jon.trainer", "Jon", "Calleja", "Castaños", "prueba8", new GregorianCalendar(2000, 9 - 1, 23), "España", null);
			
			dbManager.storeTrainer(erikTrainer);
			dbManager.storeTrainer(anderTrainer);
			dbManager.storeTrainer(ikerTrainer);
			dbManager.storeTrainer(hugoTrainer);
			dbManager.storeTrainer(unaiTrainer);
			dbManager.storeTrainer(alexTrainer);
			dbManager.storeTrainer(guillermoTrainer);
			dbManager.storeTrainer(jonTrainer);
			
			logger.info("Almacenados todos los entrenadores.");
			
			Game game1 = new Game("Bilbao Basket 2", "Referee1", 4, 2, 50, 49, 6, 7);
			Game game2 = new Game("Bilbao Basket 2", "Referee1", 4, 3, 55, 40, 4, 8);
			Game game3 = new Game("Trapaga Basket", "Referee2", 2, 3, 51, 60, 7, 9);
			Game game4 = new Game("Bilbao Basket 2", "Referee1", 4, 3, 70, 40, 2, 1);
			Game game5 = new Game("Bilbao Basket 2", "Referee1", 4, 3, 71, 41, 2, 3);
			Game game6 = new Game("Bilbao Basket", "Referee3", 1, 3, 72, 42, 2, 0);
			Game game7 = new Game("Bilbao Basket", "Referee3", 1, 2, 73, 43, 3, 1);
			Game game8 = new Game("Barakaldo Basket", "Referee4", 3, 1, 80, 76, 5, 6);
			Game game9 = new Game("Barakaldo Basket", "Referee4", 3, 2, 65, 70, 4, 2);
			Game game10 = new Game("Bilbao Basket", "Referee3", 1, 4, 90, 70, 4, 7);
			Game game11 = new Game("Barakaldo Basket 2", "Referee5", 2, 1, 50, 71, 2, 4);
			Game game12 = new Game("Barakaldo Basket 2", "Referee5", 3, 2, 93, 85, 3, 5);
			Game game13 = new Game("Barakaldo Basket 2", "Referee5", 4, 3, 87, 86, 5, 1);
			Game game14 = new Game("Trapaga Basket 2", "Referee6", 1, 3, 40, 63, 3, 2);
			Game game15 = new Game("Trapaga Basket 2", "Referee6", 2, 4, 55, 51, 1, 4);
			
			dbManager.storeGame(game1);
			dbManager.storeGame(game2);
			dbManager.storeGame(game3);
			dbManager.storeGame(game4);
			dbManager.storeGame(game5);
			dbManager.storeGame(game6);
			dbManager.storeGame(game7);
			dbManager.storeGame(game8);
			dbManager.storeGame(game9);
			dbManager.storeGame(game10);
			dbManager.storeGame(game11);
			dbManager.storeGame(game12);
			dbManager.storeGame(game13);
			dbManager.storeGame(game14);
			dbManager.storeGame(game15);
			
			logger.info("Almacenados todos los partidos.");
			
			dbManager.disconnect();
		} catch (UserRepositoryException e) {
			logger.warning(ConfigReader.dbConnectError);
		}
	}
}
