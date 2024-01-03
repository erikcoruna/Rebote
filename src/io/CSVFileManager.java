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

import org.supercsv.io.CsvMapReader;
import org.supercsv.io.CsvMapWriter;
import org.supercsv.io.ICsvMapReader;
import org.supercsv.io.ICsvMapWriter;
import org.supercsv.prefs.CsvPreference;

import db.SQLiteDBManager;
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
		try {
			fileManager.exportTrainersToFile(Paths.get("src/io/trainers.csv"));
			fileManager.importTrainersFromFile(Paths.get("src/io/trainers.csv"));
			fileManager.exportPlayersToFile(Paths.get("src/io/players.csv"));
			fileManager.importPlayersFromFile(Paths.get("src/io/players.csv"));
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
	public void importPlayersFromFile(Path path) throws UserRepositoryException {
		try (ICsvMapReader csvReader = new CsvMapReader(new FileReader(path.toFile()), CsvPreference.STANDARD_PREFERENCE)) {
			Map<String, String> row;
			
			SQLiteDBManager dbManager = new SQLiteDBManager();
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
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
				try {
					player.setTeam(dbManager.getTeam(Integer.parseInt(row.get("team"))));
				} catch (NumberFormatException e) {
					player.setTeam(null);
				}
				player.setHeight(Integer.parseInt(row.get("height")));
				player.setWeight(Float.parseFloat(row.get("weight")));
				try {
					dbManager.updatePlayer(player);
				} catch (UserRepositoryException e) {
					player.setId(-1);
					dbManager.storePlayer(player);
				}
			}
		} catch (FileNotFoundException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		} catch (IOException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		}
	}

	@Override
	public void importTrainersFromFile(Path path) throws UserRepositoryException {
		try (ICsvMapReader csvReader = new CsvMapReader(new FileReader(path.toFile()), CsvPreference.STANDARD_PREFERENCE)) {
			Map<String, String> row;
			
			SQLiteDBManager dbManager = new SQLiteDBManager();
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
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
				try {
					trainer.setTeam(dbManager.getTeam(Integer.parseInt(row.get("team"))));
				} catch (NumberFormatException e) {
					trainer.setTeam(null);
				}
				
				try {
					dbManager.updateTrainer(trainer);
				} catch (UserRepositoryException e) {
					trainer.setId(-1);
					dbManager.storeTrainer(trainer);
				}
			}
		} catch (FileNotFoundException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		} catch (IOException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		}
	}

	@Override
	public void exportPlayersToFile(Path path) throws UserRepositoryException {
		try (ICsvMapWriter csvWriter = new CsvMapWriter(new FileWriter(path.toFile()), CsvPreference.STANDARD_PREFERENCE)) {
			csvWriter.writeHeader(playerHeaders);
			
			SQLiteDBManager dbManager = new SQLiteDBManager();
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			for (Player player : dbManager.getAllPlayers()) {
				Map<String, String> row = new HashMap<>();
				row.put(playerHeaders[0], String.valueOf(player.getId()));
				row.put(playerHeaders[1], player.getUsername());
				row.put(playerHeaders[2], player.getName());
				row.put(playerHeaders[3], player.getFirstSurname());
				row.put(playerHeaders[4], player.getSecondSurname());
				row.put(playerHeaders[5], player.getPassword());
				row.put(playerHeaders[6], calendarToString(player.getBirthDate()));
				row.put(playerHeaders[7], player.getCountry());
				try {
					row.put(playerHeaders[8], String.valueOf(player.getTeam().getId()));
				} catch (NullPointerException e) {
					row.put(playerHeaders[8], null);
				}
				row.put(playerHeaders[9], String.valueOf(player.getHeight()));
				row.put(playerHeaders[10], String.valueOf(player.getWeight()));
					
				try {
					csvWriter.write(row, playerHeaders);
				} catch (IOException e) {
					System.out.println("Error escribiendo fichero CSV.");
					e.printStackTrace();
				}
			};
		} catch (IOException e) {
			throw new UserRepositoryException("Error escribiendo fichero CSV.", e);
		}
	}

	@Override
	public void exportTrainersToFile(Path path) throws UserRepositoryException {
		try (ICsvMapWriter csvWriter = new CsvMapWriter(new FileWriter(path.toFile()), CsvPreference.STANDARD_PREFERENCE)) {
			csvWriter.writeHeader(trainerHeaders);
			
			SQLiteDBManager dbManager = new SQLiteDBManager();
			System.out.println("Conectando con la base de datos...");
			dbManager.connect("src/db/rebote.db");
			
			for (Trainer trainer : dbManager.getAllTrainers()) {
				Map<String, String> row = new HashMap<>();
				row.put(trainerHeaders[0], String.valueOf(trainer.getId()));
				row.put(trainerHeaders[1], trainer.getUsername());
				row.put(trainerHeaders[2], trainer.getName());
				row.put(trainerHeaders[3], trainer.getFirstSurname());
				row.put(trainerHeaders[4], trainer.getSecondSurname());
				row.put(trainerHeaders[5], trainer.getPassword());
				row.put(trainerHeaders[6], calendarToString(trainer.getBirthDate()));
				row.put(trainerHeaders[7], trainer.getCountry());
				try {
					row.put(trainerHeaders[8], String.valueOf(trainer.getTeam().getId()));
				} catch (NullPointerException e) {
					row.put(trainerHeaders[8], null);
				}
				
				try {
					csvWriter.write(row, trainerHeaders);
				} catch (IOException e) {
					System.out.println("Error escribiendo fichero CSV.");
					e.printStackTrace();
				}
			};
		} catch (IOException e) {
			throw new UserRepositoryException("Error escribiendo fichero CSV.", e);
		}
	}
}
