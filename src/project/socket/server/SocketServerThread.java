package socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class SocketServerThread implements Runnable{
	private Socket s = null;
	private int threadNum;
		
	public SocketServerThread(Socket s, int threadNum){
		this.s = s;
		this.threadNum = threadNum;
	}
	
	/**
	 * This thread is dispatched for each request on the server. Waits on input to the OIS, 
	 * when received, finds the average array of the received ArrayList, and writes it to the 
	 * OutputStream of the associated client's socket.
	 */
	
	public void run(){
		int count = 0;
		ArrayList<double[]> payload = null; // the payload received from the client
		ObjectOutputStream oos = null; // the streams
		ObjectInputStream ois = null;
		double[] greatestArray = null; // the greatest array 
		try{
			oos = new ObjectOutputStream(s.getOutputStream()); // output to the socket
			ois = new ObjectInputStream(s.getInputStream()); // read from the socket
		}catch (Exception e){}
		
		try {
			while ((payload = (ArrayList<double[]>) ois.readObject()) != null){ // wait for the client to write something to the stream
				System.out.println("Server Thread:"+threadNum+": Received payload.");
			try {
				greatestArray = getAverage(payload); // get the array with the greatest average
				oos.writeObject(greatestArray); // write that array to the Client's inputStream
				oos.flush(); // flush the stream 
				
				}catch (Exception e){
					e.printStackTrace();
					try{
						Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}
				}
				count++;
				greatestArray = null;
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
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
