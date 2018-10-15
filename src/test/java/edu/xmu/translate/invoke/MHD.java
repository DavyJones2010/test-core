package edu.xmu.translate.invoke;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import org.junit.Test;

public class MHD {
	@Test
	public void lookupStaticTest() throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findStatic(MHD.class, "hello",
				MethodType.methodType(void.class));
		mh.invokeExact();
	}

	static void hello() {
		System.out.println("hello");
	}

	static String echo(String msg) {
		return msg;
	}

	static Integer echo(Integer val) {
		return val;
	}

	@Test(expected = IllegalAccessException.class)
	public void lookupStaticTest2() throws Throwable {
		HW hw = new HW();
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodHandle mh = lookup.findVirtual(HW.class, "hello1",
				MethodType.methodType(void.class));
		mh.invoke(hw);

		mh = lookup.findVirtual(HW.class, "hello2",
				MethodType.methodType(void.class));
		mh.invoke(hw);
	}

	@Test
	public void invokeExactAndInvokeTest() throws Throwable {
		MethodHandle mh = MethodHandles.lookup().findStatic(MHD.class, "echo",
				MethodType.methodType(String.class, String.class));
		assertEquals("hello", (String) mh.invoke("hello"));
		assertEquals("hello", (String) mh.invokeExact("hello"));

		mh = MethodHandles.lookup().findStatic(MHD.class, "echo",
				MethodType.methodType(Integer.class, Integer.class));
		assertEquals(new Integer(-123), (Integer) mh.invoke(-123));
		assertEquals(new Integer(-123),
				(Integer) mh.invokeExact(new Integer(-123)));
	}

	@Test
	public void getterSetterTest() throws Throwable {
		Point p = new Point();
		MethodHandle mh = MethodHandles.lookup().findSetter(Point.class, "x",
				int.class);
		mh.invoke(p, 12);
		mh = MethodHandles.lookup().findSetter(Point.class, "y", int.class);
		mh.invoke(p, 21);

		mh = MethodHandles.lookup().findGetter(Point.class, "x", int.class);
		assertEquals(new Integer(12), mh.invoke(p));
		mh = MethodHandles.lookup().findGetter(Point.class, "y", int.class);
		assertEquals(new Integer(21), mh.invoke(p));
	}

	@Test
	public void argsTransformTest() throws Throwable {
		MethodHandle mh = MethodHandles.lookup()
				.findStatic(
						Math.class,
						"pow",
						MethodType.methodType(double.class, double.class,
								double.class));
		mh = MethodHandles.insertArguments(mh, 1, 10);
		assertEquals(1024.0, mh.invoke(2.0));

		mh = MethodHandles.insertArguments(mh, 0, 1);
		assertEquals(1.0, mh.invoke());
	}
}

class HW {
	public void hello1() {
		System.out.println("hello from hello1");
	}

	private void hello2() {
		System.out.println("hello from hello2");
	}
}

class Point {
	int x;
	int y;
}
