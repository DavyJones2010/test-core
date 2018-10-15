package edu.xmu.test.javax.rmi;

import java.rmi.RemoteException;

public class AdderImpl implements Adder {

	@Override
	public int add(int x, int y) throws RemoteException {
		return x + y;
	}

}
