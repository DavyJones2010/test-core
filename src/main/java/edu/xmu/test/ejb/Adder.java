package edu.xmu.test.ejb;

import javax.ejb.Remote;

@Remote
public interface Adder {
	int add(int a, int b);
}
