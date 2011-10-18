package socket.client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

public class NewSocketClient {
	
	private Socket socket;
	private ArrayList<double[]> payload;
	
	private int limit;
	private int size = 5;
	
	// The data streams
	private ObjectOutputStream oos;
	private	ObjectInputStream ois;
	
	public NewSocketClient(int scenario, int port,
			String hostname, int clientNum) {
		
		// Choose between 1 thread or 10 threads
		if (scenario == 1) { limit = 1; }
		else { limit = 10; }
		
		try {
			// Set up the socket connection and the data streams 
			socket = new Socket(hostname, port);
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
			
			// Let the server know how many threads are incoming
			oos.writeInt(limit);
		}catch (IOException e){
			e.printStackTrace();
			System.exit(-1);
		}
		
		// Create the list of threads we will make
		Thread[] threads = new Thread[limit];
		
		// Create a payload and attach it to a thread, but don't run it yet
		for (int i=0; i<limit; i++) {
			genPayload();
			threads[i] = new Thread(new NewSocketClientThread(payload, oos, ois, i, clientNum));
			threads[i].setName("client" + clientNum + "thread" + i);
		}
		
		// Start the Threads
		for (int i=0; i<limit; i++) {
			//System.out.println("Running Client: " + clientNum);
			threads[i].start();
		}
		
		// Wait for all Threads to finish
		for (int i=0; i<limit; i++) {
			try {
				threads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// Cleanup
		try {
		oos.close();
		ois.close();
		socket.close();
		}
		catch (Exception e){
			e.printStackTrace();
		}
	}
	
	public void genPayload() {
		
		payload = new ArrayList<double[]>(size);
		
		// Populate the ArrayList
		for (int i=0; i<size; i++){
			payload.add(genArray());
		}
	}
	
	public static double[] genArray(){
		double [] arr = new double[1000];
		
		// Random Number Generation
		for (int i = 0; i < arr.length; i++){
			arr[i]=Math.random()*10;
		}
		return arr;
	}
}
