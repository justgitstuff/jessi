package main.agents;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class DatabaseAgent extends Agent{
	private static final long serialVersionUID = 1L;
	private ConnectionFactory factory;
	private Connection conexion;
	private String nameDB;
	DatabaseAgent(String nombreDB)
	{
		nameDB=nombreDB;
		factory=new ConnectionFactory();
		conexion=factory.getConnection(nameDB);
		init();
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
					ACLMessage msg = receive(MessageTemplate.MatchPerformative(ACLMessage.REQUEST));
					if (msg != null) 
					{
						ResultSetMetaData resultMeta;
						ResultSet result;
						String replyMsg="";
						try 
						{
							if(!conexion.isValid(100))
							{
								conexion=factory.getConnection(nameDB);
							}
							result=conexion.prepareStatement(msg.getContent()).executeQuery();
							resultMeta=result.getMetaData();
							while(result.next())
							{
								for(int i=0;i<resultMeta.getColumnCount();i++)
								{
									if(i==resultMeta.getColumnCount()-1)
									{
										replyMsg+=result.getString(i)+"/n";
									}else
									{
										replyMsg+=result.getString(i)+",";
									}
								}
							}
						} catch (SQLException e) 
						{
							e.printStackTrace();
						} 
						ACLMessage reply = msg.createReply();
						reply.setContent(replyMsg);
						myAgent.send(reply);
						System.out.println(myAgent.getLocalName()+" SENT ANSWER MESSAGE WITH QUERY RESULT");
					} else 
					{
						block();
					}
				}
			}
		);
	}
}
