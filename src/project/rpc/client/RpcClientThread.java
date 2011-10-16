package rpc.client;

import java.net.*;
import java.util.*;
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.*;

public class RpcClientThread extends Thread{
	int threadNum;
	double[] array;
	
	public RpcClientThread(int tn){
		this.threadNum = tn;
	}
		
	public void run(){
		long totalTime = 0;
		long timeTaken = 0;
		long startTime = 0;
		try{
			array = null;
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(new URL("http://127.0.0.1:4444/xmlrpc"));
			config.setEnabledForExtensions(true); // allow serializable objects to be used
			config.setConnectionTimeout(60000); // timeout of 1 min
			config.setReplyTimeout(60000); // 1 min timeout on replies too
			
			XmlRpcClient client = new XmlRpcClient();
			client.setConfig(config);
			
			for (int i = 1; i < 11; i++){
				try{ 
					ArrayList<double[]> payload = genPayload();
					System.out.println("Thread:"+threadNum+": Starting run ["+i+"]");
					startTime = System.currentTimeMillis();//starting timer after creation of payload.
					array = (double[]) client.execute("Worker.getAverage", payload);
					timeTaken = System.currentTimeMillis() - startTime;
					totalTime += timeTaken;
					System.out.println("Thread:"+threadNum+": Received array in: "+timeTaken+"ms");
				}catch(Exception e){ e.printStackTrace();}
			}
			System.out.println("Thread:"+threadNum+": Complete in: "+totalTime+"ms | Average call time:"+totalTime/10+"ms");
		}catch (Exception e){e.printStackTrace();}
	}
	
	public static ArrayList<double[]> genPayload(){
		int size=5;
	
		ArrayList<double[]> payload = new ArrayList<double[]>(size);
			for (int i=0; i<size; i++){
				payload.add(genArray());
			}
			return payload;
		
	}
	
	public static double[] genArray(){
		double [] arr = new double[1000];
		for (int i = 0; i < arr.length; i++){
			arr[i]=Math.random()*10;
		}
		return arr;
	}
	
	
	
}
