package main.agents;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import static misc.DebugFunctions.*;

/**
 * Simple class to register a service with the DFAgent, just add this
 * OneShotBehaviour, send it an agent, the service type and the service
 * as a String.
 * @author Rogelio Ramirez
 */
public class RegisterServiceBehaviour extends OneShotBehaviour {

	private String type;
	private String name;
	
	public RegisterServiceBehaviour(Agent agent, String name, String type)  {
		this.myAgent = agent;
		this.name = name;
		this.type = type;
	}
	
	/** Register the classroom-search service in the yellow pages */
	@Override
	public void action() {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.setName(myAgent.getAID());
		ServiceDescription sd = new ServiceDescription();
		sd.setType(type);
		sd.setName(name);
		dfd.addServices(sd);
		try {
			DFService.register(myAgent, dfd);
			log(myAgent, "Service " + type + ":" + name + " registered.");
		} catch (FIPAException fe) {
			fe.printStackTrace();
		}
	}
}