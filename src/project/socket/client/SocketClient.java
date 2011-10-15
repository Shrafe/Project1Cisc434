package socket.client;
// YO
import java.io.*;
import java.net.*;

public class SocketClient {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		Socket s = null; // the socket for communication and connecting to the server
		boolean scenario2 = false;
		int socket = 4444;
			// 	we want to send 10 Payloads in parallel
			// spawn a new ClientThread to send the payload to the socket and wait for the server's response
			// generate 10 payloads and send them
			//send only one payload
			if (scenario2){
				for (int i = 0; i < 10; i++){
					new SocketClientThread(new Socket("localhost",4444)).start(); // 10 calls at the same time.
				}
			}
			else
				new SocketClientThread(new Socket("localhost",4444)).start(); // 1 thread, calling ten times

	}

}
