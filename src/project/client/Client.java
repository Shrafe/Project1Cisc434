package project.client;

import java.io.*;
import java.net.*;

public class Client {
	public static void main (String [] args) throws IOException, ClassNotFoundException{
		Socket s = null; // the socket for communication and connecting to the server
		int multimode = 1; // indicates if we want to send only 1 payload or 10. 1 for 10, else for 1
		if (multimode==1){
			try{
				for (int i = 0; i < 10; i++){
					s = new Socket("localhost", 4444);
					new ClientThread(s).start();				
				}				
			}catch(Exception e){
				e.printStackTrace();
				System.exit(1);
			}
		// we want to send 10 Payloads in parallel
		
			// spawn a new ClientThread to send the payload to the socket and wait for the server's response
			// generate 10 payloads and send them

		}
		else { //send only one payload
			s = new Socket("localhost", 4444);
			new ClientThread(s).start();
		}
	}

}
