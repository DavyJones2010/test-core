package edu.xmu.test.forkjoin;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.collect.Lists;

public class ForkJoinTest2 {
	@Test
	public void forkJoinTest2() throws InterruptedException {
		List<Integer> data = Lists.newArrayList();
		for (int i = 0; i < 1000000; i++) {
			data.add(i);
		}
		ForkJoinPool pool = new ForkJoinPool();
		SumCalculator calculator = new SumCalculator(data);
		pool.execute(calculator);
		while (!calculator.isDone()) {
			do {
				System.out
						.printf("******************************************\n");
				System.out.printf("Main: Parallelism: %d\n",
						pool.getParallelism());
				System.out.printf("Main: Active Threads: %d\n",
						pool.getActiveThreadCount());
				System.out.printf("Main: Task Count: %d\n",
						pool.getQueuedTaskCount());
				System.out.printf("Main: Steal Count: %d\n",
						pool.getStealCount());
				System.out
						.printf("******************************************\n");
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			} while (!calculator.isDone());
		}
		pool.shutdown();
		int value = calculator.join();
		System.out.println("Result: " + value);
	}

	public static class SumCalculator extends RecursiveTask<Integer> {
		private static final long serialVersionUID = 1L;

		private List<Integer> data;

		public SumCalculator(List<Integer> data) {
			super();
			this.data = data;
		}

		@Override
		protected Integer compute() {
			List<SumCalculator> calculators = Lists.newArrayList();
			int sum = 0;
			if (data.size() > 20) {
				List<List<Integer>> datas = Lists.partition(data, 20);
				for (List<Integer> data : datas) {
					SumCalculator calculator = new SumCalculator(data);
					calculator.fork();
					calculators.add(calculator);
					sum += calculator.join();
				}
			} else {
				for (int val : data) {
					sum += val;
				}
			}
			return sum;
		}
	}

}