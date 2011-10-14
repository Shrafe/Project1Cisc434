package socket.server;

// derp
import java.net.*;
import java.io.*;
// just run this thing and connect using the Client class

public class Server {

	public static void main (String [] args) throws IOException{
		ServerSocket server = null;
		
		try {
			server = new ServerSocket(4444); // arbitrary port. make configurable?
		}
		catch (IOException e){
			System.err.println("Couldn't listen to the socket 10000. FAIL");
			System.exit(1);
		}
		
		while (true){
			new ServerThread(server.accept()).start(); // create a new thread with the socket recieved if a client connects, and start it	
		}		
	}
}




