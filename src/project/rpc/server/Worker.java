package rpc.server;

import java.util.*;

/**
 * This Class contains the method that does the work for the RPC call. 
 * It is the one that is added to the HandlerMapping of the server. 
 * It keeps track of the total number of calls, and the calls of the 
 * particular instance of the Class.
 * 
 * @author TomW7
 *
 */

public class Worker {
	public static int timesCalled = 1; // total calls
	public int timesCalledThis = 1; // calls on this object 
	
	/**
	 * This method takes the arrays sent by the client and returns the array with the greatest average across elements
	 * @param a the first array
	 * @param b the second array
	 * @param c the third array
	 * @param d the fourth array
	 * @param e the fifth array
	 * @return
	 */
	public double[] getAverage(double[] a, double[] b, double[] c, double[] d, double[] e){ //method signature is 5 double[] because of the way params are sent in RPC
		System.out.println("Total getAverage calls: "+timesCalled+" | This instance was called: "+timesCalledThis+" time(s)."); // print that we've been called
		timesCalledThis++; // increment times this Class has been called 
		timesCalled++; // increment static call counter 
		ArrayList<double[]>payload = new ArrayList<double[]>(5); // add all arrays to an array list
		payload.add(a);
		payload.add(b);
		payload.add(c);
		payload.add(d);
		payload.add(e);
		double currentGreatest = 0;
		double[] greatestArray = null;
			
		for (double[] array : payload){ // iterate through the list of double[]s, finding the one with the greatest average.
			double thisGreatest = getAverageArr(array);
			if (thisGreatest > currentGreatest){
				currentGreatest = thisGreatest;
				greatestArray = array;
			}
		}
		return greatestArray; // return the array with the greatest average
	}
	
	/**
	 * Returns the average across the elements of the parameter array
	 * @param arr 
	 * @return
	 */
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
