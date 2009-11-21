package main.agents;

import static misc.DebugFunctions.log;

import java.util.List;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;

/**
 * This behavior searches for agents which offer a service of the type
 * indicated type. The name of the agents found that offer this service
 * are inserted in a given list.
 * 
 * @author Rogelio Ramirez
 */
public class SearchServiceBehaviour extends TickerBehaviour {

	private List<AID> offerAgents;
	private String serviceType;
	
	public SearchServiceBehaviour(Agent a, long period, List<AID> offerAgents,
								  String serviceType) {		
		super(a, period);
		this.myAgent = a;
		this.offerAgents = offerAgents;
		this.serviceType = serviceType;
		log(a, "Searching behaviour initialized.");
	}
	/**
	 * Search every tick for new agents offering the classroom search service.
	 */
	@Override
	protected void onTick() {
		log(myAgent,  "searching for new agents for the " + serviceType);
		// Template to ask the DF to search for the agent
		DFAgentDescription template = new DFAgentDescription();
		// The service description we are looking for
		ServiceDescription sd = new ServiceDescription();
		// Set the type of the service description
		sd.setType(serviceType);
		// Add the service type to the template we are searching for
		template.addServices(sd);
		// Ask for all the agents that have the given service type 
		// and add them to the list.
		try {
			// Search for the agents that offer the service
			DFAgentDescription[] result = DFService.search(myAgent,template);
			// Add only the new agents that were found
			for (DFAgentDescription dad : result) {
				StringBuilder sb = new StringBuilder();
				sb.append("Found the following " + serviceType +  "agents:\n");
				if (!offerAgents.contains(dad)) {
					offerAgents.add(dad.getName());
					sb.append("\t" + dad.getName() + "\n");
				}
				log(myAgent, sb.toString());
			}
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}