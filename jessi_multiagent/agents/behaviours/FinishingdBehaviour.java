package agents.behaviours;

import static misc.DebugFunctions.log;

import java.util.ArrayList;
import java.util.List;

import jade.core.AID;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class FinishingdBehaviour extends CyclicBehaviour{

	
	private List<AID> signalPropatagionAgents;
	
	public FinishingdBehaviour(List<AID> signalPropagationAgents) {
		super();
		this.signalPropatagionAgents = signalPropagationAgents;
	}
	
	public FinishingdBehaviour() {
		super();
		signalPropatagionAgents = new ArrayList<AID>();
	}
	
	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			log(myAgent, "finishing");
			log(myAgent, "sending finishing signal");
			ACLMessage m = new ACLMessage(ACLMessage.PROPAGATE);
			// Add all the agents that will receive the proposal request
			for (AID agent : signalPropatagionAgents) {
				m.addReceiver(agent);
			}
			log(myAgent, "Sending finished signal");
			myAgent.send(m);
			myAgent.doDelete();
		} else {
			block();
		}
	}
}