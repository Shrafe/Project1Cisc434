package corba.client;

public class CorbaClient {
	public static void main(String[] args) {
		String scenario = args[0];
		
		if (scenario.equals("2")){
			for (int i = 0; i<10; i++){
				new CorbaClientThread(i).start();
			}
		}
		else
			new CorbaClientThread(1).start();
	}
}
