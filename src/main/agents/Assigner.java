package agents;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Queue;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import utility.Pair;

public class Assigner extends Agent{
	private static final long serialVersionUID = 1L;
	public final static String OK = "OK";
	public final static String NOSOL = "NoSolution";
	private ConnectionFactory factory;
	private Connection conexion;
	private Queue<String> que;
	private Queue<Pair<String,String>> assigned;
	private Pair<String,String> lastSent;
	Assigner()
	{
		factory=new ConnectionFactory();
		conexion=factory.getConnection();
		que=new LinkedList<String>();
		assigned=new LinkedList<Pair<String,String>>();
		init();
	}
	
	private void fillQueue(String queue)
	{
		for(String a : queue.split(","))
		{
			que.offer(a);
		}
	}
	
	private String next()
	{
		return assignNewProf(que.poll());
	}

	private String filterMsg(String msg)
	{
		if(msg==OK)
		{
			return next();
		}else if(msg.split(",").length==2)
		{
			String[] a=msg.split(",");
			assigned.offer(new Pair<String,String>(a[0],a[1]));
			return next();
		}else if(msg==NOSOL)
		{
			return assignNewProf(lastSent);
		}else
		{
			fillQueue(msg);
			return next();
		}
	}
	
	private String assignNewProf(String group) {
		String prof="";
		//algoritmo de seleccion
		
		
		
		lastSent.setLeft(group);
		lastSent.setRight(prof);
		assigned.offer(lastSent);
		return group+","+prof;
	}
	
	private String assignNewProf(Pair<String, String> par) {
		String newProf="";
		//obtener un nuevo prof que no sea el que ya se envio previamente
		
		lastSent.copy(par);
		return par.getLeft()+","+par.getRight();
	}

	private void init()
	{
		addBehaviour
		(
			new CyclicBehaviour(this) 
			{
				private static final long serialVersionUID = 1L;

				public void action() 
				{
					ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.INFORM));
					if (msg != null) 
					{
						String content=filterMsg(msg.getContent());
							try {
								if(!conexion.isValid(100))
								{
									conexion=factory.getConnection();
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
						if(content!=null)
						{
							ACLMessage message = new ACLMessage(ACLMessage.INFORM);
							message.setContent(content);
							//message.addReceiver(r);
							myAgent.send(message);
							System.out.println(myAgent.getLocalName()+" SENT MESSAGE WITH ASSIGNMENT");
						}
					} else 
					{
						block();
					}
				}
			}
		);
	}
}
