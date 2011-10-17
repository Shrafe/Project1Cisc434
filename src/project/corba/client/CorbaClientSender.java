package corba.client;

import corba.server.Payload;

public class CorbaClientSender extends Thread {
	public Payload payload;
	public int clientNum;
	public int threadNum;
	
	public CorbaClientSender(Payload payload, int cn, int tn){
		this.payload = payload;
		this.clientNum = cn;
		this.threadNum = tn;		
	}
	
	public void run(){
		long startTime = 0;
		long timeTaken = 0;
		long totalTime = 0;
		try{ 
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Sending payload ...");
			startTime = System.currentTimeMillis();
			CorbaClient.corbaImpl.getAverage(payload);
			timeTaken = System.currentTimeMillis() - startTime;
			totalTime+=timeTaken;
			System.out.println("Client:"+clientNum+"|Thread:"+threadNum+": Received result in: "+timeTaken+"ms");
		}catch (Exception e){e.printStackTrace();}
	}

}