package socket.client;
// YO
import java.io.*;
import java.net.*;

public class SocketClient {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		String scenario = args[0];
		int socket = 4444;
		
		if (scenario.equals("2")){
			for (int i = 0; i < 10; i++){
				new SocketClientThread(new Socket("localhost",socket),i).start(); // 10 threads each calling 10 times
			}
		}
		else
			new SocketClientThread(new Socket("localhost",socket),1).start(); // 1 thread calling ten times

	}

}
