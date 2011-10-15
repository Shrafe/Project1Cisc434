package rmi.server;

import java.util.*;
import java.rmi.server.*;
import java.rmi.*;
import java.rmi.registry.*;

public class RmiServerImpl implements RmiServer {
	
	public RmiServerImpl(){}	
	
	public static void main(String [] args){
		try{
			String name = "RmiServer";
			RmiServer server = new RmiServerImpl();
			RmiServer stub = (RmiServer) UnicastRemoteObject.exportObject(server,4444);
			Registry reg = LocateRegistry.createRegistry(1099);
			reg.rebind(name,stub);
			System.out.println("RmiServerImpl ready");
		} catch (Exception e){e.printStackTrace();}
	}
	
	public double[] getAverage(ArrayList<double[]>payload){
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
