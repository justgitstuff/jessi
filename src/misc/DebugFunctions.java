package misc;

import jade.core.Agent;

public final class DebugFunctions {
	
	/** Debug flag */
	private static final boolean DEBUG = true;
	
	/** Log function */
	private static void log(String who, String message) {
		if(DEBUG) {
			System.out.println("Agent " + who + ": " +  message);
		}
	}
	/** Log function */
	public static void log(Agent agent, String message) {
		if(DEBUG) {
			log(agent.getLocalName(), message);
		}
	}
}
