package socket.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	public static void main (String [] args) throws IOException{
		ServerSocket[] sockets = new ServerSocket[10];
		//String initialPort = args[0];
		String initialPort = "4444";
		
		try {
			
			// Set up a range of 10 ports to use so that we limit ourselves to 10 clients at a time
			for (int i = 0; i < 10; i++) {
				sockets[i] = new ServerSocket(Integer.parseInt(initialPort) + i);
			}
		}
		catch (Exception e){
			System.err.println("Error in SocketServer:\n");
			e.printStackTrace();
			System.exit(1);
		}
		
		System.out.println("Socket Server listening on ports: " + initialPort + "-" + (Integer.parseInt(initialPort) + 10));
		while (true){
			//new SocketServerThread(sockets[0].accept()).start();
			//new SocketServerThread(sockets[1].accept()).start();
			for (int i = 0; i < 10; i++) {
				// create a new thread with the socket received if a client connects, and start it
				new SocketServerThread(sockets[i].accept()).start(); 
			}
		}		
	}
}