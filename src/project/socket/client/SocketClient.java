package socket.client;
// YO
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class defining the client operations on the socket. Creates a thread for each payload sent, 
 * with options for sending either 1 payload, or 10 payloads concurrently
 * @author TomW7
 *
 */

public class SocketClient {
	public Socket socket;
	public int clientNum;
	public int scenario;
	public double[] array; 
	
	public SocketClient(Socket s, int cn, int scenario){
		this.socket = s; // this client's socket connection
		this.clientNum = cn; // client identification number
		this.scenario = scenario; // either 1 or 2
	}
	/**
	 * Entry point into the client. Starts the client with specified settings
	 * 
	 * @param args : array with the following elements 
	 * @param args[0] : scenario number
	 * @param args[1] : port number
	 * @param args[2] : hostname
	 * @param args[3] : clientNumber
	 * @throws IOException
	 * @throws ClassNotFoundException
	 */
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		int scenario = Integer.parseInt(args[0]);
		int port = Integer.parseInt(args[1]);
		String hostname = args[2];
		int clientNum = Integer.parseInt(args[3]);
		Socket socket = new Socket(hostname, port);
		SocketClient client = new SocketClient(socket, clientNum, scenario); 
		System.out.println("Running Scenario: "+scenario+" on hostname: "+hostname+" on port: "+port);
		client.execute();
	}
	
	/**
	 * Executes the client. 
	 */
	public void execute(){
		long totalTime = 0, timeTaken = 0, startTime = 0; // Performance metrics
		int limit;
		
		if (scenario == 1) {
			limit = 1;
		}
		else {
			limit = 10; // scenario 2, we send 10 concurrent requests
		}
		
		ExecutorService es = Executors.newFixedThreadPool(limit); // create our thread executor.
		Set<Runnable> senderSet = new HashSet<Runnable>(); // the set of requests we want to run
		ObjectOutputStream oos = null; // streams
		ObjectInputStream ois = null; 
		SynchedStreams ss = null; // Synchronized stream for writing. 
		try{ 
			oos = new ObjectOutputStream(socket.getOutputStream()); // create streams
			ois = new ObjectInputStream(socket.getInputStream());
			ss = new SynchedStreams(oos); // synchronize on the outputstream
		}catch (Exception e){e.printStackTrace();}
		
		try{		
			for (int i = 0; i < limit; i++){
				try{
					Runnable sender = new SocketClientSender(ss, genPayload(), i, clientNum); // create as many senders as we need
					senderSet.add(sender); //add them to the set of Senders
				}catch (Exception e){
					e.printStackTrace();
					try{
						Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}}
			}
			// Start the performance timer
			startTime = System.currentTimeMillis();
			// Send the data
			for (Runnable sender : senderSet){
				es.submit(sender); //execute all of the senders
			}
			// we know we will only get 10 results back.
			for (int i = 0; i<limit; i++){
				ois.readObject(); // get a response from the server 
				System.out.println("Received response");				
			}
			timeTaken = System.currentTimeMillis() - startTime; // this is the time taken.
		} catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
		try{
			System.out.println("Client:"+clientNum+": Complete in "+ timeTaken +"ms");
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
