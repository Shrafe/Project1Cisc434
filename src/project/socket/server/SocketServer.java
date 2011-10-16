package socket.server;

import java.io.IOException;
import java.net.ServerSocket;

public class SocketServer {

	public static void main (String [] args) throws IOException{
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(4444); // arbitrary port. make configurable?
		}
		catch (IOException e){
			System.err.println("Couldn't listen to the socket 10000. FAIL");
			System.exit(1);
		}
		System.out.println("Socket Server ready");
		while (true){
			new SocketServerThread(server.accept()).start(); // create a new thread with the socket recieved if a client connects, and start it	
		}		
	}
}