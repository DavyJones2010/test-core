package edu.xmu.test.javax.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer {
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		Adder adder = new AdderImpl();
		Adder stub = (Adder) UnicastRemoteObject.exportObject(adder, 11111);
		LocateRegistry.getRegistry().bind("adder", stub);
		System.out.println("Server ready");
	}
}
