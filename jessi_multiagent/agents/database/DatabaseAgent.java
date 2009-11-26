package agents.database;

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
	// SQL Connection timeout maximum waiting time (in seconds);
	private int TIMEOUT_WAIT_TIME = 5;
	// System independent line separator
	private final String NL = System.getProperty("line.separator");

	/**
	 * Initialize the database name and the connection to the database server.
	 * 
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
			MessageTemplate mt = MessageTemplate
					.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = receive(mt);
			if (msg != null) {
				ResultSetMetaData resultMeta;
				ResultSet result;
				String replyMsg = "";
				try {
					if (!connection.isValid(TIMEOUT_WAIT_TIME)) {
						connection = factory.getConnection(dbName);
					}
					result = connection.prepareStatement(msg.getContent())
							.executeQuery();
					resultMeta = result.getMetaData();
					while (result.next()) {
						for (int i = 0; i < resultMeta.getColumnCount(); i++) {
							if (i == resultMeta.getColumnCount() - 1) {
								replyMsg += result.getString(i) + NL;
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
			} else { // No message received, block.
				block();
			}
		}
	}
}
