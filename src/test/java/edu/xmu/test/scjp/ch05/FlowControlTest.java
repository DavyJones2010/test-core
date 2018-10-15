package edu.xmu.test.scjp.ch05;

import org.junit.Test;

public class FlowControlTest {

	@Test
	public void defaultTest() {
		switch (Color.GREEN) {
		case BLUE:
			System.out.println("BLUE");
		default:
			System.out.println("DEFAULT");
		case GREEN:
			System.out.println("GREEN");
		case YELLOW:
			System.out.println("YELLOW");
		}
	}

	@Test
	public void forTest() {
		for (int i = 0; i < 10; i++) {
			continue;
		}
		// for (int i = 0; i < 10;) {
		// continue;
		// i++;
		// }
		for (int i = 0; i < 10;) {
			if (i == 5) {
				// continue; // Loop forever
				break;
			}
			i++;
		}
	}

	@Test
	public void labeledTest1() {
		System.out.println("A");
		boolean isTrue = true;
		outer: for (int i = 0; i < 2; i++) {
			System.out.println("B");
			while (isTrue) {
				break;
			}
			System.out.println("C");
		}
		System.out.println("D");
	}

	@Test
	public void labeledTest2() {
		System.out.println("A");
		boolean isTrue = true;
		outer: for (int i = 0; i < 2; i++) {
			System.out.println("B");
			while (isTrue) {
				break outer;
			}
			System.out.println("C");
		}
		System.out.println("D");
	}

	@Test
	public void labeledTest3() {
		System.out.println("A");
		for (int i = 0; i < 2; i++) {
			System.out.println("B");
			for (int j = 0; j < 2; j++) {
				System.out.println("C");
				continue;
			}
			System.out.println("D");
		}
		System.out.println("E");
	}

	@Test
	public void labeledTest4() {
		System.out.println("A");
		outer: for (int i = 0; i < 2; i++) {
			System.out.println("B");
			for (int j = 0; j < 2; j++) { // j++ is dead code
				System.out.println("C");
				continue outer;
			}
			System.out.println("D");
		}
		System.out.println("E");
	}

	enum Color {
		GREEN, BLUE, YELLOW, BLACK;
	}

	@Test
	public void forLoopTest() {
	}
}
