package rmi.client;

public class RmiClient {
	public static void main(String[] args) throws Exception {
		String scenario = args[0];
		
		if (scenario.equals("2")){
			for (int i = 0; i<10; i++){
				new RmiClientThread().start();
			}
		}
		else 
			new RmiClientThread().start();
	}
}
