package edu.xmu.test.scjp.ch09;

import org.junit.Test;

public class StringGeneratorTest {

	@Test
	public void synchronizeBlockTest() throws InterruptedException {
		StringBuilder sb = new StringBuilder();
		Thread t1 = new Thread(new MyRunnable('A', sb));
		Thread t2 = new Thread(new MyRunnable('B', sb));
		Thread t3 = new Thread(new MyRunnable('C', sb));
		t1.start();
		t2.start();
		t3.start();
		t1.join();
		t2.join();
		t3.join();
		System.out.println(sb.toString());
	}

	private static class MyRunnable implements Runnable {
		char c;
		StringBuilder sb;

		public MyRunnable(char c, StringBuilder sb) {
			super();
			this.c = c;
			this.sb = sb;
		}

		@Override
		public void run() {
			synchronized (sb) {
				for (int i = 0; i < 20; i++) {
					sb.append(c);
				}
			}
		}
	}

}
