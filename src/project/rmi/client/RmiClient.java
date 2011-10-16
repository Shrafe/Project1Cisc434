package rmi.client;

public class RmiClient {
	public static void main(String[] args) throws Exception {
		String scenario = args[0];
		String hostname = args[1];
		
		System.out.println("Running Scenario: "+scenario+" on hostname: "+hostname);
		
		if (scenario.equals("2")){
			for (int i = 0; i<10; i++){
				new RmiClientThread(i, hostname).start();
			}
		}
		else 
			new RmiClientThread(1, hostname).start();
	}
}
