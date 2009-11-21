package main.agents;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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

/**
 * This example shows how to implement the initiator role in a FIPA-contract-net
 * interaction protocol. In this case in particular we use a
 * <code>ContractNetInitiator</code> to assign a dummy task to the agent that
 * provides the best offer among a set of agents (whose local names must be
 * specified as arguments).
 * 
 * @author Giovanni Caire - TILAB
 */
public class ClassroomContractNetInitiatorAgent extends Agent {

	// Agents that give offers
	private ArrayList<AID> classroomOfferingAgents = new ArrayList<AID>();
	// Total responders of the Request For Proposal
	private int nResponders;

	protected void setup() {

		addBehaviour(new SearchForAgentsBehaviour());
		
		
		// Read names of responders as arguments
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			nResponders = args.length;
			System.out.println("Trying to delegate dummy-action to one out of "
					+ nResponders + " responders.");
			// Fill the CFP message
			ACLMessage msg = new ACLMessage(ACLMessage.CFP);
			for (int i = 0; i < args.length; ++i) {
				msg.addReceiver(new AID((String) args[i], AID.ISLOCALNAME));
			}
			msg.setProtocol(FIPANames.InteractionProtocol.FIPA_CONTRACT_NET);
			// We want to receive a reply in at most 3 secs
			msg.setReplyByDate(new Date(System.currentTimeMillis() + 3000));
			msg.setContent("dummy-action");

			addBehaviour(new ContractNetInitiator(this, msg) {

				/** Handle propose is executed every time we receive a proposal */
				protected void handlePropose(ACLMessage propose, Vector v) {
					System.out.println("Agent " + propose.getSender().getName() + " proposed " + propose.getContent());
				}

				protected void handleRefuse(ACLMessage refuse) {
					System.out.println("Agent " + refuse.getSender().getName() + " refused");
				}

				protected void handleFailure(ACLMessage failure) {
					if (failure.getSender().equals(myAgent.getAMS())) {
						// FAILURE notification from the JADE runtime: the
						// receiver
						// does not exist
						System.out.println("Responder does not exist");
					} else {
						System.out.println("Agent "
								+ failure.getSender().getName() + " failed");
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
				protected void handleAllResponses(Vector responses,
						Vector acceptances) {
					if (responses.size() < nResponders) {
						// Some responder didn't reply within the specified
						// timeout
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
							// Set the response performative to "reject" by
							// default
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
						System.out.println("Accepting proposal " + bestProposal
								+ " from responder " + bestProposer.getName());
						accept.setPerformative(ACLMessage.ACCEPT_PROPOSAL);
					}
				}

				protected void handleInform(ACLMessage inform) {
					System.out.println("Agent " + inform.getSender().getName()
							+ " successfully performed the requested action");
				}
			});
		} else {
			System.out.println("No responder specified.");
		}
	}
	

	/**
	 * This beheviour searches for agents which offer a service of the type
	 * "classroom-search". The name of the agents found that offer this service
	 * are saved in the classroomOfferingAgents list.
	 * 
	 * @author Rogelio Ramirez
	 */
	private class SearchForAgentsBehaviour extends CyclicBehaviour {

		@Override
		public void action() {
			DFAgentDescription template = new DFAgentDescription();
			ServiceDescription sd = new ServiceDescription();
			sd.setType("classroom-search");
			template.addServices(sd);

			try {
				// Search for the agents that offer the service
				DFAgentDescription[] result = DFService.search(myAgent,
						template);
				// Add only the new agents that were found
				for (DFAgentDescription dad : result) {
					if (!classroomOfferingAgents.contains(dad)) {
						System.out.println("Found the following classroom-offering agents:");
						classroomOfferingAgents.add(dad.getName());
					}
				}
			} catch (FIPAException fe) {
				fe.printStackTrace();
			}
		}
	}
}
