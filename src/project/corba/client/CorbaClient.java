package corba.client;

import org.omg.CORBA.ORB;
import org.omg.CosNaming.NamingContextExt;
import org.omg.CosNaming.NamingContextExtHelper;
import corba.server.*;

public class CorbaClient {
	public static Corba corbaImpl;
	public static void main(String[] args) {
		String scenario = args[0];
		
		String[] ph = null;
		ORB orb = ORB.init(ph,null);
		
		try {
			org.omg.CORBA.Object objectReference = orb.resolve_initial_references("NameService");
			NamingContextExt namingContextReference = NamingContextExtHelper.narrow(objectReference);
			corbaImpl = CorbaHelper.narrow(namingContextReference.resolve_str("getAverage"));
		} catch (Exception e) {e.printStackTrace();}
			
		
		
		if (scenario.equals("2")){
			for (int i = 0; i<10; i++){
				new CorbaClientThread(i).start();
			}
		}
		else
			new CorbaClientThread(1).start();
	}
}
