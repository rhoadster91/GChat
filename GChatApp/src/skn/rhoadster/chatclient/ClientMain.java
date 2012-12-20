package skn.rhoadster.chatclient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class ClientMain 
{
	protected static String roomname;
	protected static String name;
	protected static ObjectInputStream objIn;
	protected static ObjectOutputStream objOut;
	protected static Socket socket;
	protected static String IP_ADDRESS;
	
	public static void main(String[] args) 
	{
		IP_ADDRESS = new String(args[0]);
		System.out.println("Enter your name: ");
		Scanner sc = new Scanner(System.in);
		name = sc.nextLine();
		
		System.out.println("Enter room name: ");
		sc = new Scanner(System.in);
		roomname = sc.nextLine();
		try 
		{
			socket = new Socket(IP_ADDRESS, 9898);
			try 
			{
				objIn = new ObjectInputStream(socket.getInputStream());
				objOut = new ObjectOutputStream(socket.getOutputStream());
				Message msg = new Message();
				msg.sender = name;
				msg.room = roomname;				
				objOut.writeObject(msg);
				objOut.flush();
				ChatGUI.launch();				
			}
			catch (IOException e) 
			{
				ChatGUI.writeToScreen("Server is down. Please try again after some time.");
			}
		} 
		catch (UnknownHostException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
	}

}
