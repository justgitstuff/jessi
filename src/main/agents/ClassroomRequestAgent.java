package main.agents;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import static misc.DebugFunctions.*;

// TODO: Delete this comment.
// This class should send to the contract net only the
// group id and the professor id, it should then wait for an answer to send the next
// request 
public class ClassroomRequestAgent extends Agent {
	
	// Time between searches for the contract net agents.
	private static final long SEARCH_DELTA = 15000L;
	// The list of the agents that provide the contract network service
	// NOTE: This is just one agent, but may be extended in the future
	private ArrayList<AID> CNAgents = new ArrayList<AID>();
	// List of the classroom requests needed. Contains several requests
	// of the form (groupd_id, professor_id)
	private LinkedList<String> classroomRequests = new LinkedList<String>();
	// Regex to parse the responses of the type (group_id, professor_id)
	private final String REGEX = "\\((\\d+),(\\d+)\\)";
	
	@Override
	protected void setup() {
		// Search for the agent(s) that provides the contract-net-request-receiver
		// service, so we can send it the classroom requests
		addBehaviour(new SearchServiceBehaviour(this, SEARCH_DELTA, CNAgents, 
					 ClassroomContractNetInitiatorAgent.SERVICE_TYPE));
		// TODO: Delete this comment 
		// READ the database, get the groups, and send them their id along
		// with the professor id to the ClassroomContracNetworkInitiatorAgent
		
		// TODO: Delete this code, and comment
		// Lets assume we have already red the database and we have obtained 10
		// different groups, so we send each group one by one to request the classroom
		classroomRequests.add("(1,1)");
		classroomRequests.add("(2,1)");
		classroomRequests.add("(3,1)");
		classroomRequests.add("(4,2)");
		classroomRequests.add("(5,2)");
		classroomRequests.add("(6,2)");
		classroomRequests.add("(7,3)");
		classroomRequests.add("(8,3)");
		classroomRequests.add("(9,3");
		classroomRequests.add("(10,1)");
		classroomRequests.add("(11,2)");
		classroomRequests.add("(12,3)");
		classroomRequests.add("(13,4)");
		
		// This behavior should just be added after we got a Collection
		// with all the group requests ready 
		addBehaviour(new ClassroomRequestBehaviour());
	}
	
	private class ClassroomRequestBehaviour extends Behaviour {

		// Variable indicating the actual state
		private int state = 0;
		// Message template, this constructed according to what this
		// agents needs to hear
		private MessageTemplate mt;
		// The conversation id
		private static final String CONVERSATION_ID = "classroom-request";
		// The different agent states 
		private static final int WAIT_FOR_AGENTS = 0;
		private static final int CREATE_REQUEST = 1;
		private static final int RECEIVE_RESPONSE = 2;
		private static final int FINISHED = 3;
		
		@Override
		public void action() {
			switch(state) {
				// If there aren't any agents to give the message to, do 
				// nothing, else change to the state to prepare a new request
				case WAIT_FOR_AGENTS:
					if(CNAgents.size() > 0) {
						state = CREATE_REQUEST;
					}
					break;
				// Create a request and send it
				case CREATE_REQUEST:
					// Create the message
					ACLMessage msg = new ACLMessage(ACLMessage.REQUEST);
					// Add the receiver for the message
					msg.addReceiver(CNAgents.get(0));
					// Set the content for the message, a tuple with the 
					// group id and the professor id
					msg.setContent(classroomRequests.peek());
					// Set a conversation id
					msg.setConversationId(CONVERSATION_ID);
					// Create a reply-with (this needs to be unique)
					msg.setReplyWith("classroom-request" + System.currentTimeMillis());
					// Send the message
					myAgent.send(msg);
					// Prepare a message hearing template for the response 
					mt = MessageTemplate.and(
							MessageTemplate.MatchConversationId(CONVERSATION_ID), 
							MessageTemplate.MatchInReplyTo(msg.getReplyWith()));
					state = RECEIVE_RESPONSE;
					break;
				case RECEIVE_RESPONSE:
					// Receive the response 
					ACLMessage response = myAgent.receive(mt);
					// If there was a reply check the type
					if(response != null) {
						// Get the content and the performative of the 
						// response and act accordingly 
						String content = response.getContent().trim();
						int perf = response.getPerformative();
						// A successful classroom assignment
						if(perf == ACLMessage.INFORM) {
							// Regex to check if content has a tuple response
							Pattern p = Pattern.compile(REGEX);
							Matcher m = p.matcher(content);
							// If the content of the message is "ok" then the 
							// classroom was assigned successfully.
							if(content.equals("ok")) {
								log(myAgent, "Classroom successfully assigned");
								classroomRequests.pop();
							}
							// The content is a (group_id, professor_id)
							// so add it back to the queue. 
							else if(m.find()) {
								classroomRequests.pop();
								classroomRequests.add(content);
								log(myAgent, "Classroom assigned, but returned " + 
									"collision. " + content);
							}
							else {
								String message = "Illegal state";
								logError(myAgent, message);
								assert false : message;
							}	
						}
						// Refusal 
						else if(perf == ACLMessage.REFUSE){
							log(myAgent, "Classroom assignment refused.");
							// TODO: Here you should switch for a different professor
							// to see if the course can have an assignment 
							// TODO: Don't forget to remember which professors you've
							// tested this group with, so if you're out of alternatives
							// you should just give up
						}
						else {
							String error = "Performative has an invalid value";
							log(myAgent, error);
							assert false : error;
						}
						
						state = (classroomRequests.size() > 0) ? 
								CREATE_REQUEST : FINISHED;
					}
					// If there wasn't any reply, block the behavior until
					// a message is received
					else {
						block();
					}
					break;
			}
		}

		@Override
		public boolean done() {
			return classroomRequests.size() == 0 || state == FINISHED;
		}		
	}
}