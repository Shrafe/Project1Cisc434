package corba.client;

import java.util.concurrent.Callable;

import corba.server.Corba;
import corba.server.Payload;

public class CorbaClientSender implements Callable<double[]> {
	private Payload payload;
	private int clientNum;
	private int threadNum;
	private Corba corbaImpl;
	
	
	public CorbaClientSender(Payload payload, Corba corba, int tn, int cn){
		this.payload = payload;
		this.clientNum = cn;
		this.threadNum = tn;	
		this.corbaImpl = corba;
	}
	
	public double[] call(){
		long startTime = 0;
		long timeTaken = 0;
		double[] result = null;
		try{ 
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Sending payload ...");
			startTime = System.currentTimeMillis();
			corbaImpl.getAverage(payload);
			timeTaken = System.currentTimeMillis() - startTime;
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Received result in: "+timeTaken+"ms");
		}catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
		return result;
	}
}
