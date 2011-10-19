package socket.client;
// YO
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


public class SocketClient {
	public Socket socket;
	public int clientNum;
	public int scenario;
	public double[] array; 
	
	public SocketClient(Socket s, int cn, int scenario){
		this.socket = s;
		this.clientNum = cn;
		this.scenario = scenario;
	}
	
	
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
	
	public void execute(){
		long totalTime = 0, timeTaken = 0, startTime = 0; // Performance metrics
		int limit;
		
		if (scenario == 1) {
			limit = 1;
		}
		else {
			limit = 10;
		}
		
		ExecutorService es = Executors.newFixedThreadPool(limit);
		Set<Callable<double[]>> senderSet = new HashSet<Callable<double[]>>();
		Set<Future<double[]>> resultSet = new HashSet<Future<double[]>>();
		
		try{
		// Start at thread 1 and finish on thread 10
			for (int i = 0; i < limit; i++){
				try{
					Callable<double[]> sender = new SocketClientSender(socket, genPayload(), i, clientNum);
					// Create the data we wish to send
					senderSet.add(sender);
				}catch (Exception e){
					e.printStackTrace();
					try{
						Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}}
				}
					// Start the performance timer
				startTime = System.currentTimeMillis();
					
					// Send the data
				for (Callable<double[]>sender : senderSet){
					Future<double[]> future = es.submit(sender);
					resultSet.add(future);
				}
				for (Future<double[]> result: resultSet){
					result.get();
				}
		} catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
		try{
			System.out.println("Client:"+clientNum+": Complete in: "+totalTime+"ms | Average call time:"+totalTime/10+"ms");
			try{
				Thread.sleep(100000);
			}catch(Exception ex){ex.printStackTrace();}
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
