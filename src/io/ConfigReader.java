package io;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigReader {

	public static String projectName;
	public static boolean projectTestData;
	public static String dbConnectError;
	public static String dbDisconnectError;
	public static String dbCreateTableError;
	public static String dbDropTableError;
	public static String dbStoreError;
	public static String dbGetError;
	public static String dbDeleteError;
	public static String dbUpdateError;
	public static String csvReadError;
	public static String csvWriteError;
	
    public static void loadConfiguration() {
        Properties prop = new Properties();
        InputStream input = null;

        try {
            input = new FileInputStream("conf/config.properties");

            prop.load(input);

            projectName = prop.getProperty("name");
            projectTestData = Boolean.valueOf(prop.getProperty("testData"));
            dbConnectError = prop.getProperty("dbConnectError");
            dbDisconnectError = prop.getProperty("dbDisconnectError");
            dbCreateTableError = prop.getProperty("dbCreateTableError");
            dbDropTableError = prop.getProperty("dbDropTableError");
            dbStoreError = prop.getProperty("dbStoreError");
            dbGetError = prop.getProperty("dbGetError");
            dbDeleteError = prop.getProperty("dbDeleteError");
            dbUpdateError = prop.getProperty("dbUpdateError");
            csvReadError = prop.getProperty("csvReadError");
            csvWriteError = prop.getProperty("csvWriteError");
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
