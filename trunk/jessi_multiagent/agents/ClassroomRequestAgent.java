package agents;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import misc.Pair;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import agents.behaviours.SearchServiceBehaviour;
import agents.database.ConnectionFactory;
import static misc.DebugFunctions.*;

// TODO: Delete this comment.
// This class should send to the contract net only the
// group id and the professor id, it should then wait for an answer to send the next
// request 
public class ClassroomRequestAgent extends Agent {

	// Time between searches for the contract net agents.
	private static final long SEARCH_DELTA = 15000L;
	// String when the response assigned is "OK"
	private static final String OK = "ok";
	private int GroupsCapacity = 25;
	// Creates a new Connection Factory....for connections
	ConnectionFactory factory = new ConnectionFactory();
	// The list of the agents that provide the contract network service
	// NOTE: This is just one agent, but may be extended in the future
	private ArrayList<AID> CNAgents = new ArrayList<AID>();
	// List of the group IDs we are going to process
	private Queue<String> groups = new LinkedList<String>();
	// List of the possible professors for each processing group, in priority
	// order
	private Queue<String> professors = new LinkedList<String>();
	// The last processed group
	private String lastGroupSent = "";
	// Variable to hold the refused pair, to assign the next professor
	private Pair<String, String> refusePair = null;
	// List of Failed Groups, the groups without solution
	private Queue<String> failedGroups = new LinkedList<String>();
	// Regex to parse the responses of the type (group_id, professor_id)
	private final String REGEX = "\\((\\d+),(\\d+)\\)";

	@Override
	protected void setup() {
		// Search for the agent(s) that provides the
		// contract-net-request-receiver
		// service, so we can send it the classroom requests
		addBehaviour(new SearchServiceBehaviour(this, SEARCH_DELTA, CNAgents,
				ClassroomContractNetInitiatorAgent.SERVICE_TYPE));
		// TODO: Delete this comment
		// READ the database, get the groups, and send them their id along
		// with the professor id to the ClassroomContracNetworkInitiatorAgent

		// TODO: Delete this code, and comment
		// Lets assume we have already red the database and we have obtained 10
		// different groups, so we send each group one by one to request the
		// classroom
		fillGroups();
		obtainGroups();
		// This behavior should just be added after we got a Collection
		// with all the group requests ready
		addBehaviour(new ClassroomRequestBehaviour());
	}

	private class ClassroomRequestBehaviour extends Behaviour {

		// Variable indicating the actual state
		private int state = 0;
		// Message template, this constructed according to what this
		// agents needs to hear
		private MessageTemplate mt;
		// The conversation id
		private static final String CONVERSATION_ID = "classroom-request";
		// The different agent states
		private static final int WAIT_FOR_AGENTS = 0;
		private static final int CREATE_REQUEST = 1;
		private static final int RECEIVE_RESPONSE = 2;
		private static final int FINISHED = 3;

		@Override
		public void action() {
			switch (state) {
			// If there aren't any agents to give the message to, do
			// nothing, else change to the state to prepare a new request
			case WAIT_FOR_AGENTS:
				if (CNAgents.size() > 0) {
					state = CREATE_REQUEST;
				}
				break;
			// Create a request and send it
			case CREATE_REQUEST:
				// Create the message
				ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
				// Add the receiver for the message
				msg.addReceiver(CNAgents.get(0));
				// Set the content for the message, a tuple with the
				// group id and the professor id
				if (refusePair == null) {
					lastGroupSent=groups.peek();
					msg.setContent(assignNewProfessor(groups.poll()).toString());
				} else {
					msg.setContent(refusePair.toString());
					refusePair = null;
				}
				// Set a conversation id
				msg.setConversationId(CONVERSATION_ID);
				// Create a reply-with (this needs to be unique)
				msg.setReplyWith("classroom-request"
						+ System.currentTimeMillis());
				// Send the message
				
				myAgent.send(msg);
				log(myAgent, "Looking for classroom with " + msg.getContent());
				// Prepare a message hearing template for the response
				mt = MessageTemplate.and(MessageTemplate
						.MatchConversationId(CONVERSATION_ID), MessageTemplate
						.MatchInReplyTo(msg.getReplyWith()));
				state = RECEIVE_RESPONSE;
				break;
			case RECEIVE_RESPONSE:
				// Receive the response
				ACLMessage response = myAgent.receive(mt);
				// If there was a reply check the type
				if (response != null) {
					// Get the content and the performative of the
					// response and act accordingly
					String content = response.getContent().trim();
					int perf = response.getPerformative();
					// A successful classroom assignment
					if (perf == ACLMessage.INFORM) {
						// Regex to check if content has a tuple response
						Pattern p = Pattern.compile(REGEX);
						Matcher m = p.matcher(content);
						// If the content of the message is "ok" then the
						// classroom was assigned successfully.
						if (content.equals(OK)) {
							log(myAgent, "Classroom successfully assigned");
						}
						// The content is a (group_id, professor_id)
						// so add it back to the queue.
						else if (m.find()) {
							groups.offer(content.substring(
									content.indexOf('(') + 1, content
											.indexOf(',') - 1));
							log(myAgent, "Classroom assigned, but returned "
									+ "collision. " + content);
						} else {
							String message = "Illegal state";
							logError(myAgent, message);
							assert false : message;
						}
					}
					// Refusal
					else if (perf == ACLMessage.REFUSE) {
						log(myAgent, "Classroom assignment refused.");
						if (!professors.isEmpty()) {
							refusePair = new Pair<String, String>(
									lastGroupSent, professors.poll());
						} else {
							failedGroups.offer(lastGroupSent);
						}
						// TODO: Here you should switch for a different
						// professor
						// to see if the course can have an assignment
						// TODO: Don't forget to remember which professors
						// you've
						// tested this group with, so if you're out of
						// alternatives
						// you should just give up
					} else {
						String error = "Performative has an invalid value";
						log(myAgent, error);
						assert false : error;
					}

					state = (!groups.isEmpty()) ? CREATE_REQUEST : FINISHED;
				}
				// If there wasn't any reply, block the behavior until
				// a message is received
				else {
					block();
				}
				break;
			case FINISHED:
				log(myAgent,"Classroom request agent done");
				break;
			}
		}

		@Override
		public boolean done() {
			return groups.isEmpty() || state == FINISHED;
		}
	}

	private void fillGroups() {
		// SQL to obtain groups
		String sql = "SELECT Materia_Id, Materia_Poblacion from Materia;";
		String materia = "";
		// Generate connection to the database
		Connection conexion = factory.getConnection();
		ResultSet result;
		try {
			// Obtains ResultSet tables from the execution of the query
			result = conexion.prepareStatement(sql).executeQuery();
			// Number of groups
			double nuGroups = 0;
			// Iterates over the table of results
			while (result.next()) {
				// Stores the Materia_Id
				materia = result.getString("Materia_Id");
				// Divides the population and the capacity and results in the
				// number of groups
				nuGroups = Math.ceil(result.getDouble(2) / GroupsCapacity);
				// Stores the groups in the database
				for (int i = 0; i < nuGroups; i++) {
					conexion.prepareStatement(
							"INSERT into grupo(Materia_Id, Grupo_Capacidad ) values (\""
									+ materia + "\",\""
									+ Math.ceil(result.getDouble(2) / nuGroups)
									+ "\");").executeUpdate();
				}
			}
			// conexion.commit();
		} catch (SQLException e) {
			logError(this, "SQL Error when filling groups");
			e.printStackTrace();
		}
		factory.closeConnection(conexion);
	}

	private void obtainGroups() {
		// SQL to obtain groups
		String sql = "SELECT Grupo_Id FROM grupo;";
		// Generate connection to the database
		Connection conexion = factory.getConnection();
		ResultSet result;
		try {
			// Obtains ResultSet tables from the execution of the query
			result = conexion.prepareStatement(sql).executeQuery();
			// Iterates over the table of results
			while (result.next()) {
				// Stores the resultant groups in a queue
				groups.offer(result.getString(1));
			}
		} catch (SQLException e) {
			logError(this, "SQL Error when filling groups");
			e.printStackTrace();
		}
		factory.closeConnection(conexion);
	}

	private Pair<String, String> assignNewProfessor(String group) {
		professors.clear();
		// SQL to obtain groups
		String sql = "SELECT Profesor_Id FROM profesor_materia WHERE Materia_Id=(SELECT Materia_Id FROM grupo WHERE Grupo_Id ="
				+ group + ") ORDER BY Prioridad;";
		// Generate connection to the database
		Connection conexion = factory.getConnection();
		ResultSet result;
		try {
			// Obtains ResultSet tables from the execution of the query
			result = conexion.prepareStatement(sql).executeQuery();
			// Iterates over the table of results
			while (result.next()) {
				// Stores the resultant professors in a queue
				professors.offer(result.getString(1));
			}
		} catch (SQLException e) {
			log(this, "SQL Error when filling groups.");
			e.printStackTrace();
		}
		factory.closeConnection(conexion);
		return new Pair<String, String>(group, professors.poll());
	}
}