package socket.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SynchedSocket {
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos; 

	public SynchedSocket(Socket s){
		this.s = s;
		try{
		this.ois = new ObjectInputStream(s.getInputStream());
		this.oos = new ObjectOutputStream(s.getOutputStream());
		}catch (Exception e){
			e.printStackTrace();
		}
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
		
	public synchronized double[] receiveHelper(){
		double[] result = null;
		try{
			result = (double[]) this.ois.readObject();
		} catch(Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
		return result;
	}
	
	public void close(){
		try{s.close();}catch(Exception e){}
	}
}
