package edu.xmu.test.scjp.ch08;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

class A {
	String m() {
		return "Outer";
	}
}

public class TestInner {
	class A {
		String m() {
			return "Middle";
		}
	}

	@Test
	public void innerTest() {
		assertEquals("Middle", new TestInner().new A().m());
		assertEquals("Middle", new A().m());
		@SuppressWarnings({ "unused", "hiding" })
		class A {
			String m() {
				return "Inner";
			}
		}
	}
}
