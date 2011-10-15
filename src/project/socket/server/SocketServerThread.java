package socket.server;

import java.net.*;
import java.util.*;
import java.io.*;

public class SocketServerThread extends Thread {
	private Socket s = null;
	long time;
	
	public SocketServerThread(Socket s){
		super("SocketServerThread");
		this.s = s;
		time = System.currentTimeMillis();
		
	}
	
	public void run(){
		int count = 0;
		// assume we can get the arrays as is, because they are indeed serializable
		// each client is going to give us 5 arrays in a "packet" 
		ArrayList<double[]> arrays = null; // so we know which array to send back
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;
		double[] greatestArray = null; // the greatest array 
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
			greatestArray = getAverage(arrays);
			oos.writeObject(greatestArray);	
			
			}catch (IOException e){}
			count++;
			greatestArray = null;
		}
		try{
			oos.close();
			ois.close();
			s.close();
		} catch (Exception e){}
	}
	
	public double[] getAverage(ArrayList<double[]>payload){
		double currentGreatest = 0;
		double[] greatestArray = null;
		for (double[] array : payload){
			double average = getAverageArray(array);
			if (average > currentGreatest){
				currentGreatest = average;
				greatestArray = array;
			}
		}
		return greatestArray;
	}
	
	public double getAverageArray(double[] arr){
		double average = 0;
		double sum = 0;
		for (int i=0; i<arr.length; i++){
			sum += arr[i];
		}
		average = sum / 1000;
		return average;
	}
}
