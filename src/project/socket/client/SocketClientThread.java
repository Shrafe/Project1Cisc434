package socket.client;

import java.net.*;
import java.util.*;
import java.io.*;

// thread to do the actual sending of a payload to the server
// it waits for a response for it's payload, and quits when it's done

public class SocketClientThread extends Thread {
	public Socket socket;
	public long time;
	public double[] array; 

	public SocketClientThread(Socket s){
		this.socket = s;
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
				ArrayList<double[]> payload = genPayload();
				System.out.println("Sending...");
				time = System.currentTimeMillis();//starting timer after creation of payload.
				oos.writeObject(payload);
				array = (double[])ois.readObject();
				System.out.println("Done : "+(System.currentTimeMillis()-time)+"ms");
					
			} catch (Exception e){ e.printStackTrace();}
		}
		try{
			oos.close();
			ois.close();
			socket.close();
		}catch (Exception e){}
		
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
