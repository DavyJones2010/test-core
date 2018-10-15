package edu.xmu.test.spring.aop;

public class Person implements Sleepable {

	@Override
	public void sleep() {
		System.out.println("Person is sleeping");
	}

}
