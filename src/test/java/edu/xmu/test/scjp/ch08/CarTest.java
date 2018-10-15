package edu.xmu.test.scjp.ch08;

import org.junit.Test;

public class CarTest {
	class Engine {
		{
			System.out.println("In preceeding method scope");
			CarTest.this.drive();
		}

		Engine() {
			System.out.println("In constructor scope");
			CarTest.this.drive();
		}

		{
			System.out.println("In afterwarding method scope");
			CarTest.this.drive();
		}
	}

	@Test
	public void goTest() {
		new Engine();
	}

	void go() {
		new Engine();
	}

	void drive() {
		System.out.println("Hallo");
	}
}
