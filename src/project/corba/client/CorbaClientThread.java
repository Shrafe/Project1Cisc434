package corba.client;

import corba.server.*;
import org.omg.CosNaming.*;
import org.omg.CORBA.*;

class CorbaClientThread extends Thread {
	private double[] array;
	private int threadNum;
	
	public CorbaClientThread(int tn){
		this.threadNum = tn;
	}
	
	public void run(){
		long totalTime = 0;
		long timeTaken = 0;
		long startTime = 0;
		try{
			for (int i = 1; i < 11; i++){
				try{
					Payload payload = genPayload();
					System.out.println("Thread:"+threadNum+": Starting run ["+i+"]");
					startTime = System.currentTimeMillis();
					array = CorbaClient.corbaImpl.getAverage(payload);
					timeTaken = System.currentTimeMillis() - startTime;
					totalTime+=timeTaken;
					System.out.println("Thread:"+threadNum+": Received array in: "+timeTaken+"ms");
				} catch (Exception e){e.printStackTrace();}
			}
			System.out.println("Thread:"+threadNum+": Complete in: "+totalTime+"ms | Average call time:"+totalTime/10+"ms");
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

