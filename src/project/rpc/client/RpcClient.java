package rpc.client;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.xmlrpc.client.XmlRpcClient;
import org.apache.xmlrpc.client.XmlRpcClientConfigImpl;

/**
 * Class defining the behavior of the Client for RPC technology.
 * @author TomW7
 *
 */

public class RpcClient {
	private int clientNum;
	private URL serverUrl = null; // url is in the form http://<hostname>:<port>
	private int scenario; 

	public RpcClient(int cn, String url, int scenario){
		this.clientNum = cn;
		try {
			this.serverUrl = new URL(url);
		} catch (Exception e) {
			System.out.println("Error in RPC Client: [" + cn +"]\n");
			e.printStackTrace();
			try{
				Thread.sleep(10000);
				}catch(Exception ex){ex.printStackTrace();}
			System.exit(1);
		}
		this.scenario = scenario;
	}

	/**
	 * Entry point into the Client. Gathers parameters and creates a new RpcClient
	 * according to the parameters
	 * @param args
	 * @param args[0] : scenario number
	 * @param args[1] : hostname
	 * @param args[2] : port number
	 * @param args[3] : client identification number
	 * 
	 */
	
	public static void main(String[] args) {
		int scenario = Integer.parseInt(args[0]);
		String hostname = args[1];
		int port = Integer.parseInt(args[2]);
		int clientNum = Integer.parseInt(args[3]);
		String serverUrl = hostname+":"+port+"/xmlrpc";
		RpcClient client = new RpcClient(clientNum, serverUrl, scenario);
		System.out.println("Running Scenario: "+scenario+" on server:" +serverUrl);
		client.execute();		
	}
	
	public void execute(){
		int size;
		long startTime = 0;
		if(scenario == 2)
			size = 10;
		else 
			size = 1;
		
		ExecutorService es = Executors.newFixedThreadPool(size); // controls the thread pool. allows us to return values from threads; very useful!
		Set<Callable<double[]>> senderSet = new HashSet<Callable<double[]>>();
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

			for (int i = 0; i < size; i++){
				try{ 
					Callable<double[]> sender = new RpcClientSender(server,genPayload(),i,clientNum);
					senderSet.add(sender);
				}catch(Exception e){ 
					e.printStackTrace();
					try{
					Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}}
			}
			startTime = System.currentTimeMillis();
			for (Callable<double[]> sender : senderSet){
				Future<double[]> future = es.submit(sender);
				resultSet.add(future);
			}
			for (Future<double[]> result : resultSet){
				result.get();
			}
			System.out.println("Client:"+clientNum+": Received all results in: "+(System.currentTimeMillis()-startTime)+"ms");
		}catch (Exception e){e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
		//done
		es.shutdown();
		try{
		Thread.sleep(100000); //done. sleep for a long enough time to 
		}catch(Exception ex){ex.printStackTrace();}
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

