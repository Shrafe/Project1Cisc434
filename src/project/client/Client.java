package client;
// YO
import java.io.*;
import java.net.*;

public class Client {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		Socket s = null; // the socket for communication and connecting to the server
		boolean scenario2 = true;
			// 	we want to send 10 Payloads in parallel
			// spawn a new ClientThread to send the payload to the socket and wait for the server's response
			// generate 10 payloads and send them
			//send only one payload
			s = new Socket("localhost", 4444);
			new ClientThread(s, scenario2).start();

	}

}
