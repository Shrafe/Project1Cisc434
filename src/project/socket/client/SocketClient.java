package socket.client;
// YO
import java.io.*;
import java.net.*;

public class SocketClient {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		String scenario = args[0];
		int port = Integer.parseInt(args[1]);
		String hostname = args[2];
		
		System.out.println("Running Scenario: "+scenario+" on hostname: "+hostname+" on port: "+port);
		
		if (scenario.equals("2")){
			for (int i = 0; i < 10; i++){
				new SocketClientThread(new Socket(hostname,port),i).start(); // 10 threads each calling 10 times
			}
		}
		else
			new SocketClientThread(new Socket(hostname,port),1).start(); // 1 thread calling ten times

	}

}
