package edu.xmu.test.javaweb.jndi;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class JNDISample {
	public static void main(String[] args) throws NamingException {
		Context initContext = new InitialContext();
		Context c = (Context) initContext.lookup("java:comp/env");
//		c.lookup(name);
	}
}
