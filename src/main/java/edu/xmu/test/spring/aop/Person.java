package edu.xmu.test.spring.aop;

public class Person {

	//@Override
	public void sleep() {
		System.out.println("Person is sleeping");
		sleepDeep();
	}

	//@Override
	public void sleepDeep() {
		System.out.println("Person is sleepingDeep");
	}

}
