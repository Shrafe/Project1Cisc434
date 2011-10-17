package rmi.client;

import java.rmi.registry.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import rmi.server.RmiServer;

public class RmiClientThread extends Thread {
	private int clientNum;
	private String hostname;
	private int scenario;
	
	
	public RmiClientThread(int cn, String shn, int scenario){
		this.clientNum = cn;
		this.hostname = shn;
		this.scenario = scenario;
	}
	
	public void run(){
		int size; 
		long startTime = 0;
		if (scenario == 2)
			size = 10;
		else
			size = 1;
		
		ExecutorService es = Executors.newFixedThreadPool(size); // controls the thread pool. allows us to return values from threads; very useful!
		Set<Future<double[]>> resultSet = new HashSet<Future<double[]>>(); // the "Future" class, blocks on the completion of the thread
		
		try {
			String name = "RmiServer"; // the name of the interface we're trying to call the implementation for
			Registry reg = LocateRegistry.getRegistry(hostname); // get the registry that contains the reference to our RmiServerImpl, located at serverHostname
			RmiServer server = (RmiServer) reg.lookup(name); // retrieve our RmiServer reference from the registry
			startTime = System.currentTimeMillis(); // need better way to time this
			for (int i = 0; i < size; i++){
				try{
					Callable<double[]>sender = new RmiClientSender(server, genPayload(), i, clientNum);
					Future<double[]> future = es.submit(sender);
					resultSet.add(future);
				} catch (Exception e){ e.printStackTrace();}
			}
			for (Future<double[]> result : resultSet){
				result.get();
			}
			System.out.println("Client:"+clientNum+": Received all results in: "+(System.currentTimeMillis()-startTime)+"ms");
		} catch (Exception e){e.printStackTrace();}
	}
	
	public ArrayList<double[]> genPayload(){
		int size=5;
		ArrayList<double[]> payload = new ArrayList<double[]>(size);
			for (int i=0; i<size; i++){
				payload.add(genArray());
			}
			return payload;
	}
	
	public double[] genArray(){
		double [] arr = new double[1000];
		for (int i = 0; i < arr.length; i++){
			arr[i]=Math.random()*10;
		}
		return arr;
	}
	
	
}
