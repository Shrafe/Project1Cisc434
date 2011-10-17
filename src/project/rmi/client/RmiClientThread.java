package rmi.client;

import java.rmi.registry.*;
import java.util.ArrayList;

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
		if (scenario == 2){
			size = 10;
		} else
			size = 1;
		try {
			String name = "RmiServer"; // the name of the interface we're trying to call the implementation for
			Registry reg = LocateRegistry.getRegistry(hostname); // get the registry that contains the reference to our RmiServerImpl, located at serverHostname
			RmiServer server = (RmiServer) reg.lookup(name); // retrieve our RmiServer reference from the registry
			
			for (int i = 0; i < size; i++){
				try{
					new RmiClientSender(server, genPayload(), i, clientNum).start();
				} catch (Exception e){ e.printStackTrace();}
			}
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
