package main.agents;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import static misc.DebugFunctions.*;

/** Agent that connects to the database server and handles requests. */
public class DatabaseAgent extends Agent {
	// Connection factory
	private ConnectionFactory factory;
	// Connection to the database
	private Connection connection;
	// Database name
	private String dbName;

	/**
	 * Initialize the database name and the connection to the database server.
	 * @param dbName
	 *            Database schema to be searched. "itesm" schema.
	 */
	public DatabaseAgent(String dbName) {
		super();
		this.dbName = dbName;
		factory = new ConnectionFactory();
		connection = factory.getConnection(dbName);
	}

	/** Add the database behavior to hear for database queries. */
	@Override
	protected void setup() {
		addBehaviour(new DatabaseBehaviour());
	}

	/** Behavior to handle database requests. */
	private class DatabaseBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			ACLMessage msg = receive(MessageTemplate
					.MatchPerformative(ACLMessage.REQUEST));
			if (msg != null) {
				ResultSetMetaData resultMeta;
				ResultSet result;
				String replyMsg = "";
				try {
					if (!connection.isValid(100)) {
						connection = factory.getConnection(dbName);
					}
					result = connection.prepareStatement(msg.getContent())
							.executeQuery();
					resultMeta = result.getMetaData();
					while (result.next()) {
						for (int i = 0; i < resultMeta.getColumnCount(); i++) {
							if (i == resultMeta.getColumnCount() - 1) {
								replyMsg += result.getString(i) + "/n";
							} else {
								replyMsg += result.getString(i) + ",";
							}
						}
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				ACLMessage reply = msg.createReply();
				reply.setContent(replyMsg);
				myAgent.send(reply);
				log(myAgent, "sent answer message with query result.");
			} else {
				block();
			}
		}
	}
}
