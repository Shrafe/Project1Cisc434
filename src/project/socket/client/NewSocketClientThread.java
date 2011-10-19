package socket.client;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewSocketClientThread implements Runnable {
	
	private ArrayList<double[]> payload;
	private ObjectOutputStream oos;
	private ObjectInputStream ois;
	private double[] array;
	private String threadName;
	
	public NewSocketClientThread(ArrayList<double[]> payload,
			ObjectOutputStream oos,	ObjectInputStream ois,
			int threadNum, int clientNum) {
		
		this.payload = payload;
		this.oos = oos;
		this.ois = ois;
		this.threadName = "client" + clientNum + "thread" + threadNum;
	}
	
	public void run() {
		
		passData();
	}
	
	public synchronized void passData() {
		
		String tName = Thread.currentThread().getName();
		
		if (!tName.contains("thread0")) {
			try {
				wait();
			} catch (InterruptedException e) {}
			//tName = Thread.currentThread().getName();
		}
		
		try {
			// Write the payload
			oos.writeObject(payload);
			
			System.out.println("Running thread: " + threadName);
			
			// Read the returned array
			array = (double[])ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// Wake up ALL the threads!
		notifyAll();
	}
}
