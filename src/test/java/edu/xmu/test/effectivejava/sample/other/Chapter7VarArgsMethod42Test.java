package edu.xmu.test.effectivejava.sample.other;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.google.common.primitives.Ints;

public class Chapter7VarArgsMethod42Test {

	@Test
	public void varArgsTest() {
		/* *
		 * It is legal we don't pass in any param to var args method. It doesn't even enforce us to pass in any param
		 */
		assertEquals(0, sum());
	}

	@Test
	public void primeTypeVarArgsTest() {
		int[] values = { 1, 2, 3, 4 };
		// Compiliation error!
		// List<Integer> ints = Arrays.asList(values);
		List<int[]> vals = Arrays.asList(values);
		assertEquals(1, vals.size());

		// Guava has provided some util methods for this
		List<Integer> ints = Ints.asList(values);
		assertEquals(4, ints.size());
	}

	@Test
	public void printArrayTest() {
		// It is recommanded to use Arrays.toString() to print array
		int[] values = { 1, 2, 3, 4 };
		System.out.println(Arrays.toString(values));
	}

	@Test
	public void reallocateSpaceTest() {
		int[] values = { 1, 2, 3, 4 };
		System.out.println(values);
		// The orignial data will be passed in
		reallocateSpace(values);

		// A new array will be created every time we invoke this method
		reallocateSpace(1, 2, 3, 4);
		reallocateSpace(1, 2, 3, 4);
	}

	@Test
	public void reallocateSpacePerformanceTest() {
		int[] values = { 1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		long start = System.currentTimeMillis();
		for (int i = 0; i < 100000; i++) {
			nonReallocateSpace(values);
		}
		System.out.println("Non-VarArgs cost: " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();

		for (int i = 0; i < 100000; i++) {
			reallocateSpace(1, 2, 3, 4, 5, 6, 7, 0, 1, 2, 3, 4, 5, 6, 7, 9, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		}
		System.out.println("VarArgs cost: " + (System.currentTimeMillis() - start));
	}

	private void reallocateSpace(int... values) {
//		System.out.println(values);
	}

	private void nonReallocateSpace(int[] values) {
//		System.out.println(values);
	}

	// private int sum(int[] args) {
	private int sum(int... args) {
//		sum(null);
		System.out.println(args.length);
		int sum = 0;
		for (int arg : args) {
			sum += arg;
		}
		return sum;
	}
}
