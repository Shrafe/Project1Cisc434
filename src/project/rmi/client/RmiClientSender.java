package rmi.client;

import java.util.ArrayList;
import rmi.server.RmiServer;

public class RmiClientSender extends Thread{
	public RmiServer server;
	public ArrayList<double[]>payload;
	public int clientNum;
	public int threadNum;
	
	public RmiClientSender(RmiServer server, ArrayList<double[]>payload, int tn, int cn){
		this.server = server;
		this.payload = payload;
		this.clientNum = cn;
		this.threadNum = tn;
	}
	
	public void run(){
		long startTime = 0;
		long timeTaken = 0;
		long totalTime = 0;
		try{
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Sending payload ...");
			startTime = System.currentTimeMillis(); // measuring only the time taken to transfer
			server.getAverage(payload);
			timeTaken = System.currentTimeMillis() - startTime;
			totalTime+=timeTaken;
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Received result in: "+timeTaken+"ms");
		}catch (Exception e){e.printStackTrace();}		
	}
}
