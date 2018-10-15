package edu.xmu.test.javax.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RmiClient {
	public static void main(String[] args) throws RemoteException, NotBoundException {
		Registry registry = LocateRegistry.getRegistry();
		Adder adder = (Adder) registry.lookup("adder");
		System.out.println(adder.add(1, 3));
	}
}
