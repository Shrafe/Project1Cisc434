package corba.client;

import corba.server.*;

import org.omg.CosNaming.*;
import org.omg.CORBA.*;

class CorbaClientThread extends Thread {
	public double[] array;
	public long time;
	static Corba corbaImpl;
	
	public void run(String[] args){
		try{
			ORB orb = ORB.init(args,null);
			
			org.omg.CORBA.Object objectReference = orb.resolve_initial_references("NameService");
			NamingContextExt namingContextReference = NamingContextExtHelper.narrow(objectReference);
			
			String name = "getAverage";
			corbaImpl = CorbaHelper.narrow(namingContextReference.resolve_str(name));
			System.out.println("Obtained a handle on server object: " + corbaImpl);
			
			for (int i = 0; i < 10; i++){
				try{
					Payload payload = genPayload();
					System.out.println("Sending...");
					time = System.currentTimeMillis();
					array = corbaImpl.getAverage(payload);
					System.out.println("Done : "+(System.currentTimeMillis()-time)+"ms");
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

