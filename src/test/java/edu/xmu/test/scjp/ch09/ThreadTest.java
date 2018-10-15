package edu.xmu.test.scjp.ch09;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Test;

public class ThreadTest {
	private static final Logger logger = Logger.getLogger(ThreadTest.class);

	/**
	 * A thread can be created by: Thread(), Thread(String), Thread(Runnable),
	 * Thread(Runnable, String)
	 */
	@Test
	public void createThreadTest() {
		Thread t = new Thread() {
			@Override
			public void run() {
				logger.info("Thread: " + this.getName() + " run");
			}
		};
		t.start();

		t = new Thread("MyThread") {
			@Override
			public void run() {
				logger.info("Thread: " + this.getName() + " run");
			}
		};
		t.start();

		t = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Thread: " + Thread.currentThread().getName()
						+ " run");
			}
		});
		t.start();

		t = new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Thread: " + Thread.currentThread().getName()
						+ " run");
			}
		}, "MyThread");
		t.start();
	}

	/**
	 * Once a thread has been started, it cannot be restarted again!
	 */
	@Test(expected = IllegalThreadStateException.class)
	public void restartTest() throws InterruptedException {
		Thread t = new Thread(new MyRunnable(), "MyRunnable");
		t.start();
		t.join();
		System.out.println("Thread " + t.getName() + " finished execution");
		t.start();// Exception thrown here!
	}

	/**
	 * <pre>
	 * 		waiting/blocking/sleeping
	 * 			|			^
	 * 			V			|
	 * new -> runnable <-> running -> dead
	 * </pre>
	 * 
	 */
	@SuppressWarnings("static-access")
	@Test
	public void threadStateTransitionTest() throws InterruptedException {
		Thread.sleep(1000L); // Causes current thread to sleeping state
		Thread.yield(); // May change current thread to runnable/waiting state

		Thread t = new Thread(new MyRunnable(), "MyRunnable");
		t.start();
		/*
		 * Will not cause "MyRunnable" but CurrentThread to sleep/runnable
		 * state. They are static method! They don't affect the instance t!
		 */
		t.sleep(1000L);
		t.yield();
		Object lock = new Object();
		/*
		 * The compete for object lock can also cause a thread to blocking
		 * state.
		 */
		synchronized (lock) {
			System.out.println("In critical zone");
			/*
			 * This method will wake up all the threads which are blocked when
			 * attempting to the the lock. If we do not call this method, all
			 * the blocking threads will never be wake up and transit to
			 * runnable state.
			 */
			lock.notifyAll();
		}
	}

	/**
	 * DO NOT USE "stop()", "suspend()" or "resume()"! They are deprecated and
	 * highly dangerous.
	 */
	@Test
	public void stopSuspendAndResumeTest() {
	}

	/**
	 * If a thread is interrupted while executing wait(), its interrupted status
	 * would be cleared(set to false), otherwise, it would be set as true.
	 * 
	 * @throws InterruptedException
	 */
	@Test
	public void interruptTest() throws InterruptedException {
		Thread t = new Thread(new MySleepRunnable(), "MySleepRunnable");
		t.start();
		t.interrupt();
		Thread.sleep(10L);
		assertFalse(t.isInterrupted());

		t = new Thread(new MyInterruptableRunnable(), "MyInterruptableRunnable");
		t.start();
		t.interrupt();
		Thread.sleep(10L);
		assertTrue(t.isInterrupted());
	}

	public static void main(String[] args) throws InterruptedException {
		Thread t = new Thread(new MySleepRunnable(), "MySleepRunnable");
		t.start();
		t.interrupt();
		Thread.sleep(10L);
		logger.info("Thread: " + t.getName() + ", isInterrupted: "
				+ t.isInterrupted());
		logger.info("Thread: " + Thread.currentThread().getName()
				+ ", isInterrupted: " + Thread.currentThread().isInterrupted());

		t = new Thread(new MyInterruptableRunnable(), "MyInterruptableRunnable");
		t.start();
		t.interrupt();
		Thread.sleep(10L);
		logger.info("Thread: " + t.getName() + ", isInterrupted: "
				+ t.isInterrupted());
		logger.info("Thread: " + Thread.currentThread().getName()
				+ ", isInterrupted: " + Thread.currentThread().isInterrupted());
		System.exit(0);
	}

	private static class MyRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("Hallo world!");
		}
	}

	private static class MySleepRunnable implements Runnable {
		@Override
		public void run() {
			System.out.println("Start MySleepRunnable!");
			try {
				Thread.sleep(100000L);
			} catch (InterruptedException e) {
				logger.error("Interrupted, Thread: "
						+ Thread.currentThread().getName()
						+ ", isInterrupted: "
						+ Thread.currentThread().isInterrupted(), e);
			}
		}
	}

	private static class MyInterruptableRunnable implements Runnable {
		@Override
		public void run() {
			while (true) {
			}
		}
	}
}