package corba.client;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import corba.server.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

class CorbaClientThread extends Thread {
	private int clientNum;
	private int scenario;

	
	public CorbaClientThread(int cn, int scenario){
		this.clientNum = cn;
		this.scenario = scenario;
	}
	
	public void run(){
		int size;
		long startTime = 0;
		if (scenario == 2)
			size = 10;
		else
			size = 1;
		
		ExecutorService es = Executors.newFixedThreadPool(size); // controls the thread pool. allows us to return values from threads; very useful!
		Set<Future<double[]>> resultSet = new HashSet<Future<double[]>>(); // the "Future" class, blocks on the completion of the thread

		try{
			startTime = System.currentTimeMillis();
			for (int i = 0; i < size; i++){
				try{
					Callable<double[]> sender = new CorbaClientSender(genPayload(),i,clientNum);
					Future<double[]> future = es.submit(sender);
					resultSet.add(future);
				} catch (Exception e){e.printStackTrace();}
			}
			for (Future<double[]> result : resultSet){
				result.get();
			}
			System.out.println("Client:"+clientNum+": Received all results in: "+(System.currentTimeMillis()-startTime)+"ms");
		} catch (Exception e){e.printStackTrace();}
		es.shutdown();
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

