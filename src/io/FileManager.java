package io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import db.SQLiteDBManager;
import domain.Team;

public class FileManager {
	
	private static SQLiteDBManager dbManager = new SQLiteDBManager();
	
	public static void main(String[] args) throws Exception {
//		writeLeagueToFile(ReboteCollections.createLeague(Paths.get("resources/db/rebote.db")));
		System.out.println(readLeagueFromFile());
	}
	
	public static void writeLeagueToFile(List<List<List<Team>>> league) throws Exception {
		new File("resources/data/league.txt").delete();
		try (PrintWriter writer = new PrintWriter(new FileWriter("resources/data/league.txt", true))) {
			for (List<List<Team>> week : league) {
				writer.println();
				for (List<Team> game : week) {
					writer.println(game.get(0).getId() + ";" + game.get(1).getId());
				}
			}
			writer.println();
		}
	}
	
	public static List<List<List<Team>>> readLeagueFromFile() throws Exception {
		dbManager.connect("resources/db/rebote.db");
		List<List<List<Team>>> league = new ArrayList<>();
		try (BufferedReader reader = new BufferedReader(new FileReader("resources/data/league.txt"))) {
			String line;
			List<List<Team>> week = null;
			
			while ((line = reader.readLine()) != null) {
				if (line.trim().isEmpty()) {
					if (week != null) {
						league.add(week);
						week = null;
					}
				} else {
					if (week == null) {
						week = new ArrayList<>();
					}
					
					String[] teamIds = line.split(";");
					Team team1 = dbManager.getTeam(Integer.valueOf(teamIds[0]));
					Team team2 = dbManager.getTeam(Integer.valueOf(teamIds[1]));
					
					List<Team> game = new ArrayList<>(Arrays.asList(team1, team2));
					week.add(game);
				}
			}
			return league;
		} catch (Exception e) {
			return null;
		}
	}
}
