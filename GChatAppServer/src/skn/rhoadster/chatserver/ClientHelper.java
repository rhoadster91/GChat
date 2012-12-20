package skn.rhoadster.chatserver;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import skn.rhoadster.chatclient.Message;

public class ClientHelper extends Thread
{
	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	public String name;
	public String room;
	
	ClientHelper(Socket s)
	{
		socket = s;
		try 
		{
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());			
			Message m = (Message)objIn.readObject();
			name = m.sender;
			room = m.room;
			System.out.println(name + " joined room " + room + ".");						
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public void writeToMe(Message m)
	{
		try 
		{
			objOut.writeObject(m);
			objOut.flush();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void run()
	{
		try 
		{
			Iterator<ClientHelper> clientIterator = ChatServerMain.clientHelpers.iterator();
			ArrayList<ClientHelper> roomPeople = new ArrayList<ClientHelper>();			
			ClientHelper temp;
			while(clientIterator.hasNext())
			{
				temp = clientIterator.next();
				if(temp.room.contentEquals(room))
					roomPeople.add(temp);
			}
			clientIterator = roomPeople.iterator();
			Message m = new Message();
			m.room = room;
			if(!clientIterator.hasNext())
			{
				m = new Message();
				m.room = room;
				m.content = new String("No one else is currently in this room.");
				objOut.writeObject(m);
				objOut.flush();
			}
			else
			{
				m = new Message();
				m.room = room;
				m.content = new String("Other people in this room are:");
				objOut.writeObject(m);		
				objOut.flush();
				while(clientIterator.hasNext())
				{				
					m = new Message();
					m.room = room;
					m.content = new String(clientIterator.next().name); 
					objOut.writeObject(m);
					objOut.flush();
				}
			}
			ChatServerMain.clientHelpers.add(this);			
			m = new Message();
			m.room = room;
			m.content = new String(name + " joined the room.");
			ChatServerMain.pushToAll(m);			
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	
		while(true)
		{
			try 
			{
				Message msg = (Message)objIn.readObject();
				ChatServerMain.pushToAll(msg);
			} 
			catch (IOException e) 
			{				
				System.out.println(name + " left room " + room + ".");
				break;
			}
			catch (ClassNotFoundException e) 
			{				
				e.printStackTrace();
				break;
			}			
		}
		ChatServerMain.logOff(name, room);
	}
}
