package edu.xmu.test.javase.javapuzzlers;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.junit.Test;

import edu.xmu.test.javase.javapuzzlers.sub.API;
import edu.xmu.test.javase.javapuzzlers.sub.API.SampleInterface;

public class Puzzler78Reflection {

	@Test(expected = java.lang.IllegalAccessException.class)
	public void test() throws Exception {
		Set<String> s = new HashSet<String>();
		s.add("foo");
		// it is actually with type of: java.util.HashMap.KeyIterator<K, V> which is package private
		// therefore we cannot access this class here.
		Iterator<String> it = s.iterator();
		Method method = it.getClass().getMethod("hasNext");
		System.out.println(method.invoke(it));
	}

	@Test(expected = java.lang.IllegalAccessException.class)
	public void test2() throws Exception {
		System.out.println(API.member.hashCode());
		SampleInterface a = API.member;
		Method method = a.getClass().getMethod("hashCode");
		System.out.println(method.invoke(a));
	}
}
