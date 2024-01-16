package io;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Logger;

import gui.WindowStart;

public class ConfigReader {

	static Logger logger = Logger.getLogger(WindowStart.class.getName());

	public static String projectName;
	public static boolean projectTestData;
	public static boolean projectCsvEdit;
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
            input = new FileInputStream(Paths.get("conf/config.properties").toString());

            prop.load(input);

            projectName = prop.getProperty("name");
            logger.info("Nombre del proyecto: " + projectName);
            projectTestData = Boolean.valueOf(prop.getProperty("testData"));
            logger.info("Datos de prueba: " + projectTestData);
            projectCsvEdit = Boolean.valueOf(prop.getProperty("csvEdit"));
            logger.info("Editar datos desde fichero CSV: " + projectCsvEdit);
            dbConnectError = prop.getProperty("dbConnectError");
            logger.info("Mensaje de error conectando a la base de datos: " + dbConnectError);
            dbDisconnectError = prop.getProperty("dbDisconnectError");
            logger.info("Mensaje de error desconectando de la base de datos: " + dbDisconnectError);
            dbCreateTableError = prop.getProperty("dbCreateTableError");
            logger.info("Mensaje de error creando tabla en la base de datos: " + dbCreateTableError);
            dbDropTableError = prop.getProperty("dbDropTableError");
            logger.info("Mensaje de error eliminando tabla de la base de datos: " + dbDropTableError);
            dbStoreError = prop.getProperty("dbStoreError");
            logger.info("Mensaje de error guardando en la base de datos: " + dbStoreError);
            dbGetError = prop.getProperty("dbGetError");
            logger.info("Mensaje de error obteniendo de la base de datos: " + dbGetError);
            dbDeleteError = prop.getProperty("dbDeleteError");
            logger.info("Mensaje de error eliminando de la base de datos: " + dbDeleteError);
            dbUpdateError = prop.getProperty("dbUpdateError");
            logger.info("Mensaje de error actualizando en la base de datos: " + dbUpdateError);
            csvReadError = prop.getProperty("csvReadError");
            logger.info("Mensaje de error leyendo de fichero CSV: " + csvReadError);
            csvWriteError = prop.getProperty("csvWriteError");
            logger.info("Mensaje de error escribiendo en fichero CSV: " + csvWriteError);
        } catch (IOException ex) {
        	System.out.println(ex);
            logger.warning("Error leyendo fichero de configuración.");
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
