package edu.xmu.test.guava.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.google.common.base.Predicate;
import com.google.common.base.Supplier;

public class SupplierTest {

	@Test
	public void getTest() {
		String str = new Supplier<String>() {
			public String get() {
				return "AAA";
			}
		}.get();

		assertEquals("AAA", str);
	}

	@Test
	public void getTest2() {
		Predicate<String> predicate = new Supplier<Predicate<String>>() {
			public Predicate<String> get() {
				return new Predicate<String>() {
					public boolean apply(String input) {
						return "AAA".equals(input);
					}
				};
			}
		}.get();

		assertTrue(predicate.apply("AAA"));
		assertFalse(predicate.apply("BBB"));
	}

}
