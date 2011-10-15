package rmi.server;

import java.rmi.*;
import java.util.*;

public interface RmiServer extends Remote {
	// remote method
	public double[] getAverage(ArrayList<double[]> payload) throws RemoteException; 
}
