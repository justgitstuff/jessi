package agents.behaviours;

import static misc.DebugFunctions.log;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class FinishingdBehaviour extends CyclicBehaviour{

	@Override
	public void action() {
		MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPAGATE);
		ACLMessage msg = myAgent.receive(mt);
		if (msg != null) {
			log(myAgent, "finishing");
			myAgent.doDelete();
		} else {
			block();
		}
	}
}