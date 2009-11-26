package main.agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.ContractNetInitiator;
import jade.domain.FIPANames;
import java.util.Date;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Enumeration;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static misc.DebugFunctions.*;

/**
 * This class is an agent that implements the FIPA contract net interaction
 * protocol. We use the ContractNetInitiator behavior, which asks other agents
 * to search for a classroom that matches some specifications.
 * 
 * The subclass SearchForAgentsBehaviour is a cyclic behaviour which looks for
 * the other agents that can provide the search service named
 * "classroom-search".
 * 
 * @author Rogelio Ramirez
 */
public class ClassroomContractNetInitiatorAgent extends Agent {

	// Agents that give offers
	private ArrayList<AID> classroomOfferAgents = new ArrayList<AID>();
	// List that contains the requests as soon as they arrive, and the
	// ACLMessage
	// For this request awaiting to be replied
	private LinkedList<String> classroomRequests = new LinkedList<String>();
	// Total responders of the Request For Proposal
	private int nResponders;
	// The message that is sent to the contract network, this is changed for
	// every new classroom request
	private ACLMessage msg;
	// Time between searches for agents that offer the classroom-search service
	private final long TIME_BETWEEN_SEARCHES = 10000L;
	// CFP flag, indicates if a request to the CN is already being made
	private boolean cfp_in_process = false;
	// Reply, to queue processing agent
	private ACLMessage queueProcessorReply = null;
	// Service registered info
	public static final String SERVICE_NAME = "JADE-CN-classroom-request";
	public static final String SERVICE_TYPE = "CN-classroom-request-receiver";

	protected void setup() {

		log(this, "looking for agents");
		// Add the behavior that receives classroom searches requests
		addBehaviour(new ReceiveRequestsServer());
		// Register this agent in the DF
		addBehaviour(new RegisterServiceBehaviour(this, SERVICE_NAME,
				SERVICE_TYPE));
		// Add the behavior that continually searches for agents that offer
		// the classroom-search service
		addBehaviour(new SearchServiceBehaviour(this, TIME_BETWEEN_SEARCHES,
				classroomOfferAgents,
				ClassroomContractNetResponderAgent.SERVICE_TYPE));
		// Add the behavior that creates the message that will be used
		// by the ContractNetInitiator
		addBehaviour(new CallForProposalsBehaviour());
	}

	/**
	 * This behavior only processes the classroom queue. Constructs the Call for
	 * proposal message that the ClassroomContractNetworkInitiatorBehaviour will
	 * use to ask the ContractNetwrok for classrooms.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class CallForProposalsBehaviour extends CyclicBehaviour {

		private static final long MAX_WAITING_TIME = 2000;

		@Override
		public void action() {
			// Do nothing if there are no agents to serve the request proposal
			if (classroomOfferAgents == null
					|| classroomOfferAgents.size() == 0
					|| classroomRequests.size() == 0 || cfp_in_process) {
				return;
			}

			log(myAgent, "CallForProposalBehaviour executing");
			// We already have some classroom-offering agents, make them work !
			log(myAgent, "Trying to ask for a classroom to "
					+ classroomOfferAgents.size() + " responders");
			// Fill the CFP message (this is going to be sent to all the agents
			// that provide the service
			msg = new ACLMessage(ACLMessage.CFP);
			// Add all the agents that will receive the proposal request
			for (AID agent : classroomOfferAgents) {
				msg.addReceiver(agent);
			}
			// Set the interaction protocol
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			// We want to receive a reply in at most 3 seconds
			msg.setReplyByDate(new Date(System.currentTimeMillis()
					+ MAX_WAITING_TIME));
			msg.setContent("classroom-search(" + classroomRequests.getFirst()
					+ ")");
			// Mark that a call for proposal is in process
			cfp_in_process = true;
			// Add the ContractNetInitiatorAgentBehaviour
			myAgent.addBehaviour(new ClassroomContractNetInitiatorBehaviour(
					myAgent, msg));
		}
	}

	/**
	 * This Behaviour extends the ContractNetInitiatior Behaviour. Once the
	 * requests for proposals have been sent, it handles the messages received
	 * when a propose, refuse and failure are sent.
	 * 
	 * When all the responses have been received or the specified milis have
	 * passed the handleAllResponses method is executed.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class ClassroomContractNetInitiatorBehaviour extends
			ContractNetInitiator {

		public ClassroomContractNetInitiatorBehaviour(Agent a, ACLMessage cfp) {
			super(a, cfp);
		}

		/** Handle propose is executed every time we receive a proposal */
		protected void handlePropose(ACLMessage propose, Vector v) {
			log(myAgent, propose.getSender().getName() + " proposed "
					+ propose.getContent());
		}

		/**
		 * Handle refuse is executed every time the answer is a refuse instead
		 * of a proposal
		 */
		protected void handleRefuse(ACLMessage refuse) {
			log(myAgent, refuse.getSender().getName() + " refused");
		}

		/**
		 * Handle failure is executed if the agent we asked for the proposal
		 * does not exist
		 */
		protected void handleFailure(ACLMessage failure) {
			if (failure.getSender().equals(myAgent.getAMS())) {
				// FAILURE notification from the JADE runtime: the receiver
				// does not exist
				log(myAgent, "Responder does not exist");
			} else {
				log(myAgent, failure.getSender().getName() + " failed");
			}
			// Immediate failure --> we will not receive a response from this
			// agent
			nResponders--;
		}

		/**
		 * HandleAllResponses is executed once we have received all the
		 * repsonses be it proposals or refuses. We select the best proposal
		 * once we have all of them.
		 */
		protected void handleAllResponses(Vector responses, Vector acceptances) {
			if (responses.size() < nResponders) {
				// Some responder didn't reply within the specified timeout
				System.out.println("Timeout expired: missing "
						+ (nResponders - responses.size()) + " responses");
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
				// Tell the queue processor agent that a classroom was found
				// send a message with the corresponding assignment
				queueProcessorReply.setPerformative(ACLMessage.INFORM);
				queueProcessorReply.setContent("ok");
			}
			// There wasn't any proposal. So the search for a classroom has
			// failed
			// tell queue processor agent so.
			else {
				queueProcessorReply.setPerformative(ACLMessage.REFUSE);
				queueProcessorReply.setContent("not-available");
			}
			myAgent.send(queueProcessorReply);
		}

		/**
		 * This method is invoked when a node acknowledges that a proposal has
		 * been accepted.
		 */
		protected void handleInform(ACLMessage inform) {
			log(myAgent, inform.getSender().getName()
					+ " successfully performed the requested action");
			classroomRequests.pop();
			cfp_in_process = false;
		}
	}

	/**
	 * This behaviour waits for a message with the REQUEST performative and
	 * processes the message, adding a new request to the queue.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class ReceiveRequestsServer extends CyclicBehaviour {

		@Override
		public void action() {
			log(myAgent, "Executing ReceiveRequestsServer behavior ");
			MessageTemplate mt = MessageTemplate
					.MatchPerformative(ACLMessage.REQUEST);
			ACLMessage msg = myAgent.receive(mt);
			if (msg != null) {
				String content = msg.getContent();
				Pattern p = Pattern.compile("\\((\\d+),(\\d+)\\)");
				Matcher m = p.matcher(content);
				if (m.find()) {
					queueProcessorReply = msg.createReply();
					classroomRequests.add(content);
				} else {
					String error = "Received bad message from "
							+ msg.getSender() + " with content: " + content;
					logError(myAgent, error);
					assert false : error;
				}
			} else {
				block();
			}
		}
	}
}