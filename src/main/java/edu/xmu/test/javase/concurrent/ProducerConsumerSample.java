package edu.xmu.test.javase.concurrent;

import java.util.List;

import com.google.common.collect.Lists;

public class ProducerConsumerSample {
	static class Consumer implements Runnable {
		Object lock;
		List<String> pipeline;

		public Consumer(Object lock, List<String> pipeline) {
			super();
			this.lock = lock;
			this.pipeline = pipeline;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (lock) {
					while (pipeline.isEmpty()) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(Thread.currentThread() + " is consuming " + pipeline.remove(0));
					lock.notifyAll();
				}
			}
		}
	}

	static class Producer implements Runnable {
		Object lock;
		List<String> pipeline;

		public Producer(Object lock, List<String> pipeline) {
			super();
			this.lock = lock;
			this.pipeline = pipeline;
		}

		@Override
		public void run() {
			while (true) {
				synchronized (lock) {
					while (!pipeline.isEmpty()) {
						try {
							lock.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					System.out.println(Thread.currentThread() + " is producing " + pipeline.add("GOOD"));
					// because if we only notify(), we may notify another producer, which will make it in wait() again, and no consumer is notified
					// program will be stucked
					lock.notifyAll();
				}
			}
		}
	}

	/**
	 * Multiple Producer & Multiple Consumer
	 */
	public static void main(String[] args) throws InterruptedException {
		Object lock = new Object();
		List<String> pipeline = Lists.newArrayList();
		Thread p = new Thread(new Producer(lock, pipeline), "Producer-1");
		Thread p2 = new Thread(new Producer(lock, pipeline), "Producer-2");
		Thread c1 = new Thread(new Consumer(lock, pipeline), "Consumer-1");
		Thread c2 = new Thread(new Consumer(lock, pipeline), "Consumer-2");
		Thread c3 = new Thread(new Consumer(lock, pipeline), "Consumer-3");

		c1.start();
		Thread.sleep(100L);
		c2.start();
		Thread.sleep(100L);
		c3.start();
		Thread.sleep(100L);
		p.start();
		Thread.sleep(100L);
		p2.start();
	}
}
