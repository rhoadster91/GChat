package skn.rhoadster.chatserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

import skn.rhoadster.chatclient.Message;

public class ChatServerMain extends Thread
{
	private static ClientHelper tempHelper;
	protected static ArrayList<ClientHelper> clientHelpers = new ArrayList<ClientHelper>();
	private static FileHelper fileHelper;
	private static DownloadHelper downloadHelper;
	
	public static void main(String[] args) 
	{
		new ChatServerMain().start();
		Thread fileServer = new Thread()
		{
			@Override
			public void run()
			{
				try 
				{
					ServerSocket server = new ServerSocket(9899);
					System.out.println("File server running.");
					while(true)
					{
						Socket socket = server.accept();
						fileHelper = new FileHelper(socket);
						fileHelper.start();
						
					}
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
			}
		};
		fileServer.start();
		Thread downloadServer = new Thread()
		{
			@Override
			public void run()
			{
				try 
				{
					ServerSocket server = new ServerSocket(9897);
					System.out.println("Download server running.");
					while(true)
					{
						Socket socket = server.accept();
						downloadHelper = new DownloadHelper(socket);
						downloadHelper.start();						
					}
				}
				catch (IOException e) 
				{
					e.printStackTrace();
				}	
			}
		};
		downloadServer.start();
	}
	
	@Override
	public void run()
	{
		try 
		{
			ServerSocket server = new ServerSocket(9898);
			System.out.println("Server running.");
			while(true)
			{
				Socket socket = server.accept();
				tempHelper = new ClientHelper(socket);
				tempHelper.start();
				
			}
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}		
	}
	
	public static synchronized void pushToAll(Message m)
	{
		Iterator<ClientHelper> clientIterator = clientHelpers.iterator();		
		while(clientIterator.hasNext())
		{
			tempHelper = clientIterator.next();
			if(tempHelper.room.contentEquals(m.room))
			{				
				tempHelper.writeToMe(m);
			}
		}
	}
	
	public static void logOff(String name, String room)
	{
		Iterator<ClientHelper> clientIterator = clientHelpers.iterator();
		while(clientIterator.hasNext())
		{
			tempHelper = clientIterator.next();
			if(tempHelper.name.contentEquals(name) && tempHelper.room.contentEquals(room))
			{
				Message m = new Message();
				m.content = new String(tempHelper.name + " left the room.");
				m.room = tempHelper.room;				
				clientIterator.remove();
				pushToAll(m);
				break;
			}
		}
	}

}
