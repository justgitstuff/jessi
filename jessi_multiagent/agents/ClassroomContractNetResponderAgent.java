package agents;

import models.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;

import agents.behaviours.RegisterServiceBehaviour;
import agents.database.ConnectionFactory;
import static misc.DebugFunctions.*;

/**
 * This example shows how to implement the responder role in a FIPA-contract-net
 * interaction protocol. In this case in particular we use a
 * ContractNetResponder to participate into a negotiation where an initiator
 * needs to assign a task to an agent among a set of candidates.
 * 
 * @author Rogelio Ramirez
 */
public class ClassroomContractNetResponderAgent extends Agent {

	public static final String SERVICE_NAME = "JADE-classroom-search";
	public static final String SERVICE_TYPE = "classroom-search";
	private ConnectionFactory connectionFactory;
	private Connection connection;
	private LinkedList<Profesor> profesores;
	private LinkedList<Lugar> lugares;
	private LinkedList<Asignacion> asignaciones;
	// Regex to parse the responses of the type (group_id, professor_id)
	private final String REGEX = "\\((\\d+),(\\d+)\\)";

	public ClassroomContractNetResponderAgent() throws SQLException {
		super(); // Call the agent constructor
		connectionFactory = new ConnectionFactory();
		connection = connectionFactory.getConnection();
		try {
			profesores = Profesor.createAll();
			lugares = Lugar.createAll();
			Collections.sort(lugares);
			asignaciones = new LinkedList<Asignacion>();
		} catch (SQLException e) {
			logError(this, "Fatal error in the database.");
			e.printStackTrace();
			throw new SQLException(e);
		}
	}

	protected void setup() {
		// Register the service with the DFAgent
		addBehaviour(new RegisterServiceBehaviour(this, SERVICE_NAME, SERVICE_TYPE));
		// Create the message template for the contract net interaction protocol
		log(this, " waiting for CFP...");
		MessageTemplate template = MessageTemplate.and(
						MessageTemplate.MatchProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET),
						MessageTemplate.MatchPerformative(ACLMessage.CFP));
		addBehaviour(new ClassroomContractNetResponderBehaviour(this, template));
	}

	private Profesor findProfessor(int profesorId) {
		// Find the professor with the given id
		Profesor prof = null;
		for (Profesor p : profesores) {
			if (profesorId == p.getId()) {
				prof = p;
				break;
			}
		}
		return prof;
	}

	private int getCapacidad(int grupoId) throws SQLException {

		int capacidad = 0;

		try {
			while (!connection.isValid(1)) {
				connection = connectionFactory.getConnection();
			}

			String query = "select Grupo_Capacidad from grupo where "
					+ "Grupo_Id = " + grupoId + ";";

			PreparedStatement s = connection.prepareStatement(query);
			ResultSet result = s.executeQuery();
			result.first();
			capacidad = result.getInt("Grupo_Capacidad");
			log(this, "Capacity for the group " + grupoId + " is " + capacidad);
		} catch (SQLException e) {
			logError(this, e.getMessage());
			logError(this, "Error getting the capacity of a course");
			throw new SQLException(e);
		}
		return capacidad;
	}

	/**
	 * This class extends the ContractNetResponder behaviour. It receives
	 * messages (Call For Proposals), when one of this messages matches the
	 * template, received in the constructor the prepareResponse method is
	 * executed.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class ClassroomContractNetResponderBehaviour extends
			ContractNetResponder {

		// Proposal value that is selected
		// This should be different every result
		private Asignacion proposal = null;
		// Proposal error
		private static final int PROPOSAL_ERROR = 0;

		public ClassroomContractNetResponderBehaviour(Agent a,
				MessageTemplate mt) {
			super(a, mt);
		}

		/**
		 * When a message arrives that matches the message template passed to
		 * the constructor, the callback method prepareResponse is executed. It
		 * must return the wished response, for instance the PROPOSE reply
		 * message.
		 */
		protected ACLMessage prepareResponse(ACLMessage cfp)
				throws NotUnderstoodException, RefuseException {

			String content = cfp.getContent();
			log(myAgent, "CFP received from " + cfp.getSender().getName()
					+ "Content: " + content);
			// Regex to check if content has a tuple response
			Pattern p = Pattern.compile(REGEX);
			Matcher m = p.matcher(content);
			if (!m.find()) {
				throw new RefuseException("message received wasn't understood");
			}
			int grupoId = Integer.parseInt(m.group(1));
			int profId = Integer.parseInt(m.group(2));
			int proposalValue = getClassroomProposal(grupoId, profId);
			// If there is a proposal, communicate so
			if (proposalValue > PROPOSAL_ERROR) {
				// We provide a proposal
				log(myAgent, "Proposing " + proposalValue);
				ACLMessage propose = cfp.createReply();
				propose.setPerformative(ACLMessage.PROPOSE);
				propose.setContent(String.valueOf(proposalValue));
				return propose;
				// No proposal refuse by default
			} else {
				// We refuse to provide a proposal
				log(myAgent, "No proposals for " + content + ", refusing...");
				throw new RefuseException("evaluation-failed");
			}
		}

		/**
		 * This method is executed when the proposal is accepted. This returns a
		 * message with INFORM performative to tell the proposal was accepted
		 * correctly and FAILURE performative to tell the
		 */
		protected ACLMessage prepareResultNotification(ACLMessage cfp,
				ACLMessage propose, ACLMessage accept) throws FailureException {
			log(myAgent, "Proposal accepted");
			if (setClassroomProposal()) {
				log(myAgent, "Classroom successfully assigned");
				ACLMessage inform = accept.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				return inform;
			} else {
				log("Classroom assignment failed");
				throw new FailureException("unexpected-error");
			}
		}

		/** This method is executed when a proposal is rejected */
		protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose,
				ACLMessage reject) {
			// Output that the proposal was rejected and do nothing about it
			log("Proposal rejected");
		}

		/**
		 * Return a proposal, the greater the number the better the proposal, a
		 * value of 0 indicates no proposal.
		 */
		private int getClassroomProposal(int grupoId, int profesorId) {

			// This is changed if a proposal is found
			int proposal_value = PROPOSAL_ERROR;

			try {
				// Get the professor
				Profesor prof = findProfessor(profesorId);
				// Fail if the professor isn't found
				if (prof == null) {
					logError(myAgent, "Professor with id " + profesorId + " not found.");
					return PROPOSAL_ERROR;
				}

				int capacidad = getCapacidad(grupoId);

				int lugarCount = 0;
				for (Lugar lugar : lugares) {
					LinkedHashSet<Horario> horarioProf = prof.getHorarioDisp();
					LinkedHashSet<Horario> horarioLugar = lugar.getHorarioDisp();
					// The available hour is the intersection between
					// this two sets
					LinkedHashSet<Horario> intersection = new LinkedHashSet<Horario>();
					intersection.addAll(horarioProf);
					intersection.retainAll(horarioLugar);
					if (lugar.getCapacidad() >= capacidad
							&& intersection.size() > 0) {
						Horario horario = null;
						// El primer horario disponible
						for (Horario h : intersection) {
							horario = h;
							break;
						}
						
						assert horario != null;
						
						// Genera la propuesta y termina
						proposal = new Asignacion(grupoId, prof, lugar, horario);
						proposal_value = 1 + (lugares.size() - lugarCount);
						break;
					}
					lugarCount++;
				}
			} catch (SQLException e) {
				logError(myAgent, e.getMessage());
				return PROPOSAL_ERROR;
			}

			log(myAgent, "Proposal is" + proposal.toString());
			return proposal_value;
		}

		/** Return false if this method fails. */
		private boolean setClassroomProposal() {

			boolean result = true;
			result &= proposal.getProf().setHorarioBusy(proposal.getHorario().getId());
			result &= proposal.getLugar().setHorarioBusy(proposal.getHorario().getId());

			asignaciones.add(proposal);
			// Reset the proposal values
			proposal = null;
			return result;
		}
	}
}