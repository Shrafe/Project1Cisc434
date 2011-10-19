package socket.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * This class synchronizes writes on the given OutputStream
 * Utilized by SocketClient in order to not corrupt the stream
 * in scenario 2. 
 * @author TomW7
 *
 */

public class SynchedStreams {
	private ObjectOutputStream oos; //the stream we're synchronizing

	public SynchedStreams(ObjectOutputStream oos){
		this.oos = oos; 

	}
	
	/**
	 * Sends the object Payload along the stream. Other Threads attempting
	 * to send along the same stream will not corrupt it.
	 * @param payload
	 */
	public synchronized void send(Object payload){
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
