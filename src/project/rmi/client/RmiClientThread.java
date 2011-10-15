package rmi.client;

import java.rmi.registry.*;
import java.util.ArrayList;

import rmi.server.RmiServer;

public class RmiClientThread extends Thread {
	public double[] array;
	public long time;
	
	public void run(){
		try {
			String name = "RmiServer";
			Registry reg = LocateRegistry.getRegistry("localhost");
			RmiServer server = (RmiServer) reg.lookup(name);
			
			for (int i = 0; i < 10; i++){
				try{
					ArrayList<double[]> payload = genPayload();
					System.out.println("Sending...");
					time = System.currentTimeMillis();
					array = server.getAverage(payload);
					System.out.println("Done : "+(System.currentTimeMillis()-time)+"ms");
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
