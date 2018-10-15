package edu.xmu.test.spring.aop;

public class Human implements Sleepable {

	@Override
	public void sleep() {
		System.out.println("Human is going to sleep");
	}

}
