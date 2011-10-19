package socket.client;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class SocketClientHelper {
	
	public static synchronized void sendHelper(ObjectOutputStream oos, Object payload){
		try{
			oos.writeObject(payload);
		} catch (Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}}
	}
		
	public static synchronized Object receiveHelper(ObjectInputStream ois){
		Object result = null;
		try{
			result = ois.readObject();
		} catch(Exception e){
			e.printStackTrace();
			try{
				Thread.sleep(10000);
			}catch(Exception ex){ex.printStackTrace();}
		}
		return result;
	}
}
