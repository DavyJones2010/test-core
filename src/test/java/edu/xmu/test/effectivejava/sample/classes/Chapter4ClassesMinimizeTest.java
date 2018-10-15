package edu.xmu.test.effectivejava.sample.classes;

import java.util.Date;

import org.junit.Test;

public class Chapter4ClassesMinimizeTest {
	@Test
	public void minimizeCouplingTest() {
	}

	@Test
	public void minimizeAccessibilityTest() {
		// private->package private->protected->public
	}

	static class Person {
		// better import java.util.Date instead of java.sql.Date
		Date birthday;
	}
}
