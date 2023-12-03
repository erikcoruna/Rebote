package io;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.nio.file.Paths;
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

import domain.IFileManager;
import domain.Player;
import domain.Trainer;
import domain.UserRepositoryException;

public class CSVFileManager implements IFileManager {
	
	public String[] playerHeaders = getAttributeNames(Player.class);
	public String[] trainerHeaders = getAttributeNames(Trainer.class);
	public static void main(String[] args) {
		CSVFileManager fileManager = new CSVFileManager();
		List<Trainer> trainersList = new ArrayList<>();
		String[] teams = {"Test", "Test2"};
		trainersList.add(new Trainer(1, "Erik", "Coruña", "Rodríguez", "prueba", new GregorianCalendar(2004, 22, 4), "España", teams));
		trainersList.add(new Trainer(2, "Ander", "Herrero", "Pascual", "prueba", new GregorianCalendar(2004, 24, 7), "Francia", teams));
		try {
			fileManager.exportTrainersToFile(trainersList, Paths.get("src/io/trainers.csv"));
			fileManager.importTrainersFromFile(Paths.get("src/io/trainers.csv"));
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
	
	@Override
	public List<Player> importPlayersFromFile(Path path) throws UserRepositoryException {
		try (ICsvMapReader csvReader = new CsvMapReader(new FileReader("src/io/players.csv"), CsvPreference.STANDARD_PREFERENCE)) {
			Map<String, String> row;
			
			csvReader.getHeader(false);
			while((row = csvReader.read(playerHeaders)) != null) {
				System.out.println(String.format("id: %s", row.get("id")));
			}
			return null;
		} catch (FileNotFoundException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		} catch (IOException e) {
			throw new UserRepositoryException("Error leyendo archivo CSV.", e);
		}
	}

	public List<Trainer> importTrainersFromFile(Path path) throws UserRepositoryException {
		try (ICsvMapReader csvReader = new CsvMapReader(new FileReader("src/io/trainers.csv"), CsvPreference.STANDARD_PREFERENCE)) {
			Map<String, String> row;
			
			csvReader.getHeader(false);
			while((row = csvReader.read(trainerHeaders)) != null) {
				System.out.println(String.format("id: %s", row.get("id")));
			}
			return null;
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
				row.put(playerHeaders[1], player.getName());
				row.put(playerHeaders[2], player.getFirstSurname());
				row.put(playerHeaders[3], player.getSecondSurname());
				row.put(playerHeaders[4], player.getPassword());
				row.put(playerHeaders[5], String.valueOf(player.getBirthDate()));
				row.put(playerHeaders[6], player.getCountry());
				row.put(playerHeaders[7], player.getCategory());
				row.put(playerHeaders[8], String.valueOf(player.getHeight()));
				row.put(playerHeaders[9], String.valueOf(player.getWeight()));
				row.put(playerHeaders[10], player.getTeam());
				
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
				row.put(trainerHeaders[1], trainer.getName());
				row.put(trainerHeaders[2], trainer.getFirstSurname());
				row.put(trainerHeaders[3], trainer.getSecondSurname());
				row.put(trainerHeaders[4], trainer.getPassword());
				row.put(trainerHeaders[5], String.valueOf(trainer.getBirthDate()));
				row.put(trainerHeaders[6], trainer.getCountry());
				row.put(trainerHeaders[7], trainer.getTeams().toString());
				
				csvWriter.write(row, trainerHeaders);
			}
		} catch (IOException e) {
			throw new UserRepositoryException("Error escribiendo fichero CSV.", e);
		}
	}
}
