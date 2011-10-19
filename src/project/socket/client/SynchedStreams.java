package socket.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SynchedStreams {
	private ObjectInputStream ois;
	private ObjectOutputStream oos; 

	public SynchedStreams(ObjectOutputStream oos){
		this.oos = oos; //ordering of these is important

	}
	
	public synchronized void sendHelper(Object payload){
		try{
			this.oos.writeObject(payload);
			this.oos.flush();
		} catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}}
	}
}
