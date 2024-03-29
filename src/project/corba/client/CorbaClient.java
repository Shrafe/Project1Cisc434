package corba.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import corba.server.*;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class CorbaClient {
	private int clientNum;
	private int scenario;
	private Corba corbaImpl = null;
	private ORB orb;
	
	public CorbaClient(int cn, int scenario){
		this.clientNum = cn;
		this.scenario = scenario;
	}	

	public static void main(String[] args) {
		int scenario = Integer.parseInt(args[0]);
		int clientNum = Integer.parseInt(args[1]);
		String port = args[2];
		String hostname = args[3];
		System.setProperty("org.omg.CORBA.ORBInitialPort", port);
		System.setProperty("org.omg.CORBA.ORBInitialHost", hostname);
		CorbaClient client = new CorbaClient(clientNum, scenario);
		System.out.println("Running Scenario: "+scenario+" on CorbaServer");
		client.execute();
	}
		
	public void execute(){
		String[] ph = null;
		orb = ORB.init(ph,null);
		// setup the connection
		try {
			org.omg.CORBA.Object objectReference = orb.resolve_initial_references("NameService");
			NamingContextExt namingContextReference = NamingContextExtHelper.narrow(objectReference);
			corbaImpl = CorbaHelper.narrow(namingContextReference.resolve_str("getAverage"));
			System.out.println("Got handle on:" +corbaImpl);
		} catch (Exception e) {
			e.printStackTrace();
			try{
				Thread.sleep(10000); //allow us to see errors if they occur using batch script
			}catch(Exception ex){ex.printStackTrace();}
		}
		
		int size;
		long startTime = 0;
		
		if (scenario == 2)
			size = 10;
		else
			size = 1;
		
		ExecutorService es = Executors.newFixedThreadPool(size); // controls the thread pool. allows us to return values from threads; very useful!
		Set<Callable<double[]>> senderSet = new HashSet<Callable<double[]>>(); // set of Senders so we can start them all at closer to the same time
		Set<Future<double[]>> resultSet = new HashSet<Future<double[]>>(); // the "Future" class, blocks on the completion of the thread
	
		try{
			for (int i = 0; i < size; i++){ // create all of the sender objects
				try{
					Callable<double[]> sender = new CorbaClientSender(genPayload(),corbaImpl,i,clientNum);
					senderSet.add(sender);
				} catch (Exception e){
					e.printStackTrace();
					try{
						Thread.sleep(10000);
					}catch(Exception ex){ex.printStackTrace();}
				}
			}
			startTime = System.currentTimeMillis();
			for (Callable<double[]> sender : senderSet){
				Future<double[]> future = es.submit(sender);
				resultSet.add(future);
			}
			for (Future<double[]> result : resultSet){
				result.get();
			}
		} catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
				}catch(Exception ex){ex.printStackTrace();}
		}
		System.out.println("Client:"+clientNum+": Received all results in: "+(System.currentTimeMillis()-startTime)+"ms");
		es.shutdown();
		try{
			Thread.sleep(100000);
		} catch (Exception e){e.printStackTrace();}
	}
		
	public Payload genPayload(){
		Payload payload = new Payload();
		payload.setOne(genArray());
		payload.setTwo(genArray());
		payload.setThree(genArray());
		payload.setFour(genArray());
		payload.setFive(genArray());
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

