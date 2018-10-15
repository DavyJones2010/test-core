package edu.xmu.test.javase.concurrent;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1> BlockingQueue: LinkedBlockingQueue, ArrayBlockingQueue, SynchronousQueue <br>
 * 2> ConcurrentHashMap, CopyOnWriteArrayList <br>
 */
public class ConcurrentCollectionSample {
	public static void main(String[] args) throws InterruptedException {
		blockingQueueSample();
		concurrentMapSample();
		concurrentListSample();
		TimeUnit.SECONDS.sleep(5L);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("System is shutting down...");
			}
		});
		System.exit(0);
	}

	/**
	 * SourceCode: add() will first lock the instance, then create a new internal array <br>
	 * invoke System.arrayCopy() to populate the internal array, then release the lock <br>
	 */
	static void concurrentListSample() {
		new CopyOnWriteArrayList<>();
	}

	/**
	 * Add lock on the segment instead of the whole map <br>
	 */
	static void concurrentMapSample() {
		new ConcurrentHashMap<>();
	}

	static void blockingQueueSample() {
		/*
		 * If there is no data in blocking queue, queue.take() will be blocked, if queue is full, queue.put() will be blocked <br>
		 * BlockingQueue is internally realized by using synchronizer & arrays
		 */
		BlockingQueue<String> q = new ArrayBlockingQueue<>(1);
		Thread p1 = new Thread(new Producer(q), "Producer-1");
		Thread p2 = new Thread(new Producer(q), "Producer-2");
		Thread p3 = new Thread(new Producer(q), "Producer-3");

		Thread c1 = new Thread(new Consumer(q), "Consumer-1");
		Thread c2 = new Thread(new Consumer(q), "Consumer-2");

		p1.start();
		p2.start();
		p3.start();
		c1.start();
		c2.start();

		q = new LinkedBlockingQueue<>(1);
		p1 = new Thread(new Producer(q), "Producer-4");
		p2 = new Thread(new Producer(q), "Producer-5");
		p3 = new Thread(new Producer(q), "Producer-6");

		c1 = new Thread(new Consumer(q), "Consumer-3");
		c2 = new Thread(new Consumer(q), "Consumer-4");

		p1.start();
		p2.start();
		p3.start();
		c1.start();
		c2.start();

		/*
		 * SynchronousQueue is a special blocking queue with fixed size: 0 <br>
		 * Producer have to wait for consumer, and consumer have to wait for producer <br>
		 * producer.put() and consumer.take() happens exactly at the same time <br>
		 * The output: "current queue size: 0" means consumer consumes the string exactly when producer produced the string <br>
		 * SynchronousQueue is used in java.util.concurrent.Executors.newCachedThreadPool()
		 */
		q = new SynchronousQueue<>();
		p1 = new Thread(new Producer(q), "Producer-7");
		p2 = new Thread(new Producer(q), "Producer-8");
		p3 = new Thread(new Producer(q), "Producer-9");

		c1 = new Thread(new Consumer(q), "Consumer-5");
		c2 = new Thread(new Consumer(q), "Consumer-6");

		p1.start();
		p2.start();
		p3.start();
		c1.start();
		c2.start();
	}

	static class Producer implements Runnable {
		BlockingQueue<String> q;

		public Producer(BlockingQueue<String> q) {
			super();
			this.q = q;
		}

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				try {
					TimeUnit.SECONDS.sleep(new Random(2).nextInt());
					q.put("GOOD");
					System.out.println(Thread.currentThread() + " produced GOOD, current queue size: " + q.size());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	static class Consumer implements Runnable {
		BlockingQueue<String> q;

		public Consumer(BlockingQueue<String> q) {
			super();
			this.q = q;
		}

		@Override
		public void run() {
			for (int i = 0; i < 30; i++) {
				try {
					System.out.println(Thread.currentThread() + " consumed: " + q.take());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
