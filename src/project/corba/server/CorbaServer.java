package corba.server;

import org.omg.CosNaming.*;
import org.omg.CosNaming.NamingContextPackage.*;
import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import java.util.Properties;
import java.util.ArrayList;

class CorbaImpl extends CorbaPOA {
	private ORB orb;
	
	public void setORB(ORB orb){
		this.orb = orb;
	}
	// implementation
	public double[] getAverage(Payload p){
		ArrayList<double[]>payload = new ArrayList<double[]>();
		double currentGreatest = 0;
		double[] greatestArray = null;
		payload.add(p.arrayOne);
		payload.add(p.arrayTwo);
		payload.add(p.arrayThree);
		payload.add(p.arrayFour);
		payload.add(p.arrayFive);			
		for (double[] array : payload){
			double thisGreatest = getAverageArr(array);
			if (thisGreatest > currentGreatest){
				currentGreatest = thisGreatest;
				greatestArray = array;
			}
		}
		return greatestArray;			
	}
	//helper
	private double getAverageArr(double[] arr){ 
		double average = 0;
		double sum = 0;
		for (int i=0; i<arr.length; i++){
			sum += arr[i];
		}
		average = sum / 1000;
		return average;
	}
	
	public void shutdown(){
		this.orb.shutdown(false);
	}
}

public class CorbaServer {
	public static void main(String[]args){
		try{
			Runtime.getRuntime().exec("cmd /c start src/project/corba/server/orbd.exe"); // start orbd service
			
			ORB orb = ORB.init(args, null);
			POA root = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
			root.the_POAManager().activate();
			
			CorbaImpl corbaImpl = new CorbaImpl();
			corbaImpl.setORB(orb);
			
			org.omg.CORBA.Object ref = root.servant_to_reference(corbaImpl);
			Corba corbaRef = CorbaHelper.narrow(ref);
			
			org.omg.CORBA.Object objectReference = orb.resolve_initial_references("NameService");
			NamingContextExt namingContextReference = NamingContextExtHelper.narrow(objectReference);
			
			String name = "getAverage";
			NameComponent path[] = namingContextReference.to_name(name);
			namingContextReference.rebind(path, corbaRef);
			
			System.out.println("CorbaServer ready and waiting for requests...");
			
			orb.run();
		} catch (Exception e){e.printStackTrace();}
		
		System.out.println("CorbaServer Exiting");
		
			
		}
	}
	


	
	
	
	
	
	

