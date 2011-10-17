package rmi.client;

public class RmiClient {
	public static void main(String[] args) throws Exception {
		int scenario = Integer.parseInt(args[0]);
		String hostname = args[1];
		
		RmiClientThread[] clients = new RmiClientThread[10];
		
		System.out.println("Running Scenario: "+scenario+" on hostname: "+hostname);
		
		for (int i = 0; i<10; i++){
			clients[i] = new RmiClientThread(i, hostname, scenario);
		}
		
		for (int i = 0; i<10; i++){
			clients[i].start();
		}
	}
}

