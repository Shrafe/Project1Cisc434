package socket.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	public static void main (String [] args) throws IOException{
		ServerSocket socket = new ServerSocket();
		//String initialPort = args[0];
		String initialPort = "4444";

		try {
			socket = new ServerSocket(Integer.parseInt(initialPort));
		}
		catch (Exception e){
			System.err.println("Error in SocketServer:\n");
			e.printStackTrace();
			System.exit(1);
		}

		System.out.println("Socket Server listening on: " + initialPort);
		while (true){
			// create a new thread with the socket received if a client connects, and start it

			new NewSocketServerThread(socket.accept()).start();
		}
	}
}