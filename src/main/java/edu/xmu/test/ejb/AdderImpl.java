package edu.xmu.test.ejb;

import javax.ejb.Stateless;

@Stateless(mappedName = "st1")
public class AdderImpl implements Adder {
	@Override
	public int add(int a, int b) {
		return a + b;
	}
}
