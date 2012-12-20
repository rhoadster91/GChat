package skn.rhoadster.chatserver;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import skn.rhoadster.chatclient.Message;

public class FileHelper extends Thread
{
	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	public String name;
	public String room;
	
	FileHelper(Socket s)
	{
		socket = s;
		try 
		{
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());			
			Message m = (Message)objIn.readObject();
			name = m.sender;
			room = m.room;
			System.out.println(name + " is trying to upload file to " + room + ".");						
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
	
	@Override
	public void run()
	{
		try
		{
			String fname = (String)objIn.readObject();
			File incomingFile = new File("D:\\GChatFiles\\" + fname);
			incomingFile.createNewFile();
			byte []buffer = (byte [])objIn.readObject();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(incomingFile));
			bos.write(buffer);
			bos.close();
			Message m = new Message();
			m.room = room;
			m.content = new String(name + " has uploaded a new file: " + fname);
			ChatServerMain.pushToAll(m);
			objOut.writeObject("Confirmed");
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
}
