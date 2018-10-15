package edu.xmu.translate.invoke;

import static org.junit.Assert.assertEquals;

import java.lang.invoke.ConstantCallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;

import org.junit.Test;

public class CallSiteTest {

    @Test
    public void constantCallSiteTest() throws Throwable {
	MethodHandle methodHandle = MethodHandles.lookup().findVirtual(
		String.class, "substring",
		MethodType.methodType(String.class, int.class, int.class));
	ConstantCallSite callSite = new ConstantCallSite(methodHandle);
	MethodHandle handle = callSite.dynamicInvoker();
	assertEquals("l", handle.invoke("Hello", 2, 3));
    }

    @Test
    public void mutableCallSiteTest() throws Throwable {
	MethodType methodType = MethodType.methodType(int.class, int.class,
		int.class);
	MutableCallSite mutableCallSite = new MutableCallSite(methodType);
	MethodHandle invoker = mutableCallSite.dynamicInvoker();
	mutableCallSite.setTarget(MethodHandles.lookup().findStatic(Math.class,
		"min", methodType));
	assertEquals(-1, invoker.invoke(-1, 1));

	mutableCallSite.setTarget(MethodHandles.lookup().findStatic(Math.class,
		"max", methodType));
	assertEquals(1, invoker.invoke(-1, 1));
    }
}
