package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.supercsv.io.CsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import domain.IFileManager;
import domain.League;
import domain.Player;
import domain.Team;
import domain.Trainer;
import domain.UserRepositoryException;

public class CSVFileManager implements IFileManager {
	
	public String[] playerHeaders = getAttributeNames(Player.class);
	public String[] trainerHeaders = getAttributeNames(Trainer.class);
	private SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd");
	public static void main(String[] args) {
		CSVFileManager fileManager = new CSVFileManager();
		List<Trainer> trainersList = new ArrayList<>();
		Team team1 = new Team(1, "team1", "Bilbao", "Bilbao basket", "Este es el equipo de baloncesto de Bilbao.", League.A);
		Team team2 = new Team(2, "team2", "Barakaldo", "Barakaldo basket", "Este es el equipo de baloncesto de Barakaldo.", League.B);
		trainersList.add(new Trainer(1, "erik.coruna", "Erik", "Coruña", "Rodríguez", "prueba", new GregorianCalendar(2004, 4 - 1, 22), "España", team1));
		trainersList.add(new Trainer(2, "ander.herrero", "Ander", "Herrero", "Pascual", "prueba", new GregorianCalendar(2004, 7 - 1, 26), "Francia", team2));
		
		List<Player> playersList = new ArrayList<>();
		playersList.add(new Player(1, "erik.coruna", "Erik", "Coruña", "Rodríguez", "prueba", new GregorianCalendar(2004, 4 - 1, 22), "España", team1, 170, 60.3f));
		playersList.add(new Player(2, "ander.herrero", "Ander", "Herrero", "Pascual", "prueba", new GregorianCalendar(2004, 7 - 1, 26), "Francia", team2, 196, 75.4f));
		try {
			fileManager.exportTrainersToFile(trainersList, Paths.get("src/io/trainers.csv"));
			System.out.println(fileManager.importTrainersFromFile(Paths.get("src/io/trainers.csv")));
			fileManager.exportPlayersToFile(playersList, Paths.get("src/io/players.csv"));
			System.out.println(fileManager.importPlayersFromFile(Paths.get("src/io/players.csv")));
		} catch (UserRepositoryException e) {
			e.printStackTrace();
		}
	}
	
	private static String[] getAttributeNames(Class<?> clazz) {
        List<String> attributeNames = new ArrayList<>();
        if (clazz.getSuperclass() != null) {
        	Field[] superclassFields = clazz.getSuperclass().getDeclaredFields();
        	for (Field field : superclassFields) {
        		attributeNames.add(field.getName());
        	}
        }
        
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            attributeNames.add(field.getName());
        }
        
        return attributeNames.toArray(new String[0]);
    }
	
	private GregorianCalendar stringToCalendar(String str) {
		GregorianCalendar calendar = new GregorianCalendar();
		try {
			calendar.setTime(isoDateFormat.parse(str));
		} catch (ParseException e) {
			calendar.setTime(null);
		}
		return calendar;
	}
	
	private String calendarToString(GregorianCalendar calendar) {
		return isoDateFormat.format(calendar.getTime());
	}
	
	@Override
	public List<Player> importPlayersFromFile(Path path) throws UserRepositoryException {
		try (ICsvMapReader csvReader = new CsvMapReader(new FileReader("src/io/players.csv"), CsvPreference.STANDARD_PREFERENCE)) {
			Map<String, String> row;
			List<Player> playerList = new ArrayList<>();
			
			csvReader.getHeader(false);
			while ((row = csvReader.read(playerHeaders)) != null) {
				Player player = new Player();
				player.setId(Integer.parseInt(row.get("id")));
				player.setUsername(row.get("username"));
				player.setName(row.get("name"));
				player.setFirstSurname(row.get("firstSurname"));
				player.setSecondSurname(row.get("secondSurname"));
				player.setPassword(row.get("password"));
				player.setBirthDate(stringToCalendar(row.get("birthDate")));
				player.setCountry(row.get("country"));
				player.setHeight(Integer.parseInt(row.get("height")));
				player.setWeight(Float.parseFloat(row.get("weight")));
				String teamStr = row.get("team").replaceAll("[\\[\\]]", "");
				String[] teamSplit = teamStr.split(", ");
				Team team = new Team();
				team.setName(teamSplit[1]);
				team.setCity(teamSplit[2]);
				team.setStadium(teamSplit[3]);
				team.setDescription(teamSplit[4]);
				team.setLeague(League.valueOf(teamSplit[5]));
				player.setTeam(team);
				playerList.add(player);
			}
			return playerList;
		} catch (FileNotFoundException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		} catch (IOException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		}
	}

	public List<Trainer> importTrainersFromFile(Path path) throws UserRepositoryException {
		try (ICsvMapReader csvReader = new CsvMapReader(new FileReader("src/io/trainers.csv"), CsvPreference.STANDARD_PREFERENCE)) {
			Map<String, String> row;
			List<Trainer> trainersList = new ArrayList<>();
			
			csvReader.getHeader(false);
			while((row = csvReader.read(trainerHeaders)) != null) {
				Trainer trainer = new Trainer();
				trainer.setId(Integer.parseInt(row.get("id")));
				trainer.setUsername(row.get("username"));
				trainer.setName(row.get("name"));
				trainer.setFirstSurname(row.get("firstSurname"));
				trainer.setSecondSurname(row.get("secondSurname"));
				trainer.setPassword(row.get("password"));
				trainer.setBirthDate(stringToCalendar(row.get("birthDate")));
				trainer.setCountry(row.get("country"));
				String teamStr = row.get("team").replaceAll("[\\[\\]]", "");
				String[] teamSplit = teamStr.split(", ");
				Team team = new Team();
				team.setName(teamSplit[1]);
				team.setCity(teamSplit[2]);
				team.setStadium(teamSplit[3]);
				team.setDescription(teamSplit[4]);
				team.setLeague(League.valueOf(teamSplit[5]));
				trainer.setTeam(team);
				trainersList.add(trainer);
			}
			return trainersList;
		} catch (FileNotFoundException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		} catch (IOException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		}
	}

	@Override
	public void exportPlayersToFile(List<Player> players, Path path) throws UserRepositoryException {
		try (ICsvMapWriter csvWriter = new CsvMapWriter(new FileWriter("src/io/players.csv"), CsvPreference.STANDARD_PREFERENCE)) {
			csvWriter.writeHeader(playerHeaders);
			
			for (Player player : players) {
				Map<String, String> row = new HashMap<>();
				row.put(playerHeaders[0], String.valueOf(player.getId()));
				row.put(playerHeaders[1], player.getUsername());
				row.put(playerHeaders[2], player.getName());
				row.put(playerHeaders[3], player.getFirstSurname());
				row.put(playerHeaders[4], player.getSecondSurname());
				row.put(playerHeaders[5], player.getPassword());
				row.put(playerHeaders[6], calendarToString(player.getBirthDate()));
				row.put(playerHeaders[7], player.getCountry());
				row.put(playerHeaders[8], String.valueOf(player.getHeight()));
				row.put(playerHeaders[9], String.valueOf(player.getWeight()));
				row.put(playerHeaders[10], player.getTeam().toString());
				
				csvWriter.write(row, playerHeaders);
			}
		} catch (IOException e) {
			throw new UserRepositoryException("Error escribiendo fichero CSV.", e);
		}
	}

	@Override
	public void exportTrainersToFile(List<Trainer> trainers, Path path) throws UserRepositoryException {
		try (ICsvMapWriter csvWriter = new CsvMapWriter(new FileWriter("src/io/trainers.csv"), CsvPreference.STANDARD_PREFERENCE)) {
			csvWriter.writeHeader(trainerHeaders);
			
			for (Trainer trainer : trainers) {
				Map<String, String> row = new HashMap<>();
				row.put(trainerHeaders[0], String.valueOf(trainer.getId()));
				row.put(trainerHeaders[1], trainer.getUsername());
				row.put(trainerHeaders[2], trainer.getName());
				row.put(trainerHeaders[3], trainer.getFirstSurname());
				row.put(trainerHeaders[4], trainer.getSecondSurname());
				row.put(trainerHeaders[5], trainer.getPassword());
				row.put(trainerHeaders[6], calendarToString(trainer.getBirthDate()));
				row.put(trainerHeaders[7], trainer.getCountry());
				row.put(trainerHeaders[8], trainer.getTeam().toString());
				
				csvWriter.write(row, trainerHeaders);
			}
		} catch (IOException e) {
			throw new UserRepositoryException("Error escribiendo fichero CSV.", e);
		}
	}
}
