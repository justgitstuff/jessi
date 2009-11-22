package misc;

import java.io.PrintWriter;
import jade.core.Agent;

public final class DebugFunctions {
	
	/** Debug flag */
	private static final boolean DEBUG = true;
	/** Output to stdout and stderr */
	private static final PrintWriter PRINT_ERROR = new PrintWriter(System.err);
	private static final PrintWriter PRINT_MSG = new PrintWriter(System.out);
	
	/** Log function */
	private static void log(String who, String message, PrintWriter p) {
		p.println("Agent " + who + ": " +  message);
		p.flush();
	}
	/** Log function */
	private static void log(Agent agent, String message, boolean error) {
		if(DEBUG) {
			log(agent.getLocalName(), message, error ? PRINT_ERROR : PRINT_MSG);
		}
	}
	/** Log function */
	public static void log(Agent agent, String message) {
		log(agent, message, false);
	}
	
	public static void logError(Agent agent, String message) {
		log(agent, message, true);
	}
}
