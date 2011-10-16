package socket.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	public static void main (String [] args) throws IOException{
		ServerSocket server = null;
		String port = args[0];
		
		try {
			server = new ServerSocket(Integer.parseInt(port)); // arbitrary port. make configurable?
		}
		catch (Exception e){
			System.err.println("Error in SocketServer:\n");
			e.printStackTrace();
			System.exit(1);
		}
		System.out.println("Socket Server listening on: "+port);
		while (true){
			new SocketServerThread(server.accept()).start(); // create a new thread with the socket recieved if a client connects, and start it	
		}		
	}
}