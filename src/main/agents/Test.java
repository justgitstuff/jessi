package main.agents;
import jade.core.Agent;

public class Test extends Agent {
	protected void setup() {
		// Printout a welcome message
		System.out.println("Hello! Buyer-agent " +getAID().getName()+ " is ready.");
	}
}