package rpc.server;

import java.util.*;

public class Worker {
	public double[] getAverage(double[] a, double[] b, double[] c, double[] d, double[] e){
		ArrayList<double[]>payload = new ArrayList<double[]>(5);
		payload.add(a);
		payload.add(b);
		payload.add(c);
		payload.add(d);
		payload.add(e);
		double currentGreatest = 0;
		double[] greatestArray = null;
			
		for (double[] array : payload){
			double thisGreatest = getAverageArr(array);
			if (thisGreatest > currentGreatest){
				currentGreatest = thisGreatest;
				greatestArray = array;
			}
		}
		return greatestArray;
	}
	
	public double getAverageArr(double[] arr){ 
		double average = 0;
		double sum = 0;
		for (int i=0; i<arr.length; i++){
			sum += arr[i];
		}
		average = sum / 1000;
		return average;
	}

}
