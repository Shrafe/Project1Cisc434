package socket.client;
// YO
import java.io.*;
import java.net.*;

public class SocketClient {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		String scenario = args[0];
		int port = Integer.parseInt(args[1]);
		String hostname = args[2];
		
		SocketClientThread[] clients = new SocketClientThread[10];
		
		System.out.println("Running Scenario: "+scenario+" on hostname: "+hostname+" on port: "+port);
		
		if (scenario.equals("2")){
			for (int i = 0; i < 10; i++){
				// 10 clients(threads) each calling 10 times
				clients [i] = new SocketClientThread(new Socket(hostname,port + i), i + 1, Integer.parseInt(scenario));
			}
			// Start every client at closer to the same time than if we'd done it above. (Hopefully)
			for (int i = 0; i < 10; i++){
				clients[i].start();
			}
		}
		else {
			// Create 10 clients that will send 1 payload each on different ports
			for (int i = 0; i < 10; i++){
				// 10 threads calling 1 time
				clients [i] = new SocketClientThread(new Socket(hostname,port + i), i + 1, Integer.parseInt(scenario));
			}
			// Start every client at closer to the same time than if we'd done it above. (Hopefully)
			for (int i = 0; i < 10; i++){
				clients[i].start();
			}
		}
	}
}
