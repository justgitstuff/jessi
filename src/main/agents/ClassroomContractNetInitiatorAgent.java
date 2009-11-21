package main.agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.lang.acl.ACLMessage;
import jade.proto.ContractNetInitiator;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.util.Date;
import java.util.Vector;
import java.util.Enumeration;
import java.util.ArrayList;
import static misc.DebugFunctions.*;

/**
 * This class is an agent that implements the FIPA contract net interaction
 * protocol. We use the ContractNetInitiator behavior, which asks other agents
 * to search for a classroom that matches some specifications.
 * 
 * The subclass SearchForAgentsBehaviour is a cyclic behaviour which looks
 * for the other agents that can provide the search service named "classroom-search".
 * 
 * @author Rogelio Ramirez
 */
public class ClassroomContractNetInitiatorAgent extends Agent {

	// Agents that give offers
	private ArrayList<AID> classroomOfferingAgents = new ArrayList<AID>();
	// Total responders of the Request For Proposal
	private int nResponders;
	private int remainingRequests = 1;

	protected void setup() {
		
		log(this, "looking for agents");
		addBehaviour(new SearchForAgentsBehaviour(this, 15000L));
		addBehaviour(new CallForProposalsBehaviour());
	}
	
	/**
	 * @author rogelio
	 */
	private class CallForProposalsBehaviour extends CyclicBehaviour {

		private static final long MAX_WAITING_TIME = 3000;
		
		@Override
		public void action() {
			// Do nothing if there are no agents to serve the request proposal
			if(classroomOfferingAgents == null || 
			   classroomOfferingAgents.size() == 0 ||
			   remainingRequests <= 0) {
				return;
			}
			
			// We already have some classroom-offering agents, make them work !
			log(myAgent, "Trying to ask for a classroom to " + classroomOfferingAgents.size() + " responders");
			// Fill the CFP message (this is going to be sent to all the agents
			// that provide the service
			ACLMessage msg = new ACLMessage(ACLMessage.CFP);
			// Add all the agents that will receive de proposal request
			for (AID agent: classroomOfferingAgents) {
				msg.addReceiver(agent);
			}
			// Set the interaction protocol
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			// We want to receive a reply in at most 3 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + MAX_WAITING_TIME));
			msg.setContent("dummy-classroom-search");	
			// Add the contract net
			addBehaviour(new ClassroomContractNetInitiatorBehaviour(myAgent, msg));
			
			//TODO: Delete this
			remainingRequests--;
		}
	}

	/**
	 * This beheviour searches for agents which offer a service of the type
	 * "classroom-search". The name of the agents found that offer this service
	 * are saved in the classroomOfferingAgents list.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class SearchForAgentsBehaviour extends TickerBehaviour {
	
		public SearchForAgentsBehaviour(Agent a, long period) {
			super(a, period);
			log(a, "Agent searching behaviour initialized.");
		}
		/**
		 * Search every tick for new agents offering the classroom search service.
		 */
		@Override
		protected void onTick() {
			log(myAgent,  "searching for new agents");
			// Template to ask the DF to search for the agent
			DFAgentDescription template = new DFAgentDescription();
			// The service description we are looking for
			ServiceDescription sd = new ServiceDescription();
			// Set the type of the service description
			sd.setType("classroom-search");
			// At the service type to the template we are searching for
			template.addServices(sd);

			// Ask for all the agents that have the classroom-search service
			// and add them to the list.
			try {
				// Search for the agents that offer the service
				DFAgentDescription[] result = DFService.search(myAgent,template);
				// Add only the new agents that were found
				for (DFAgentDescription dad : result) {
					StringBuilder sb = new StringBuilder();
					sb.append("Found the following classroom-offering agents:\n");
					if (!classroomOfferingAgents.contains(dad)) {
						classroomOfferingAgents.add(dad.getName());
						sb.append("\t" + dad.getName() + "\n");
					}
					log(myAgent, sb.toString());
				}
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
	}
	
	private class ClassroomContractNetInitiatorBehaviour extends ContractNetInitiator {

		public ClassroomContractNetInitiatorBehaviour(Agent a, ACLMessage cfp) {
			super(a, cfp);
		}
		
		/** Handle propose is executed every time we receive a proposal */
		protected void handlePropose(ACLMessage propose, Vector v) {
			log(myAgent, propose.getSender().getName()+ " proposed " + propose.getContent());
		}
		/** Handle refuse is executed every time the answer is a refuse instead of a proposal */
		protected void handleRefuse(ACLMessage refuse) {
			log(myAgent, refuse.getSender().getName() + " refused");
		}
		/** Handle failure is executed if the agent we asked for the proposal 
		 * does not exist */
		protected void handleFailure(ACLMessage failure) {
			if (failure.getSender().equals(myAgent.getAMS())) {
				// FAILURE notification from the JADE runtime: the receiver
				// does not exist
				log(myAgent, "Responder does not exist");
			} else {
				log(myAgent, failure.getSender().getName() + " failed");
			}
			// Immediate failure --> we will not receive a response from
			// this agent
			nResponders--;
		}
		
		/**
		 * HandleAllResponses is executed once we have received all the
		 * repsonses be it proposals or refuses. We select the best
		 * proposal once we have all of them.
		 */
		protected void handleAllResponses(Vector responses, Vector acceptances) {
			if (responses.size() < nResponders) {
				// Some responder didn't reply within the specified timeout
				System.out.println("Timeout expired: missing "
						+ (nResponders - responses.size())
						+ " responses");
			}
			// Evaluate proposals.
			int bestProposal = -1;
			AID bestProposer = null;
			ACLMessage accept = null;
			Enumeration e = responses.elements();
			while (e.hasMoreElements()) {
				ACLMessage msg = (ACLMessage) e.nextElement();
				if (msg.getPerformative() == ACLMessage.PROPOSE) {
					ACLMessage reply = msg.createReply();
					// Set the response performative to "reject" by default
					// then the performative of the best offer is
					// changed to "accept"
					reply.setPerformative(ACLMessage.REJECT_PROPOSAL);
					acceptances.addElement(reply);
					int proposal = Integer.parseInt(msg.getContent());
					if (proposal > bestProposal) {
						bestProposal = proposal;
						bestProposer = msg.getSender();
						accept = reply;
					}
				}
			}
			// Accept the proposal of the best proposer
			if (accept != null) {
				log(myAgent, "Accepting proposal " + bestProposal
						+ " from responder " + bestProposer.getName());
							accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
			}
		}
		protected void handleInform(ACLMessage inform) {
			log(myAgent, inform.getSender().getName() 
						 + " successfully performed the requested action");
		}
	}
}
