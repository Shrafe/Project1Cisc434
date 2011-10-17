package rmi.server;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class RmiServerThread implements Callable<double[]> {
	private ArrayList<double[]>payload;
	
	public RmiServerThread(ArrayList<double[]>payload){
		this.payload = payload;
	}
	
	public double[] call(){
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
