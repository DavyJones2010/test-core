package edu.xmu.test.scjp.ch09;

import org.junit.Ignore;
import org.junit.Test;

public class DeadLockTest {
	Resource a = new Resource();
	Resource b = new Resource();

	private static class Resource {
	}

	public void read() throws InterruptedException {
		synchronized (a) {
			Thread.sleep((long) (1000 * Math.random()));
			synchronized (b) {
				System.out.println("read");
			}
		}
	}

	public void write() throws InterruptedException {
		synchronized (b) {
			Thread.sleep((long) (1000 * Math.random()));
			synchronized (a) {
				System.out.println("write");
			}
		}
	}

	@Ignore("DeadLock")
	@Test
	public void deadLockTest() throws InterruptedException {
		Thread readThread = new Thread() {
			@Override
			public void run() {
				try {
					read();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		Thread writeThread = new Thread() {
			@Override
			public void run() {
				try {
					write();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		};
		readThread.start();
		writeThread.start();
		readThread.join();
		writeThread.join();
	}
}
