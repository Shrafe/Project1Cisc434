package rpc.client;

import java.net.*;
import java.util.*;
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.*;

public class RpcClient {
	public static void main(String[] args) throws Exception {
		String scenario = args[0];
		String hostname = args[1];
		int port = Integer.parseInt(args[2]);
		String serverUrl = hostname+":"+port+"/xmlrpc";
		
		System.out.println("Running Scenario: "+scenario+" on server:" +serverUrl);
		
		if (scenario.equals("2")){
			for (int i = 0; i<10; i++){
				new RpcClientThread(i, serverUrl).start();
			}
		}
		else
			new RpcClientThread(1, serverUrl).start();
	}
}
