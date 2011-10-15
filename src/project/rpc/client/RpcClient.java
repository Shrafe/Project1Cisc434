package rpc.client;

import java.net.*;
import java.util.*;
import org.apache.xmlrpc.*;
import org.apache.xmlrpc.client.*;

public class RpcClient {
	public static void main(String[] args) throws Exception {
		boolean scenario2 = true;
		
		if (scenario2){
			for (int i = 0; i<10; i++){
				new RpcClientThread().start();
			}
		}
		else
			new RpcClientThread().start();
	}

	
	
}
