package main.agents;

import java.sql.Connection;

import jade.core.Agent;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
import static misc.DebugFunctions.*;

/**
 * This example shows how to implement the responder role in a FIPA-contract-net
 * interaction protocol. In this case in particular we use a
 * ContractNetResponder to participate into a negotiation where an
 * initiator needs to assign a task to an agent among a set of candidates.
 * 
 * @author Rogelio Ramirez
 */
public class ClassroomContractNetResponderAgent extends Agent {
	
	public static final String SERVICE_NAME = "JADE-classroom-search";
	public static final String SERVICE_TYPE = "classroom-search";
	private ConnectionFactory connectionFactory = null;
	private Connection connection = null;
	
	public ClassroomContractNetResponderAgent() {
		super(); // Call the agent constructor
		connectionFactory = new ConnectionFactory();
		connection = connectionFactory.getConnection();
		
		assert false;
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
	
	private int evaluateAction() {
		// Simulate an evaluation by generating a random number
		return (int) (Math.random() * 10);
	}

	private boolean performAction() {
		// Simulate action execution by generating a random number
		return (Math.random() > 0.0);
	}
	
	/**
	 * This class extends the ContractNetResponder behaviour. 
	 * It receives messages (Call For Proposals), when one of this messages
	 * matches the template, received in the constructor the prepareResponse 
	 * method is executed.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class ClassroomContractNetResponderBehaviour extends ContractNetResponder {

		public ClassroomContractNetResponderBehaviour(Agent a, MessageTemplate mt) {
			super(a, mt);
		}
		
		/**
		 * When a message arrives that matches the message template passed to 
		 * the constructor, the callback method prepareResponse is executed.
		 * It must return the wished response, for instance the PROPOSE  
		 * reply message. 
		 */
		protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
			log(myAgent, "CFP received from " + cfp.getSender().getName());
			log(myAgent, "Action is " + cfp.getContent());
			
			int proposal = evaluateAction();
			if (proposal > 0) {
				// We provide a proposal
				log(myAgent, "Proposing " + proposal);
				ACLMessage propose = cfp.createReply();
				propose.setPerformative(ACLMessage.PROPOSE);
				propose.setContent(String.valueOf(proposal));
				return propose;
			} else {
				// We refuse to provide a proposal
				log(myAgent, getLocalName() + ": Refuse");
				throw new RefuseException("evaluation-failed");
			}
		}
		
		/** This method is executed when the proposal is accepted. This returns 
		 * a message with INFORM performative to tell the proposal was accepted
		 * correctly and  FAILURE performative to tell the 
		 */ 
		protected ACLMessage prepareResultNotification(ACLMessage cfp,
				ACLMessage propose, ACLMessage accept) throws FailureException {
			
			System.out.println("Agent " + getLocalName() + ": Proposal accepted");
			
			if (performAction()) {
				System.out.println("Agent " + getLocalName() + ": Action successfully performed");
				ACLMessage inform = accept.createReply();
				inform.setPerformative(ACLMessage.INFORM);
				return inform;
			} else {
				System.out.println("Agent " + getLocalName()+ ": Action execution failed");
				throw new FailureException("unexpected-error");
			}
		}

		/** This method is executed when a proposal is rejected */
		protected void handleRejectProposal(ACLMessage cfp, ACLMessage propose, ACLMessage reject) {
			// Output that the proposal was rejected and do nothing about it
			System.out.println("Agent " + getLocalName() + ": Proposal rejected");
		}
	}
}