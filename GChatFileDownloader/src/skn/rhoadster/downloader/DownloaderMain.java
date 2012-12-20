package skn.rhoadster.downloader;


import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

public class DownloaderMain
{
	static String name, roomname, filepath;
	static File myFile;
	static ObjectInputStream objIn;
	static ObjectOutputStream objOut;
	static String IP_ADDRESS;
	
	public static void main(String []args)
	{
		IP_ADDRESS = new String(args[0]);		
		System.out.println("Enter file name to download from server: ");
		Scanner sc = new Scanner(System.in);
		String fileName = sc.nextLine();
		try
		{
			Socket socket = new Socket(IP_ADDRESS, 9897);
			objIn = new ObjectInputStream(socket.getInputStream());
			objOut = new ObjectOutputStream(socket.getOutputStream());
			objOut.writeObject(fileName);
			objOut.flush();
			System.out.println("Receiving file...");
			byte []buffer = (byte[]) objIn.readObject();			
			System.out.println("Received file... Now writing to disk.");
			System.out.println("Enter path where you want to save the file: ");
			sc = new Scanner(System.in);
			String myFileName = sc.nextLine();
			File myFile = new File(myFileName);
			myFile.createNewFile();
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(myFile));
			bos.write(buffer);
			bos.close();
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
