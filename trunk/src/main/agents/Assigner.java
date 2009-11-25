package main.agents;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import misc.Pair;
import static misc.DebugFunctions.*;

//TODO: Is this class still needed ?
/**
 * Class Assigner.
 * TODO: Finish this documentation
 */
public class Assigner extends Agent {
	private static final long serialVersionUID = 1L;
	public final static String OK = "ok";
	public final static String NO_SOLUTION = "no-solution";
	private ConnectionFactory factory;
	private Connection connection;
	private Queue<String> queue;
	private Queue<Pair<String, String>> assigned;
	private Pair<String, String> lastSent;

	/** Generate a new connection and the lists necessary for this agent. */
	public Assigner() {
		super();
		factory = new ConnectionFactory();
		connection = factory.getConnection();
		queue = new LinkedList<String>();
		assigned = new LinkedList<Pair<String, String>>();
	}

	/** Fill the queue */
	private void fillQueue(String q) {
		for (String a : q.split(",")) {
			queue.offer(a);
		}
	}
	
	// TODO: Refactor this. Use the queue iterator ?
	/** Returns the next value in the queue*/
	private String next() {
		return assignNewProf(queue.poll());
	}
	
	private String filterMsg(String msg) {
		if (msg == OK) {
			return next();
		} else if (msg.split(",").length == 2) {
			String[] a = msg.split(",");
			assigned.offer(new Pair<String, String>(a[0], a[1]));
			return next();
		} else if (msg == NO_SOLUTION) {
			return assignNewProf(lastSent);
		} else {
			fillQueue(msg);
			return next();
		}
	}

	private String assignNewProf(String group) {
		String prof = "";
		// algoritmo de seleccion

		lastSent.setLeft(group);
		lastSent.setRight(prof);
		assigned.offer(lastSent);
		return group + "," + prof;
	}

	private String assignNewProf(Pair<String, String> par) {
		String newProf = "";
		// obtener un nuevo prof que no sea el que ya se envio previamente
		lastSent.copy(par);
		return par.getLeft() + "," + par.getRight();
	}

	@Override
	protected void setup() {
		addBehaviour(new CyclicBehaviour(this) {
			private static final long serialVersionUID = 1L;

			public void action() {
				ACLMessage msg = receive(MessageTemplate
						.MatchPerformative(ACLMessage.INFORM));
				if (msg != null) {
					String content = filterMsg(msg.getContent());
					try {
						if (!connection.isValid(100)) {
							connection = factory.getConnection();
						}
					} catch (SQLException e) {
						logError(myAgent, "SQL Error");
						e.printStackTrace();
					}
					if (content != null) {
						ACLMessage message = new ACLMessage(ACLMessage.INFORM);
						message.setContent(content);
						// message.addReceiver(r);
						myAgent.send(message);
						System.out.println(myAgent.getLocalName()
								+ " SENT MESSAGE WITH ASSIGNMENT");
					}
				} else {
					block();
				}
			}
		});
	}
}
