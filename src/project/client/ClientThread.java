package client;

import java.net.*;
import java.util.*;
import java.io.*;

// thread to do the actual sending of a payload to the server
// it waits for a response for it's payload, and quits when it's done

public class ClientThread extends Thread {
	public Socket socket;
	public long time;
	public boolean scenario2;
	public ClientThread(Socket s, boolean scenario2){
		this.socket = s;
		this.scenario2 = scenario2;
		
	}

	public void run(){
		ObjectOutputStream oos=null;
		ObjectInputStream ois=null;
		try{
			oos = new ObjectOutputStream(socket.getOutputStream());
			ois = new ObjectInputStream(socket.getInputStream());
		}catch (IOException e){}
		
		for (int i = 0; i < 10; i++){
			try{
				ArrayList<double[]> payload = genPayload(scenario2);
				System.out.println("Sending...");
				time = System.currentTimeMillis();//starting timer after creation of payload.
				oos.writeObject(payload);
				double[] array = (double[])ois.readObject();
				System.out.println("Done : "+(System.currentTimeMillis()-time)+"ms");
					
			} catch (Exception e){ e.printStackTrace();}
		}
		try{
			oos.close();
			ois.close();
			socket.close();
		}catch (Exception e){}
		
	}

	public ArrayList<double[]> genPayload(boolean scenario2){
		int size=0;
		if (scenario2)
			size = 50;
		else
			size = 5;
	
		ArrayList<double[]> payload = new ArrayList<double[]>(5);
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
