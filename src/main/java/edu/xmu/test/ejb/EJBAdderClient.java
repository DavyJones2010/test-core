package edu.xmu.test.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBAdderClient {
	public static void main(String[] args) throws NamingException {
		Context context = new InitialContext();
		Adder adder = (Adder) context.lookup("st1");
		// first request
		System.out.println(adder.add(1, 2));
		// second request
		System.out.println(adder.add(3, 2));
		// stateless session bean will not maintain state between these two request
	}
}
