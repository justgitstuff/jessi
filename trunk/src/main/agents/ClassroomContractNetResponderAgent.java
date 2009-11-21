package main.agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.OneShotBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetResponder;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.NotUnderstoodException;
import jade.domain.FIPAAgentManagement.RefuseException;
import jade.domain.FIPAAgentManagement.FailureException;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * This example shows how to implement the responder role in a FIPA-contract-net
 * interaction protocol. In this case in particular we use a
 * <code>ContractNetResponder</code> to participate into a negotiation where an
 * initiator needs to assign a task to an agent among a set of candidates.
 * 
 * @author Rogelio Ramirez
 */
public class ClassroomContractNetResponderAgent extends Agent {
	
	protected void setup() {

		// Register the service with the DFAgent
		addBehaviour(new RegisterServiceBehaviour());		
		// Create the message template for the contract net interaction protocol
		System.out.println("Agent " + getLocalName() + " waiting for CFP...");
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

	private class RegisterServiceBehaviour extends OneShotBehaviour {

		/** Register the classroom-search service in the yellow pages */
		@Override
		public void action() {
			DFAgentDescription dfd = new DFAgentDescription();
			dfd.setName(myAgent.getAID());
			ServiceDescription sd = new ServiceDescription();
			sd.setType("classroom-search");
			sd.setName("JADE-classroom-search");
			dfd.addServices(sd);
			try {
				DFService.register(myAgent, dfd);
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
	}

	/**
	 * This class extends the ContractNetResponder behaviour. 
	 * It receives messages (Call For Proposals), when one of this messages
	 * matches the template, received in the constructor the prepareResponse 
	 * method is executed.
	 * 
	 * @author rogelio
	 */
	private class ClassroomContractNetResponderBehaviour extends ContractNetResponder {

		public ClassroomContractNetResponderBehaviour(Agent a, MessageTemplate mt) {
			super(a, mt);
		}

		protected ACLMessage prepareResponse(ACLMessage cfp) throws NotUnderstoodException, RefuseException {
			System.out.println("Agent " + getLocalName()
					+ ": CFP received from " + cfp.getSender().getName()
					+ ". Action is " + cfp.getContent());
			int proposal = evaluateAction();
			if (proposal > 2) {
				// We provide a proposal
				System.out.println("Agent " + getLocalName() + ": Proposing " + proposal);
				ACLMessage propose = cfp.createReply();
				propose.setPerformative(ACLMessage.PROPOSE);
				propose.setContent(String.valueOf(proposal));
				return propose;
			} else {
				// We refuse to provide a proposal
				System.out.println("Agent " + getLocalName() + ": Refuse");
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