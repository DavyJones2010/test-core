package edu.xmu.test.scjp.ch09;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class SynchronizedTest {

	private class NonThreadSafeNameList {
		private List<String> names = Collections
				.synchronizedList(new LinkedList<String>());

		public void add(String name) {
			names.add(name);
		}

		public String removeFirst() {
			// Not thread safe
			if (!names.isEmpty()) {
				System.out.println(Thread.currentThread()
						+ " start removeFirst()");
				try {
					Thread.sleep((long) (Math.random() * 1000));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return names.remove(0);
			} else {
				return null;
			}
		}
	}

	private static class ThreadSafeNameList {
		private List<String> names = new LinkedList<String>();

		public synchronized void add(String name) {
			names.add(name);
		}

		public synchronized String removeFirst() {
			return names.get(0);
		}
	}

	/**
	 * IndexOutOfBoundsException may be thrown by t1 or t2
	 */
	@Test
	public void notThreadSafeTest() throws InterruptedException {
		final NonThreadSafeNameList list = new NonThreadSafeNameList();
		list.add("AAA");

		Thread t1 = new Thread() {
			@Override
			public void run() {
				list.removeFirst();
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				list.removeFirst();
			}
		};
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}

	/**
	 * IndexOutOfBoundsException will never be thrown
	 */
	@Test
	public void threadSafeTest() throws InterruptedException {
		final ThreadSafeNameList list = new ThreadSafeNameList();
		list.add("AAA");

		Thread t1 = new Thread() {
			@Override
			public void run() {
				list.removeFirst();
			}
		};
		Thread t2 = new Thread() {
			@Override
			public void run() {
				list.removeFirst();
			}
		};
		t1.start();
		t2.start();
		t1.join();
		t2.join();
	}
}
