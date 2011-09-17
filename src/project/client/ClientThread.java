package project.client;

import java.net.*;
import java.util.*;
import java.io.*;

// thread to do the actual sending of a payload to the server
// it waits for a response for it's payload, and quits when it's done

public class ClientThread extends Thread {
	public Socket socket;
	public ClientThread(Socket s){
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
				oos.writeObject(genPayload());
				double[] array = (double[])ois.readObject();
				System.out.println(array);	
			} catch (Exception e){}
		}
		try{
			oos.close();
			ois.close();
			socket.close();
		}catch (Exception e){}
		
	}

	public ArrayList<double[]> genPayload(){
		ArrayList<double[]> payload = new ArrayList<double[]>(5);
		for (int i=0; i<5; i++){
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
