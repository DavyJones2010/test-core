package edu.xmu.test.jcip.ch01;

import java.util.concurrent.locks.ReentrantLock;

import org.junit.Ignore;
import org.junit.Test;

public class ReentrantLockTest {
	@Ignore
	@Test(timeout = 1000)
	public void test() throws InterruptedException {
		final DummyFile file = new DummyFile();
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep((long) (Math.random() * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					file.writeFile();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep((long) (Math.random() * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					file.writeFile();
				}
			}
		});
		Thread t3 = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					try {
						Thread.sleep((long) (Math.random() * 1000));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					file.writeFile();
				}
			}
		});
		t1.start();
		t2.start();
		t3.start();
		t1.join();
	}

	static class DummyFile {
		ReentrantLock lock = new ReentrantLock();

		public void writeFile() {
			lock.lock();
			System.out.println(Thread.currentThread() + " got lock");
			try {
				Thread.sleep(500);
				System.out.println(Thread.currentThread() + " is writeFile");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				System.out.println(Thread.currentThread() + " released lock");
				lock.unlock();
			}
		}
	}
}
