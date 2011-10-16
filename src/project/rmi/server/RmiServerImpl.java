package rmi.server;

import java.util.*;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;

public class RmiServerImpl implements RmiServer {
	
	public RmiServerImpl(){}	
	
	public static void main(String [] args){
		try{
			int port = Integer.parseInt(args[0]);
			int registryPort = 1099;
			if (args.length > 1){
				registryPort = Integer.parseInt(args[1]);
			}
						
			String name = "RmiServer";
			RmiServer server = new RmiServerImpl(); // create our server object
			RmiServer stub = (RmiServer) UnicastRemoteObject.exportObject(server,port); // makes the server available for remote calls on port 
			Registry reg = LocateRegistry.createRegistry(registryPort); // create a new registry on port 1099
			reg.rebind(name,stub); // register the name RmiServer with the registry, which the client uses to call this Class
			System.out.println("Rmi Server startup complete.\nRegistry listening on port: "+registryPort+"\nServer listening on port: "+port); // ready for calls
		} catch (Exception e){e.printStackTrace();}
	}
	
	public double[] getAverage(ArrayList<double[]>payload){ // implment the RmiServer interface
		double currentGreatest = 0;
		double[] greatestArray = null;
		for (double[] array : payload){
			double average = getAverageArray(array);
			if (average > currentGreatest){
				currentGreatest = average;
				greatestArray = array;
			}
		}
		return greatestArray;
	}
	
	public double getAverageArray(double[] arr){
		double average = 0;
		double sum = 0;
		for (int i=0; i<arr.length; i++){
			sum += arr[i];
		}
		average = sum / 1000;
		return average;
	}

	
	
}
