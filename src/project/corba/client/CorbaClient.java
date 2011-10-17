package corba.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import corba.server.*;

public class CorbaClient {
	public static Corba corbaImpl;
	public static void main(String[] args) {
		int scenario = Integer.parseInt(args[0]);
		
		String[] ph = null;
		ORB orb = ORB.init(ph,null);
		
		CorbaClientThread[] clients = new CorbaClientThread[10];
		
		try {
			org.omg.CORBA.Object objectReference = orb.resolve_initial_references("NameService");
			NamingContextExt namingContextReference = NamingContextExtHelper.narrow(objectReference);
			corbaImpl = CorbaHelper.narrow(namingContextReference.resolve_str("getAverage"));
		} catch (Exception e) {e.printStackTrace();}
			
		for (int i = 0; i<10; i++){
			clients[i] = new CorbaClientThread(i, scenario);
		}
		
		for (int i = 0; i<10; i++){
			clients[i].start();
		}
		
		
	}
}
