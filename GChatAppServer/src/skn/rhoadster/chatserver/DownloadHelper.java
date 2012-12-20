package skn.rhoadster.chatserver;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


public class DownloadHelper extends Thread
{
	private Socket socket;
	private ObjectInputStream objIn;
	private ObjectOutputStream objOut;
	public String name;
	public String room;
	
	DownloadHelper(Socket s)
	{
		socket = s;
		try 
		{
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objIn = new ObjectInputStream(socket.getInputStream());			
								
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
			
	}
	
	@Override
	public void run()
	{
		try
		{
			String fileName = (String)objIn.readObject();
			String filePath = new String("D:\\GChatFiles\\" + fileName);
			File outgoingFile = new File(filePath);
			int fileSize = (int)outgoingFile.length();
			byte []buffer = new byte[fileSize];			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(outgoingFile));
			bis.read(buffer, 0, buffer.length);
			objOut.writeObject(buffer);
			objOut.flush();	
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
