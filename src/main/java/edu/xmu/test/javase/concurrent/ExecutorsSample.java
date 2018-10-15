package edu.xmu.test.javase.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * Executor Framework: <br>
 * ExecutorService, Callable/Runnable, Future, Executors
 */
public class ExecutorsSample {

	public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
		ExecutorService es = Executors.newCachedThreadPool(Executors.defaultThreadFactory());
		futureSample(es);
	}

	/**
	 * future.get() is a blocking method, caller thread will pending until future.isDone() is true <br>
	 */
	private static void futureSample(ExecutorService es) throws InterruptedException, ExecutionException {
		Future<?> f = es.submit(new Runnable() {
			@Override
			public void run() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Hello");
			}
		});
		// while (!f.isDone()) {
		// // Main thread is busy waiting here
		// }
		System.out.println(f.get());
		System.out.println(f.isDone());

		f = es.submit(new Callable<String>() {
			@Override
			public String call() throws Exception {
				TimeUnit.SECONDS.sleep(1);
				return "Welcome";
			}
		});
		// while (!f.isDone()) {
		// // Main thread is busy waiting here
		// }
		System.out.println(f.get());
		System.out.println(f.isDone());
	}
}
