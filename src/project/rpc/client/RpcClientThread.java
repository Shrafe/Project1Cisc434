package rpc.client;

import java.net.*;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.xmlrpc.client.*;

public class RpcClientThread extends Thread{
	private int clientNum;
	private URL serverUrl = null; // url is in the form http://<hostname>:<port>
	private int scenario; 

	public RpcClientThread(int cn, String url, int scenario){
		this.clientNum = cn;
		try {
			this.serverUrl = new URL(url);
		} catch (Exception e) {
			System.out.println("Error in RPC Client: [" + cn +"]\n");
			e.printStackTrace();
		}
		this.scenario = scenario;
	}
		
	public void run(){
		int size;
		long startTime = 0;
		if(scenario == 2)
			size = 10;
		else 
			size = 1;
		
		ExecutorService es = Executors.newFixedThreadPool(size); // controls the thread pool. allows us to return values from threads; very useful!
		Set<Future<double[]>> resultSet = new HashSet<Future<double[]>>(); // the "Future" class, blocks on the completion of the thread
					
		try{
			// create a new configuration object that allows us to set the required characteristics of our client
			XmlRpcClientConfigImpl config = new XmlRpcClientConfigImpl();
			config.setServerURL(serverUrl); // the server to connect to
			config.setEnabledForExtensions(true); // allow serializable objects to be used
			config.setConnectionTimeout(60000); // timeout of 1 min
			config.setReplyTimeout(60000); // 1 min timeout on replies too
			
			// create the new client
			XmlRpcClient server = new XmlRpcClient();
			server.setConfig(config);
			startTime = System.currentTimeMillis();
			for (int i = 0; i < size; i++){
				try{ 
					Callable<double[]> sender = new RpcClientSender(server,genPayload(),i,clientNum);
					Future<double[]> future = es.submit(sender);
					resultSet.add(future);
				}catch(Exception e){ e.printStackTrace();}
			}
			for (Future<double[]> result : resultSet){
				result.get();
			}
			System.out.println("Client:"+clientNum+": Received all results in: "+(System.currentTimeMillis()-startTime)+"ms");
		}catch (Exception e){e.printStackTrace();}
		//done
		es.shutdown();
	}
	
	public ArrayList<double[]> genPayload(){
		int size=5;
	
		ArrayList<double[]> payload = new ArrayList<double[]>(size);
			for (int i=0; i<size; i++){
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
