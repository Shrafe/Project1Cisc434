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
		int threadNum = 0;
		while (true){
			new SocketServerThread(socket.accept(), threadNum).run();
			threadNum++;
		}
	}
}