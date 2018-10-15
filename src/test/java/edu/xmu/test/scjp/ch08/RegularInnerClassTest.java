package edu.xmu.test.scjp.ch08;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import edu.xmu.test.scjp.ch08.RegularInnerClassTest.OuterClass.InnerClass;

/**
 * Regular inner class is not: <br/>
 * <li>static</li> <li>method-local</li> <li>anonymous</li> <br/>
 * The only way you can access the inner class is through the live instance of
 * outer class
 */
public class RegularInnerClassTest {

	@Test
	public void instantiateInnerTest() {
		OuterClass outer = new OuterClass("Hallo");
		assertEquals("Hallo", outer.inner.getValue());
	}

	@Test
	public void instantiateInnerTest2() {
		OuterClass outer = new OuterClass("Hallo");
		InnerClass inner = outer.new InnerClass();
		assertEquals("Hallo", inner.getValue());
		assertNotEquals(outer.inner, inner);

		inner.setValue("Yes");
		assertEquals("Yes", inner.getValue());
		assertEquals("Yes", outer.inner.getValue());
	}

	@Test
	public void accessOuterClassInstanceWithinInnerClassTest() {
		OuterClass outer = new OuterClass("Hallo");
		InnerClass inner1 = outer.new InnerClass();
		InnerClass inner2 = outer.new InnerClass();
		InnerClass inner3 = outer.inner;
		assertEquals(inner1.accessOuterClassInstance(),
				inner2.accessOuterClassInstance());
		assertEquals(inner1.accessOuterClassInstance(),
				inner3.accessOuterClassInstance());
	}

	@SuppressWarnings("unused")
	@Test
	public void accessOuterClassInstanceOutOfInnerClassTest() {
		// There is no way for inner class instance to get outer class instance
		// directly in places outside of InnerClass defination itself
		OuterClass outer = new OuterClass("Hallo");
		InnerClass inner = outer.inner;
		// inner.OuterClass.this; // Compilation Error!
		InnerClass inner2 = outer.new InnerClass();
		// inner2.OuterClass.this; // Compilation Error!
	}

	public static class OuterClass {
		private String value;
		public final InnerClass inner;

		public OuterClass(String value) {
			super();
			this.value = value;
			inner = new InnerClass();
		}

		public class InnerClass {
			public String getValue() {
				return value;
			}

			public void setValue(String inputValue) {
				value = inputValue;
			}

			public OuterClass accessOuterClassInstance() {
				return OuterClass.this;
			}
		}
	}
}
