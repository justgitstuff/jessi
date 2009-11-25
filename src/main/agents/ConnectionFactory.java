package main.agents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import static misc.DebugFunctions.*;

/** Factory that generates SQL connections to the database. */
public class ConnectionFactory {
	
	// Configuration file 
	private String confFile = "conf/conf.properties";
	// This is where the properties in the configuration file are read
	private Properties properties;
	
	/** Load the properties */
	public ConnectionFactory(){
		properties = new Properties();
		loadProperties();
	}
	
	/** Generate a new connection to the database */
	// TODO: Shouldn't this method use the other more general method ? 
	public Connection getConnection(){
		Connection connection = null;

	    try {
	        String driverName = properties.getProperty("db.1.driver"); // MySQL MM JDBC driver
	        log("Driver name for the DB is: " + driverName);
	        Class.forName(driverName);	    
	        // Create a connection to the database
	        String url = properties.getProperty("db.1.url"); // a JDBC url
	        String username = properties.getProperty("db.1.username");
	        String password = properties.getProperty("db.1.password");
	        connection = DriverManager.getConnection(url, username, password);
	        
	        return connection;
	    } catch (ClassNotFoundException e) {
	        logError("Error generating the database connection.");
	    	e.printStackTrace();
	    } catch (SQLException e) {
	    	logError("Error generating the database connection.");
	    	e.printStackTrace();
	    }
		return null;
	}
	
	/** Generate a new connection to the database given the database name */
	public Connection getConnection(String nombreDB){
		Connection connection = null;

	    try {
	        String driverName = properties.getProperty("db.1.driver"); // MySQL MM JDBC driver
	        log("Driver name for the DB is: " + driverName);
	        Class.forName(driverName);
	    
	        // Create a connection to the database

	        String url = nombreDB;
	        String username = properties.getProperty("db.1.username");
	        String password = properties.getProperty("db.1.password");
	        connection = DriverManager.getConnection(url, username, password);
	        
	        return connection;
	    } catch (ClassNotFoundException e) {
	    	logError("Error generating the database connection.");
	    	e.printStackTrace();
	    } catch (SQLException e) {
	    	logError("Error generating the database connection.");
	    	e.printStackTrace();
	    }
		return null;
	}
	
	private void loadProperties(){
		try {
			properties.load(new FileInputStream(confFile));
			System.out.println("Se cargo bien");
		} catch (FileNotFoundException e) {
			logError("Properties file for the DB connection not found.");
			e.printStackTrace();
		} catch (IOException e) {
			logError("Error getting the properties file for the DB connection");
			e.printStackTrace();
		}
	}
	

}
