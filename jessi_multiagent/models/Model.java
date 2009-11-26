package models;

import java.sql.Connection;
import java.sql.SQLException;

import agents.database.ConnectionFactory;

public class Model {

	private static final ConnectionFactory cf = new ConnectionFactory();
	private static Connection connection = cf.getConnection();
	private static final int TIMEOUT_WAIT = 1;

	public static Connection getDBConnection() throws SQLException {
		if (!connection.isValid(TIMEOUT_WAIT)) {
			connection = cf.getConnection();
		}
		return connection;
	}
}
