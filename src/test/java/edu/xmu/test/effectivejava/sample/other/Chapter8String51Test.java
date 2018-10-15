package edu.xmu.test.effectivejava.sample.other;

import org.junit.Test;

public class Chapter8String51Test {
	@Test
	public void stringConcatTest() {
		long start = System.currentTimeMillis();

		String str = "";
		for (int i = 0; i < 10000; i++) {
			str += "+";
		}
		System.out.println("String add: " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		str = "";
		for (int i = 0; i < 10000; i++) {
			str = new StringBuilder(str).append("+").toString();
		}
		System.out.println("String add internal: " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		str = "";
		for (int i = 0; i < 10000; i++) {
			str = str.concat("+");
		}
		System.out.println("String concat: " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < 10000; i++) {
			sb.append("+");
		}
		sb.toString();
		System.out.println("String builder: " + (System.currentTimeMillis() - start));
	}

	@SuppressWarnings("unused")
	@Test
	public void multipleConcatTest() {
		String str = "";
		str = "A" + "B" + "C" + "D";

		str = "";
		str = new StringBuilder("A").append("B").append("C").append("D").toString();
	}
}
