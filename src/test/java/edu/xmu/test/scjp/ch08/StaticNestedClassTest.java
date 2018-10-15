package edu.xmu.test.scjp.ch08;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class StaticNestedClassTest {
	public static String someStr = "someStr";
	public String otherStr = "otherStr";

	static interface Nestable {
		String go();
	}

	static class Nest implements Nestable {
		@Override
		public String go() {
			System.out.println(someStr);
			// System.out.println(otherStr); // Compilation error!
			return "Nest";
		}
	}

	private static class PrivateNest implements Nestable {
		@Override
		public String go() {
			System.out.println(someStr);
			// System.out.println(otherStr);//Compilation error!
			return "PrivateNest";
		}
	}

	public static class PublicNest implements Nestable {
		@Override
		public String go() {
			System.out.println(someStr);
			// System.out.println(otherStr); // Compilation error!
			return "PublicNest";
		}
	}

	@Test
	public void instantiateNestedClassTest() {
		assertEquals("Nest", new Nest().go());
		assertEquals("PrivateNest", new PrivateNest().go());
		assertEquals("PublicNest", new PublicNest().go());
	}

}

class AnotherClass {
	public void accessTest() {
		assertEquals("Nest", new StaticNestedClassTest.Nest().go());
		// assertEquals("PrivateNest",
		// new StaticNestedClassTest.PrivateNest().go());//Compilation error!
		assertEquals("PublicNest", new StaticNestedClassTest.PublicNest().go());
	}
}
