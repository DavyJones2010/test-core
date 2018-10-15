package edu.xmu.test.javase.util;

import java.util.Date;
import java.util.concurrent.atomic.AtomicBoolean;

public class A {
	B b;
	static AtomicBoolean init = new AtomicBoolean(false);

	public void init() {
		if (!init.compareAndSet(false, true)) {
			return;
		}
		b = new B();
	}

	public String getX() {
		return b.getX();
	}

	public static void main(String[] args) {
//		A a = new A();
//		A b = new A();
//		a.init();
//		b.init();
//		System.out.println(b.getX());
		
//		Environment e = Environment.values()[2];
//		System.out.println(e);
		
		Date d = new Date(1463715607152L);
		System.out.println(d);
	}
}

class B {
	public String getX() {
		return "B";
	}
}
