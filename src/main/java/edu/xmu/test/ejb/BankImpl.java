package edu.xmu.test.ejb;

import javax.ejb.Stateful;

@Stateful(mappedName = "statefulBank")
public class BankImpl implements Bank {
	private int amount;

	@Override
	public boolean withdraw(int amount) {
		if (amount <= this.amount) {
			this.amount -= amount;
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void deposit(int amount) {
		this.amount += amount;
	}

	@Override
	public int getBalance() {
		return this.amount;
	}

}
