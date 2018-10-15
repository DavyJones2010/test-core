package edu.xmu.leetcode;

import java.util.Stack;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/implement-queue-using-stacks/">Implement Queue using Stacks</a>}
 *
 */
public class ImplementQueueUsingStack {
	static class MyQueue {
		Stack<Integer> inStack = new Stack<Integer>();
		Stack<Integer> outStack = new Stack<Integer>();
		int head = Integer.MIN_VALUE;

		// Push element x to the back of queue.
		public void push(int x) {
			if (inStack.isEmpty() && outStack.isEmpty()) {
				head = x;
			}
			if (!outStack.isEmpty()) {
				head = outStack.peek();
			}
			while (!outStack.isEmpty()) {
				inStack.push(outStack.pop());
			}
			inStack.push(x);
		}

		// Removes the element from in front of queue.
		public void pop() {
			while (!inStack.isEmpty()) {
				outStack.push(inStack.pop());
			}
			outStack.pop();
			if (!outStack.isEmpty()) {
				head = outStack.peek();
			} else {
				head = Integer.MIN_VALUE;
			}
		}

		// Get the front element.
		public int peek() {
			return head;
		}

		// Return whether the queue is empty.
		public boolean empty() {
			return outStack.isEmpty() && inStack.isEmpty();
		}
	}

	@Test
	public void test() {
		MyQueue queue = new MyQueue();
		queue.push(1);
		queue.push(2);
		queue.push(3);
		queue.push(4);

		System.out.println(queue.peek());
		queue.pop();
		System.out.println(queue.peek());
		queue.pop();
		System.out.println(queue.peek());
		queue.pop();
		System.out.println(queue.peek());
		queue.pop();
		System.out.println(queue.empty());
	}
}
