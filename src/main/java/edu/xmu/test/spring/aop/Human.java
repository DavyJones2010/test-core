package edu.xmu.test.spring.aop;

public class Human {

	//@Override
	public void sleep() {
		System.out.println("Human is going to sleep");
		//((Sleepable)AopContext.currentProxy()).sleepDeep();
		sleepDeep();
	}

	//@Override
	public void sleepDeep() {
		System.out.println("Human is going to sleepDeep");
	}

}
