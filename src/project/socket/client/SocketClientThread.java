package socket.client;

import java.net.*;
import java.util.*;
import java.io.*;

// thread to do the actual sending of a payload to the server
// it waits for a response for it's payload, and quits when it's done

public class SocketClientThread extends Thread {
	public Socket socket;
	public int clientNum;
	public int scenario;
	public double[] array; 

	/**
	 * Basic constructor for a SocketClient thread
	 * 
	 * @param s Socket object. Will usually start around port 4444. Host will change.
	 * @param tn Current thread number
	 */
	public SocketClientThread(Socket s, int tn, int scenario){
		this.socket = s;
		this.clientNum = tn;
		this.scenario = scenario;
	}

	/**
	 * Creates and sends a batch of data. Measures return time.
	 */
	public void run(){
		
		long totalTime = 0, timeTaken = 0, startTime = 0; // Performance metrics
		ObjectOutputStream oos=null;
		ObjectInputStream ois=null;
		try{
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		}catch (IOException e){}
		
		int limit;
		
		if (scenario == 1) {
			limit = 2;
		}
		else {
			limit = 11;
		}
		
		// Start at thread 1 and finish on thread 10
		for (int i = 1; i < limit; i++){
			try{
				// Create the data we wish to send
				ArrayList<double[]> payload = genPayload();
				System.out.println("Client:"+clientNum+": Starting thread ["+i+"]");
				
				// Start the performance timer
				startTime = System.currentTimeMillis();
				
				// Send the data
				oos.writeObject(payload);
				array = (double[])ois.readObject();
				
				// Stop the performance timer
				timeTaken = System.currentTimeMillis() - startTime;
				totalTime+=timeTaken;
				System.out.println("Client:"+clientNum+": Received array in: "+timeTaken+"ms");
				
			} catch (Exception e){ e.printStackTrace();}
		}
		try{
			System.out.println("Client:"+clientNum+": Complete in: "+totalTime+"ms | Average call time:"+totalTime/10+"ms");
			oos.close();
			ois.close();
			socket.close();
		}catch (Exception e){}
		
	}

	/**
	 * Creates the ArrayList of 5 arrays that will be sent to the server.
	 * 
	 * @return Returns the 5 arrays in one ArrayList
	 */
	public ArrayList<double[]> genPayload(){
		
		int size=5; // The chosen number of arrays per connection
		ArrayList<double[]> payload = new ArrayList<double[]>(size);
		
		// Populate the ArrayList
		for (int i=0; i<size; i++){
			payload.add(genArray());
		}
		return payload;
	}
	
	/**
	 * Creates the arrays for the ArrayList. Currently set to 1000 numbers per array
	 * 
	 * @return Returns an array of 1000 randomly generated doubles.
	 */
	public double[] genArray(){
		double [] arr = new double[1000];
		
		// RNG
		for (int i = 0; i < arr.length; i++){
			arr[i]=Math.random()*10;
		}
		return arr;
	}
	
}
