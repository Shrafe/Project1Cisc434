package rpc.client;

import java.util.ArrayList;
import java.util.concurrent.Callable;

import org.apache.xmlrpc.client.XmlRpcClient;

public class RpcClientSender implements Callable<double[]>{
	public XmlRpcClient server;
	public ArrayList<double[]> payload;
	public int clientNum;
	public int threadNum;
	
	public RpcClientSender(XmlRpcClient server, ArrayList<double[]> payload, int tn, int cn){
		this.server = server;
		this.payload = payload;
		this.clientNum = cn;
		this.threadNum = tn;
	}
	
	public double[] call(){
		long startTime = 0;
		long timeTaken = 0;
		long totalTime = 0;
		double[] result = null;
		try{			
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Sending payload ...");
			startTime = System.currentTimeMillis();//starting timer after creation of payload.
			result = (double[])server.execute("Worker.getAverage", payload);
			timeTaken = System.currentTimeMillis() - startTime;
			totalTime += timeTaken;
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Received result in: "+timeTaken+"ms");
		}catch (Exception e){e.printStackTrace();try{Thread.sleep(10000);}catch(Exception ex){ex.printStackTrace();}}
		return result;
	}
}
