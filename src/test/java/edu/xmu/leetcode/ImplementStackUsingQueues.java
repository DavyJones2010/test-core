package edu.xmu.leetcode;

import java.util.LinkedList;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/implement-stack-using-queues/">Implement Stack using Queues</a>} <br>
 */
public class ImplementStackUsingQueues {

	static class MyStack {
		LinkedList<Integer> queue1 = new LinkedList<Integer>();
		LinkedList<Integer> queue2 = new LinkedList<Integer>();
		LinkedList<Integer> pushQueue = queue1;
		LinkedList<Integer> popQueue = queue2;
		int top;

		// Push element x onto stack.
		public void push(int x) {
			pushQueue.addLast(x);
			top = x;
		}

		// Removes the element on top of the stack.
		public void pop() {
			int pushSize = pushQueue.size();
			if (0 == pushSize) {
				return;
			} else if (1 == pushSize) {
				pushQueue.removeFirst();
				top = Integer.MIN_VALUE;
			} else {
				for (int i = 0; i < pushSize - 2; i++) {
					popQueue.addLast(pushQueue.removeFirst());
				}
				top = pushQueue.removeFirst();
				popQueue.addLast(top);
				pushQueue.removeFirst();
				LinkedList<Integer> tmpQueue = popQueue;
				popQueue = pushQueue;
				pushQueue = tmpQueue;
			}
		}

		// Get the top element.
		public int top() {
			return top;
		}

		// Return whether the stack is empty.
		public boolean empty() {
			return popQueue.isEmpty() && pushQueue.isEmpty();
		}
	}

	@Test
	public void climbStairsTest() {
		MyStack stack = new MyStack();
		stack.push(1);
		stack.push(2);
		System.out.println(stack.top());
		stack.pop();
		System.out.println(stack.top());
		System.out.println(stack.empty());
		stack.pop();
		System.out.println(stack.top());
		System.out.println(stack.empty());
		stack.push(3);
		System.out.println(stack.top());
		System.out.println(stack.empty());
		stack.pop();
		System.out.println(stack.top());
		System.out.println(stack.empty());
	}
}
