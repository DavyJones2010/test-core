package edu.xmu.test.scjp.ch06;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.junit.Test;

public class MethodHandleTest {

	@Test
	public void test() throws Throwable {
		MethodHandle handle = MethodHandles.lookup().findStatic(
				MethodHandleTest.class, "sayHello",
				MethodType.methodType(String.class));
		assertEquals("Hello", handle.invoke());
		assertEquals("Hello", (String) handle.invokeExact());
	}

	public static String sayHello() {
		return "Hello";
	}
}
