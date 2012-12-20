package skn.rhoadster.fileuploader;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import skn.rhoadster.chatclient.Message;

public class UploaderMain
{
	static String name, roomname, filepath;
	static File myFile;
	static ObjectInputStream objIn;
	static ObjectOutputStream objOut;
	static String IP_ADDRESS;
	
	public static void main(String[] args)
	{		
		IP_ADDRESS = new String(args[0]);		
		System.out.println("Enter your name: ");
		Scanner sc = new Scanner(System.in);
		name = sc.nextLine();
		System.out.println("Enter target room name: ");
		sc = new Scanner(System.in);
		roomname = sc.nextLine();
		System.out.println("Enter file path: ");
		sc = new Scanner(System.in);
		filepath = sc.nextLine();
		myFile = new File(filepath);
		try
		{
			Socket socket = new Socket(IP_ADDRESS, 9899);
			objIn = new ObjectInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());
			Message msg = new Message();
			msg.sender = name;
			msg.room = roomname;				
			objOut.writeObject(msg);
			objOut.flush();
			objOut.writeObject(myFile.getName());
			objOut.flush();
			System.out.println("Reading file...");
			int fileSize = (int)myFile.length();
			byte []buffer = new byte[fileSize];			
			BufferedInputStream bis = new BufferedInputStream(new FileInputStream(myFile));
			bis.read(buffer, 0, buffer.length);
			System.out.println("Uploading in progress...");
			objOut.writeObject(buffer);
			objOut.flush();	
			System.out.println("Waiting for confirmation...");
			String str = (String)objIn.readObject();
			if(str.contentEquals("Confirmed"))
				System.out.println("Upload successful.");
			
		} 
		catch (UnknownHostException e)		{
			
			e.printStackTrace();
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
