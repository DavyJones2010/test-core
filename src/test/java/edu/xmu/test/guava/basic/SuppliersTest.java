package edu.xmu.test.guava.basic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.base.Functions;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import com.google.common.collect.Maps;

public class SuppliersTest {

	@Test
	public void memoizeTest() {
		Supplier<Object> supplier = new Supplier<Object>() {
			public Object get() {
				return new Object();
			}
		};
		Object obj1 = supplier.get();
		Object obj2 = supplier.get();
		assertNotEquals(obj1, obj2);

		Supplier<Object> proxy = Suppliers.memoize(supplier);

		obj1 = proxy.get();
		obj2 = proxy.get();
		assertEquals(obj1, obj2);
	}

	@Test
	public void memoizeWithExpirationTest() throws InterruptedException {
		Supplier<Object> supplier = new Supplier<Object>() {
			public Object get() {
				return new Object();
			}
		};
		Object obj1 = supplier.get();
		Object obj2 = supplier.get();
		assertNotEquals(obj1, obj2);

		Supplier<Object> proxy = Suppliers.memoizeWithExpiration(supplier, 1L, TimeUnit.SECONDS);
		obj1 = proxy.get();
		obj2 = proxy.get();
		assertEquals(obj1, obj2);

		obj1 = proxy.get();
		Thread.sleep(1000 * 2);
		obj2 = proxy.get();
		assertNotEquals(obj1, obj2);
	}

	@Test
	public void composeTest() {
		Map<String, String> map = Maps.newHashMap();
		map.put("A", "A for alcohol");
		map.put("B", "B for boycott");
		Supplier<String> supplier = Suppliers.compose(Functions.forMap(map), new Supplier<String>() {
			public String get() {
				return "A";
			}
		});
		String str = supplier.get();
		assertEquals("A for alcohol", str);
	}
}
