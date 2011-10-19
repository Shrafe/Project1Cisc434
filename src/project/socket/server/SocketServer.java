package socket.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	public static void main (String [] args) throws IOException{
		//String initialPort = args[0];
		String initialPort = "4444";
		ServerSocket socket = null;
		try {

			// Set up a range of 10 ports to use so that we limit ourselves to 10 clients at a time
			socket = new ServerSocket(Integer.parseInt(initialPort));

		}
		catch (Exception e){
			System.err.println("Error in SocketServer:\n");
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Socket Server listening on port: " + initialPort);
		while (true){
			//new SocketServerThread(sockets[0].accept()).start();
			//new SocketServerThread(sockets[1].accept()).start();
			// create a new thread with the socket received if a client connects, and start it
			new SocketServerThread(socket.accept()).start(); 

		}		
	}
}