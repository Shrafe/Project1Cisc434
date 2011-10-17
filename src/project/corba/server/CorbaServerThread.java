package corba.server;

import java.util.ArrayList;
import java.util.concurrent.Callable;

public class CorbaServerThread implements Callable<double[]> {
	private Payload p;
	
	public CorbaServerThread(Payload p){
		this.p = p;
	}

	public double[] call() throws Exception{
		ArrayList<double[]>payload = new ArrayList<double[]>();
		double currentGreatest = 0;
		double[] greatestArray = null;
		payload.add(p.arrayOne);
		payload.add(p.arrayTwo);
		payload.add(p.arrayThree);
		payload.add(p.arrayFour);
		payload.add(p.arrayFive);			
		for (double[] array : payload){
			double thisGreatest = getAverageArr(array);
			if (thisGreatest > currentGreatest){
				currentGreatest = thisGreatest;
				greatestArray = array;
			}
		}
		return greatestArray;			
	}
		
		private double getAverageArr(double[] arr){ 
			double average = 0;
			double sum = 0;
			for (int i=0; i<arr.length; i++){
				sum += arr[i];
			}
			average = sum / 1000;
			return average;
		}
	}