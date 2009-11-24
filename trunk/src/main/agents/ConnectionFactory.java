package agents;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
	
	String confFile = "conf/conf.properties";
	Properties properties;
	
	public ConnectionFactory(){
		properties = new Properties();
		loadProperties();
	}
	
	public Connection getConnection(){
		Connection connection = null;

	    try {
	        String driverName = properties.getProperty("db.1.driver"); // MySQL MM JDBC driver
	        System.out.println("driver: " + driverName);
	        Class.forName(driverName);
	    
	        // Create a connection to the database

	        String url = properties.getProperty("db.1.url"); // a JDBC url
	        String username = properties.getProperty("db.1.username");
	        String password = properties.getProperty("db.1.password");
	        connection = DriverManager.getConnection(url, username, password);
	        
	        return connection;
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	public Connection getConnection(String nombreDB){
		Connection connection = null;

	    try {
	        String driverName = properties.getProperty("db.1.driver"); // MySQL MM JDBC driver
	        System.out.println("driver: " + driverName);
	        Class.forName(driverName);
	    
	        // Create a connection to the database

	        String url = nombreDB;
	        String username = properties.getProperty("db.1.username");
	        String password = properties.getProperty("db.1.password");
	        connection = DriverManager.getConnection(url, username, password);
	        
	        return connection;
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return null;
	}
	
	private void loadProperties(){
		try {
			properties.load(new FileInputStream(confFile));
			System.out.println("Se cargo bien");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
