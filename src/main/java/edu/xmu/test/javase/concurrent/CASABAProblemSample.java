package edu.xmu.test.javase.concurrent;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * {@link <a href="https://en.wikipedia.org/wiki/ABA_problem">ABA Problem</a>}
 */
public class CASABAProblemSample {

	public static void main(String[] args) throws Exception {
		AtomicInteger i = new AtomicInteger(100);
		Thread t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				i.compareAndSet(100, 101);
				try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				i.compareAndSet(101, 100);
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean b = i.compareAndSet(100, 101);
				System.out.println(b);
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(i.get());

		AtomicStampedReference<Integer> j = new AtomicStampedReference<>(100, 0);
		t1 = new Thread(new Runnable() {
			@Override
			public void run() {
				j.compareAndSet(100, 101, j.getStamp(), j.getStamp() + 1);
				try {
					Thread.sleep(10L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				j.compareAndSet(101, 100, j.getStamp(), j.getStamp() + 1);
			}
		});
		t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				int originalStamp = j.getStamp();
				try {
					Thread.sleep(100L);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				boolean b = j.compareAndSet(100, 101, originalStamp, originalStamp + 1);
				System.out.println(b);
			}
		});
		t1.start();
		t2.start();
		t1.join();
		t2.join();
		System.out.println(j.getReference());
	}
}
