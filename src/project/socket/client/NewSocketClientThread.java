package socket.client;

import java.io.*;
import java.util.ArrayList;

public class NewSocketClientThread implements Runnable {
	
	private ArrayList<double[]> payload;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private double[] array;
	private String threadName;
	private int clientNum;
	private boolean locked = true;
	
	public NewSocketClientThread(ArrayList<double[]> payload,
			ObjectOutputStream oos,	ObjectInputStream ois,
			int threadNum, int clientNum) {
		
		this.payload = payload;
		this.oos = oos;
		this.ois = ois;
		this.threadName = "client" + clientNum + "thread" + threadNum;
	}
	
	public void run() {
		
		System.out.println("Running thread: " + threadName);
		passData();
	}
	
	public synchronized void passData() {
		
		String tName = Thread.currentThread().getName();
		
		//if (threadName == null) {
		//	threadName = tName;
		//	return;
		//}
		
		while (locked) {
			
			if (tName.compareTo(threadName) == 0) {
				try {
					
					// Write the payload
					oos.writeObject(payload);
					
					// Read the returned array
					array = (double[])ois.readObject();
				} catch (Exception e) {
					e.printStackTrace();
				}
				locked = false;
				notifyAll();
				threadName = null;
			}
			else {
				try {
					long t1 = System.currentTimeMillis();
					wait(20);
					if ((System.currentTimeMillis() - t1) > 20) {
						System.out.println("****** TIMEOUT! "+tName+
						" is waiting for thread: "+threadName);
						}
					tName = Thread.currentThread().getName();
					} catch (InterruptedException e) { }
			}
		}
		
		
		
	}
}
