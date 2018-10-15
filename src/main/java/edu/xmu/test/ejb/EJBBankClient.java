package edu.xmu.test.ejb;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class EJBBankClient {
	public static void main(String[] args) throws NamingException {
		Context context = new InitialContext();
		Bank bank = (Bank) context.lookup("statefulBank");
		System.out.println(bank.getBalance());
		bank.deposit(1234);
		System.out.println(bank.getBalance());
		bank.withdraw(1000);
		System.out.println(bank.getBalance());
		// stateful session bean will maintain state between these requests
	}
}
