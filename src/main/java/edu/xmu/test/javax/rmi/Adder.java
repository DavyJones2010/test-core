package edu.xmu.test.javax.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Adder extends Remote {
	public int add(int x, int y) throws RemoteException;
}
