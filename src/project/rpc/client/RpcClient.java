package rpc.client;

public class RpcClient {
	public static void main(String[] args) throws Exception {
		int scenario = Integer.parseInt(args[0]);
		String hostname = args[1];
		int port = Integer.parseInt(args[2]);
		String serverUrl = hostname+":"+port+"/xmlrpc";
		
		RpcClientThread[] clients = new RpcClientThread[10];
		
		System.out.println("Running Scenario: "+scenario+" on server:" +serverUrl);
		
		for (int i = 0; i<10; i++){
			clients[i] = new RpcClientThread(i, serverUrl, scenario);
		}
		
		for (int i=0; i<10; i++){
			clients[i].start();
		}
	}
}
