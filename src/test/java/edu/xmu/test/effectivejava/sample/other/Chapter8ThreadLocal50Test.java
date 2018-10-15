package edu.xmu.test.effectivejava.sample.other;

import org.junit.Test;

import com.google.common.base.Preconditions;

public class Chapter8ThreadLocal50Test {
	static ThreadLocal<String> t = new ThreadLocal<String>();

	@Test
	public void threadLocalTest() throws InterruptedException {
		Thread t1 = new Thread(new LocalThread());
		Thread t2 = new Thread(new LocalThread());
		Thread t3 = new Thread(new LocalThread());
		Thread t4 = new Thread(new LocalThread());

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();
	}

	private static class LocalThread implements Runnable {
		@Override
		public void run() {
			t.set(Thread.currentThread().getName());
			try {
				Thread.sleep((long) (10000 * Math.random()));
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				Preconditions.checkArgument(Thread.currentThread().getName().equals(t.get()));
			}
		}
	}
}
