package socket.client;

import java.util.*;

/**
 * Thread that writes to the output stream of the client's socket. Doesn't wait for 
 * a response to this particular request. just ends after sending.
 */

public class SocketClientSender implements Runnable {
	private SynchedStreams streams;
	private int threadNum;
	private int clientNum;
	private ArrayList<double[]>payload; 

	/**
	 * Basic constructor for a SocketClient thread
	 * 
	 * @param s Socket object. Will usually start around port 4444. Host will change.
	 * @param tn Current thread number
	 */
	public SocketClientSender(SynchedStreams ss, ArrayList<double[]> payload, int tn, int cn){
		this.streams = ss;
		this.payload = payload;
		this.threadNum = tn;
		this.clientNum = cn;
	}

	/**
	 * Sends a batch of data. 
	 */
	public void run(){
		long startTime = 0, timeTaken=0; // Performance metrics

		try{
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Sending payload ...");
			streams.send(payload);
			timeTaken = System.currentTimeMillis() - startTime;
		}catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
	}
}
