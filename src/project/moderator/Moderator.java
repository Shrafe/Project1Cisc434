package moderator;

import corba.client.*;
import rmi.client.*;
import rpc.client.*;
import socket.client.*;
import java.io.*;

public class Moderator {

	public static void main(String[] args) throws IOException{
		
		// The variables that will be used as defaults
		String tech, address;
		String scenario = "2";
		String ip = "127.0.0.1";
		String port = "4444";
		
		InputStreamReader reader = new InputStreamReader(System.in);
		BufferedReader br = new BufferedReader(reader);
		
		// Figure out what you want to demonstrate
		System.out.println("Please enter a Technology to use:\n1 - Socket, 2 - RMI, 3 - RPC, 4 - CORBA:");
		tech = br.readLine();
		
		// Choose the IP and port settings to use, type in your own if the defaults don't work for you
		System.out.println("Do you wish to use the IP " + ip +" and the port " + port + "? Type 'y' or 'n':");
		address = br.readLine();
		
		if (address.toLowerCase().equals("n")) {
			System.out.println("Enter an IP address:");
			ip = br.readLine();
			
			System.out.println("Enter a port number");
			port = br.readLine();
		}
		
		// Set up the string array that will be sent to the main methods of the clients
		String[] params = {scenario, port, ip, "0"};
		
		// Choose which client to start
		switch(Integer.parseInt(tech)) {
		case 1:	// Socket Case
			NewSocketClient[] clients = new NewSocketClient[10];
			
			for (int i=0; i<10; i++) {
				//params[3] = Integer.toString(i + 1); 
				try {
					//SocketClient.main(params);
					clients[i] = new NewSocketClient(Integer.parseInt(scenario),
							Integer.parseInt(port), ip, i+1);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			break;
		case 2: // RMI Case
			for (int i=0; i<10; i++) {
				params[3] = Integer.toString(i + 1); 
				try {
					RmiClient.main(params);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 3:  // RPC Case
			for (int i=0; i<10; i++) {
				params[3] = Integer.toString(i + 1); 
				try {
					RpcClient.main(params);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case 4: // CORBA Case
			for (int i=0; i<10; i++) {
				params[3] = Integer.toString(i + 1); 
				try {
					CorbaClient.main(params);
				}
				catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		default:
			System.out.println("That number is not an option. Exiting.");
			break;
		}
		
		br.close();
		reader.close();
		
	}
}
