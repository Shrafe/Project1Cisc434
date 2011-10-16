package rmi.client;

import java.rmi.registry.*;
import java.util.ArrayList;

import rmi.server.RmiServer;

public class RmiClientThread extends Thread {
	public double[] array;
	public int threadNum;

	public RmiClientThread(int tn){
		this.threadNum = tn;
	}
	
	public void run(){
		try {
			long totalTime = 0;
			long timeTaken = 0;
			long startTime = 0;
			String name = "RmiServer";
			Registry reg = LocateRegistry.getRegistry("localhost");
			RmiServer server = (RmiServer) reg.lookup(name);
			
			for (int i = 1; i < 11; i++){
				try{
					ArrayList<double[]> payload = genPayload();
					System.out.println("Thread:"+threadNum+": Starting run ["+i+"]");
					startTime = System.currentTimeMillis();
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
