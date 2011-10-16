package rmi.client;

import java.rmi.registry.*;
import java.util.ArrayList;

import rmi.server.RmiServer;

public class RmiClientThread extends Thread {
	private double[] array;
	private int threadNum;
	private String hostname;
	
	
	public RmiClientThread(int tn, String shn){
		this.threadNum = tn;
		this.hostname = shn;
	}
	
	public void run(){
		try {
			long totalTime = 0;
			long timeTaken = 0;
			long startTime = 0;
			String name = "RmiServer"; // the name of the interface we're trying to call the implementation for
			// TODO: Maybe we need to use a different getRegistry method, Bryce, if you can't host the registry on port 1099
			Registry reg = LocateRegistry.getRegistry(hostname); // get the registry that contains the reference to our RmiServerImpl, located at serverHostname
			RmiServer server = (RmiServer) reg.lookup(name); // retrieve our RmiServer reference from the registry
			
			for (int i = 1; i < 11; i++){
				try{
					ArrayList<double[]> payload = genPayload();
					System.out.println("Thread:"+threadNum+": Starting run ["+i+"]");
					startTime = System.currentTimeMillis(); // measuring only the time taken to transfer
					array = server.getAverage(payload);
					timeTaken = System.currentTimeMillis() - startTime;
					totalTime+=timeTaken;
					System.out.println("Thread:"+threadNum+": Received array in: "+timeTaken+"ms");
				} catch (Exception e){ e.printStackTrace();}
			}
			System.out.println("Thread:"+threadNum+": Complete in: "+totalTime+"ms | Average call time:"+totalTime/10+"ms");
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
