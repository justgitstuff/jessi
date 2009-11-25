package misc;

import java.io.PrintWriter;
import jade.core.Agent;


public final class DebugFunctions {
	
	// Debug flag 
	private static final boolean DEBUG = true;
	// Output to stdout
	private static final PrintWriter PRINT_ERROR = new PrintWriter(System.err);
	// Output to stderr
	private static final PrintWriter PRINT_MSG = new PrintWriter(System.out);
	
	/** Basic log function, used by all the other log functions */
	private static void log(String who, String message, PrintWriter p) {
		if(DEBUG) {
			p.println("Agent " + who + ": " +  message);
			p.flush();
		}
	}
	
	/** Log function */
	private static void log(Agent agent, String message, boolean error) {
		String name = agent != null ? agent.getLocalName() : "";
		log(name, message, error ? PRINT_ERROR : PRINT_MSG);
		
	}
	/** Message log with agent */
	public static void log(Agent agent, String message) {
		log(agent, message, false);
	}
	
	/** Message log without agent */
	public static void log(String message) {
		log(null, message);
	}
	
	/** Error log with agent */
	public static void logError(Agent agent, String message) {
		log(agent, message, true);
	}
	
	/** Error log without agent */
	public static void logError(String message) {
		logError(null, message);
	}
}
