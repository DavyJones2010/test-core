package edu.xmu.test.ejb;

import javax.ejb.Remote;

@Remote
public interface Bank {
	boolean withdraw(int amount);

	void deposit(int amount);

	int getBalance();
}
