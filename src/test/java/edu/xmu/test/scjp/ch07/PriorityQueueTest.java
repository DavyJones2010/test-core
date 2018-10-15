package edu.xmu.test.scjp.ch07;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.PriorityQueue;

import org.junit.Test;

import com.google.common.collect.Queues;

public class PriorityQueueTest {
	/**
	 * Elements in PriorityQueue are asc sorted(Only when we poll elements
	 * sequentially).<br/>
	 * queue.toArray() returns the unsorted array!
	 */
	@Test
	public void offerTest() {
		PriorityQueue<Integer> queue = Queues.newPriorityQueue();
		int[] a = { 1, 5, 3, 7, 6, 9, 8 };
		for (int i : a) {
			queue.offer(i);
		}
		assertArrayEquals(new Integer[] { 1, 5, 3, 7, 6, 9, 8 },
				queue.toArray());

		queue.poll();
		assertEquals(new Integer(3), queue.poll()); // sorted
	}

	/**
	 * ' ' < 'F' < 'f'
	 */
	@Test
	public void offerTest2() {
		PriorityQueue<String> queue = Queues.newPriorityQueue();
		queue.offer(">ff<");
		queue.offer(">FF<");
		queue.offer("> f<");
		queue.offer(">f <");

		assertEquals("> f<", queue.poll());
		assertEquals(">FF<", queue.poll());
		assertEquals(">f <", queue.poll());
		assertEquals(">ff<", queue.poll());
	}
}
