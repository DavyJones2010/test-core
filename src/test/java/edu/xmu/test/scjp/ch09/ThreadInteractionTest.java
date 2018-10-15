package edu.xmu.test.scjp.ch09;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Object.wait(), Object.notify(), Object.notifyAll()<br/>
 * They must be called from within a synchronized context! A thread can't invoke
 * a wait() or notify() on an object unless it owns that object's lock.
 */
public class ThreadInteractionTest {
	@Test(expected = IllegalMonitorStateException.class)
	public void illegalWaitTest() throws InterruptedException {
		Object lock = new Object();
		lock.wait();
	}

	@Test(expected = IllegalMonitorStateException.class)
	public void illegalNotifyTest() throws InterruptedException {
		Object lock = new Object();
		lock.notify();
	}

	@Test(expected = IllegalMonitorStateException.class)
	public void illegalNotifyAllTest() throws InterruptedException {
		Object lock = new Object();
		lock.notifyAll();
	}

	@Test(timeout=1000)
	@Ignore
	public void waitTest() throws InterruptedException {
		Object lock = new Object();
		Thread t1 = new Thread(new MyRunnable(lock));
		Thread t2 = new Thread(new MyRunnable(lock));
		Thread t3 = new Thread(new MyRunnable(lock));
		Thread t4 = new Thread(new MyRunnable(lock));

		t1.start();
		t2.start();
		t3.start();
		t4.start();

		t1.join();
		t2.join();
		t3.join();
		t4.join();
	}

	private static class MyRunnable implements Runnable {
		Object lock;

		public MyRunnable(Object lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			for (int i = 0; i < 5; i++) {
				System.out.println(Thread.currentThread()
						+ " attempts to get lock");
				synchronized (lock) {
					System.out.println(Thread.currentThread() + " got lock");
					try {
						Thread.sleep((long) (Math.random() * 1000));
						System.out.println(Thread.currentThread()
								+ " released lock");
						lock.notifyAll();
						lock.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println(Thread.currentThread()
						+ " finished execution");
			}
		}
	}

	/**
	 * yield's purpose is to give threads of EQUAL PRIORITY a chance to running
	 * status and its own status would become runnable
	 */
	@Test
	public void yieldTest() {
		Thread t1 = new Thread(new YieldRunnable(), "T1");
		Thread t2 = new Thread(new YieldRunnable(), "T2");
		Thread t3 = new Thread(new YieldRunnable(), "T3");
		t1.start();
		t2.start();
		t3.start();
	}

	private static final class YieldRunnable implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 10; i++) {
				System.out
						.println("Thread: " + Thread.currentThread().getName()
								+ ", count to: " + i);
				Thread.yield();
			}
		}
	}
}
