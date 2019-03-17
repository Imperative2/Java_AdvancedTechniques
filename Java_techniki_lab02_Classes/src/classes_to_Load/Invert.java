package classes_to_Load;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


public class Invert implements ClassStructure
{
	private ServerSocket serverSocket;
	private Socket socketOut;
	private PrintWriter out;
	private BufferedReader sc;
	private Socket socketIn;
	
	private String word = null;
	
	private int portnum;
	
	
	public Invert()
	{
		
	}

	@Override
	public void setInput(int port)
	{
		portnum = port;
		try
		{
			serverSocket = new ServerSocket(port);
			System.out.println("Invert running on port : "+port);
			MyThread myThread = new MyThread();
			myThread.start();
//			socketIn = serverSocket.accept();
//			sc = new Scanner(socketIn.getInputStream());
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void setOutput(String host, int port)
	{
		try
		{
			System.out.println("We made it to socket");
			socketOut = new Socket(host, port);
			System.out.println("Created socket on :"+host+"  "+port);
			out = new PrintWriter(socketOut.getOutputStream(),true);
		}
		catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@Override
	public void start(boolean b)
	{
	
		try
		{
			Thread.sleep(1000);
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("waiting2");
		if(sc == null)
			System.out.println("no jest null"+sc);
		else
			System.out.println("sc is not null");
		
		Thread thread = new MyThread2();
		thread.start();

		

	//	out.println(word);
	}

	@Override
	public void stop(boolean b)
	{

		
	}
	
	private String invertString(String str)
	{
		char[] chars = str.toCharArray();
		String result="";
		for(int i=chars.length-1; i>=0; i--)
		{
			result+=chars[i];
		}
		return result;
	}
	

	
	
	class MyThread extends Thread
	{
		@Override
		public void run()
		{
			try
			{
					
				System.out.println("We are in sockt Thread");
				//System.out.println(serverSocket.isBound()+"sdfsdffd");
					socketIn = serverSocket.accept();
					System.out.println("waiting");
					sc = new BufferedReader(new InputStreamReader(socketIn.getInputStream()));
					if(sc == null )
						System.out.println("dupa scaner");
					System.out.println(sc);
				
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		}
	}
	
	class MyThread2 extends Thread
	{
		@Override
		public void run()
		{
			System.out.println("waiting3");
			try
			{
				while(true)
				{
					System.out.println(this.getName());
					if(sc.ready() == false)
						{
						Thread.sleep(100);
						continue;
						}
					word = sc.readLine();
					System.out.println("waiting4");
					System.out.println("my word INV: "+word);
					word = invertString(word);
					System.out.println("after INV: "+word);

					System.out.println("waiting5");
					out.println(word);
					out.flush();
					break;
				}

			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (InterruptedException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	
		}
	}

}
