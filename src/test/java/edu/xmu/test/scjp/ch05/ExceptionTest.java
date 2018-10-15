package edu.xmu.test.scjp.ch05;

import java.io.FileNotFoundException;

import org.junit.Test;

public class ExceptionTest {
	@Test
	public void exceptionTest() throws Exception {
		try {
			System.out.println("A");
			throw new FileNotFoundException();
		} catch (FileNotFoundException e) {
			System.out.println("B");
			throw new Exception();
		} finally {
			System.out.println("C");
		}
		//System.out.println("D");
	}

	@SuppressWarnings("unused")
	@Test
	public void assertTest() {
		int a = -1;
		// assert a > 0;
		// assert a > 0;
	}
}
