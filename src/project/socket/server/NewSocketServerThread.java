package socket.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class NewSocketServerThread extends Thread{

	private Socket socket;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private long time;
	private int limit;
	
	public NewSocketServerThread(Socket socket){
		
		super("NewSocketServerThread");
		this.socket = socket;
		time = System.currentTimeMillis();	
	}
	
	public void run() {
		
		ArrayList<double[]> arrays = null; // so we know which array to send back
		double[] greatestArray = null; // the greatest array
		int count = 0;
		
		// Set up the data streams and read in the number of threads to expect
		try{
			oos = new ObjectOutputStream(socket.getOutputStream()); // output to the socket
			ois = new ObjectInputStream(socket.getInputStream()); // read from the socket
			
			limit = ois.readInt();
			
		}catch (Exception e){
			e.printStackTrace();
		}
		
		while (count < limit) {
			try {
				
				arrays = (ArrayList<double[]>)ois.readObject();
				System.out.println("Received an Array");
				
				greatestArray = getAverage(arrays);
				oos.writeObject(greatestArray);
				System.out.println("Sent an Array");
				
				count++;
				greatestArray = null;
			}catch (Exception e) {
				e.printStackTrace();
				System.exit(-1);
			}
		}
		
		
		try {
			oos.close();
			ois.close();
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
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
