package socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

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
		
		try {
			while ((arrays = (ArrayList<double[]>) ois.readObject()) != null){
			try {
				try{
					
				}catch (Exception e){ 
					e.printStackTrace();
					try{
						Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}	
				}

				greatestArray = getAverage(arrays);
				oos.writeObject(greatestArray);
				oos.flush();
				
				}catch (Exception e){
					e.printStackTrace();
					try{
						Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}
				}
				count++;
				greatestArray = null;
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
