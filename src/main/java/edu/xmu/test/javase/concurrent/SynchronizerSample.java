package edu.xmu.test.javase.concurrent;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 1> CountDownLatch <br>
 * 2> CyclicBarrier <br>
 * 3> Exchanger <br>
 * 4> Locks: ReentrantLock, ReadLock, WriteLock <br>
 *
 */
public class SynchronizerSample {
	public static void main(String[] args) {
		// cyclicBarrierSample();
		// countDownLatchSample();
		// reentrantLockSample();
		// reentrantReaderWriterSample();
	}

	static void reentrantReaderWriterSample() {
		ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
		Object res = new Object();

		Thread r = new Thread(new Reader(lock, res), "Reader-1");
		Thread r2 = new Thread(new Reader(lock, res), "Reader-2");
		Thread r3 = new Thread(new Reader(lock, res), "Reader-3");
		Thread w = new Thread(new Writer(lock, res), "Writer-1");

		r.start();
		r2.start();
		r3.start();
		w.start();
	}

	static class Reader implements Runnable {
		ReentrantReadWriteLock lock;
		Object res;

		public Reader(ReentrantReadWriteLock lock, Object res) {
			super();
			this.lock = lock;
			this.res = res;
		}

		@Override
		public void run() {
			while (true) {
				lock.readLock().lock();
				try {
					System.out.println(Thread.currentThread() + " start reading");
					TimeUnit.SECONDS.sleep(new Random().nextInt(3));
					System.out.println(Thread.currentThread() + " finished reading");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.readLock().unlock();
			}
		}
	}

	static class Writer implements Runnable {
		ReentrantReadWriteLock lock;
		Object res;

		public Writer(ReentrantReadWriteLock lock, Object res) {
			super();
			this.lock = lock;
			this.res = res;
		}

		@Override
		public void run() {
			while (true) {
				lock.writeLock().lock();
				try {
					System.out.println(Thread.currentThread() + " start writing");
					TimeUnit.SECONDS.sleep(new Random().nextInt(6));
					System.out.println(Thread.currentThread() + " finished writing");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				lock.writeLock().unlock();
			}
		}
	}

	static void reentrantLockSample() {
		ReentrantLock lock = new ReentrantLock();
		Thread thread = new Thread(new SimpleReader(lock), "Reader-1");
		Thread thread2 = new Thread(new SimpleReader(lock), "Reader-2");
		Thread thread3 = new Thread(new SimpleReader(lock), "Reader-3");

		thread.start();
		thread2.start();
		thread3.start();
	}

	/**
	 * Once a thread get lock in methodA(), it won't need to get the lock in methodB() <br>
	 * And this is what ReentrantLock means <br>
	 */
	static class SimpleReader implements Runnable {
		ReentrantLock lock;

		public SimpleReader(ReentrantLock lock) {
			super();
			this.lock = lock;
		}

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(new Random().nextInt(3));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				methodA();
			}
		}

		void methodA() {
			lock.lock();
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(3));
				System.out.println(Thread.currentThread() + " is running in methodA");
				methodB();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}

		void methodB() {
			lock.lock();
			try {
				TimeUnit.SECONDS.sleep(new Random().nextInt(3));
				System.out.println(Thread.currentThread() + " is running in methodB");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lock.unlock();
		}
	}

	static void countDownLatchSample() {
		CountDownLatch latch = new CountDownLatch(2);
		Thread t1 = new Thread(new Producer(latch), "Producer-1");
		Thread t2 = new Thread(new Producer(latch), "Producer-2");
		Thread c1 = new Thread(new Consumer(latch), "Consumer-1");
		c1.start();
		t1.start();
		t2.start();
	}

	static class Producer implements Runnable {
		CountDownLatch latch;

		public Producer(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				TimeUnit.SECONDS.sleep(1L);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + " produced");
			latch.countDown();
		}

	}

	static class Consumer implements Runnable {
		CountDownLatch latch;

		public Consumer(CountDownLatch latch) {
			super();
			this.latch = latch;
		}

		@Override
		public void run() {
			try {
				latch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread() + " start consuming");
		}

	}

	static void cyclicBarrierSample() {
		CyclicBarrier barrier = new CyclicBarrier(3);
		Thread runner = new Thread(new Runner(barrier), "Runner-1");
		Thread runner2 = new Thread(new Runner(barrier), "Runner-2");
		Thread runner3 = new Thread(new Runner(barrier), "Runner-3");
		runner.start();
		runner2.start();
		runner3.start();
	}

	static class Runner implements Runnable {
		CyclicBarrier barrier;

		public Runner(CyclicBarrier barrier) {
			super();
			this.barrier = barrier;
		}

		@Override
		public void run() {
			while (true) {
				try {
					TimeUnit.SECONDS.sleep(new Random().nextInt(2));
					System.out.println(Thread.currentThread() + " is ready to run");
					barrier.await();
					TimeUnit.SECONDS.sleep(1L);
					System.out.println(Thread.currentThread() + " start running");
				} catch (InterruptedException | BrokenBarrierException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
