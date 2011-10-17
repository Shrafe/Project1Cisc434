package corba.client;

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
		if (scenario == 2)
			size = 10;
		else
			size = 1;

		try{
			for (int i = 0; i < size; i++){
				try{
					new CorbaClientSender(genPayload(), clientNum, i).start();
				} catch (Exception e){e.printStackTrace();}
			}
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

