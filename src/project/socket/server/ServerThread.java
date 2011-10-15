package socket.server;

import java.net.*;
import java.util.*;
import java.io.*;

public class ServerThread extends Thread {
	private Socket s = null;
	long time;
	
	public ServerThread(Socket s){
		super("SocketServerThread");
		this.s = s;
		time = System.currentTimeMillis();
		
	}
	
	public void run(){
		int count = 0;
		// assume we can get the arrays as is, because they are indeed serializable
		// each client is going to give us 5 arrays in a "packet" 
		ArrayList<double[]> arrays = null; // so we know which array to send back
		double currentGreatest = 0; // the greatest average seen so far
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		double[] greatestArray = null; // the greatest array we've seen so far
		try{
			oos = new ObjectOutputStream(s.getOutputStream()); // output to the socket
			ois = new ObjectInputStream(s.getInputStream()); // read from the socket
		}catch (Exception e){}
		
		while (count < 10){
		try {
			try{
				arrays = (ArrayList<double[]>)ois.readObject();
			}catch (IOException e){}
			 catch (ClassNotFoundException e){}

			
			Iterator<double[]> aIt = arrays.iterator();
			
			while (aIt.hasNext()){
				double[] array = aIt.next();
				double average = getAverage(array);
				if (average > currentGreatest){
					currentGreatest = average;
					greatestArray = array;
				}
			}
			oos.writeObject(greatestArray);	
			
			}catch (IOException e){}
			count++;
			currentGreatest = -1;
			greatestArray = null;
		}
		try{
			oos.close();
			ois.close();
			s.close();
		} catch (Exception e){}
	}
	
	public double getAverage(double[] arr){
		double average = 0;
		double sum = 0;
		for (int i=0; i<arr.length; i++){
			sum += arr[i];
		}
		average = sum / 1000;
		return average;
	}
}
