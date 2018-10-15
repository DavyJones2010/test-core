package edu.xmu.leetcode;

import org.junit.Test;

public class MinStackTest {
	static final int INITIAL_CAPACITY = 89;
	int size = INITIAL_CAPACITY; // 0 based size
	int firstEmptyIndex = 0;
	int[] intArray = new int[INITIAL_CAPACITY];
	int min = Integer.MAX_VALUE;

	public void push(int x) {
		if (firstEmptyIndex == size) {
			size = size * 2;

			int[] newArray = new int[size];

			for (int i = 0; i < intArray.length; i++) {
				newArray[i] = intArray[i];
			}
			intArray = newArray;
		}
		intArray[firstEmptyIndex] = x;
		firstEmptyIndex++;
		if (x < min) {
			min = x;
		}

	}

	public void pop() {
		if (firstEmptyIndex == 0) {
			return;
		}
		int tmp = intArray[firstEmptyIndex - 1];
		firstEmptyIndex--;

		if (tmp == min) {
			min = Integer.MAX_VALUE;
			for (int i = 0; i < firstEmptyIndex; i++) {
				int value = intArray[i];
				if (value < min) {
					min = value;
				}
			}
		}
	}

	public int top() {
		return intArray[firstEmptyIndex - 1];
	}

	public int getMin() {
		return min;
	}

	@Test
	public void test() {
		MinStackTest stack = new MinStackTest();
		// stack.push(-15);
		// stack.push(1);
		// stack.push(2);
		// stack.push(3);
		// stack.push(4);
		// stack.push(5);
		// stack.push(6);
		// stack.push(-10);
		// stack.push(-20);
		// System.out.println(stack.getMin());
		// stack.pop();
		// System.out.println(stack.getMin());
		// System.out.println(stack.top());
		// stack.pop();
		// System.out.println(stack.top());
		stack.push(2);
		stack.push(0);
		stack.push(3);
		stack.push(0);
		stack.getMin();
		stack.pop();
		stack.getMin();
		stack.pop();
		stack.getMin();
		stack.pop();
		System.out.println(stack.getMin());
		// String str =
		// "push(2),push(0),push(3),push(0),getMin,pop,getMin,pop,getMin,pop,getMin";
		//
		// System.out.println(Joiner.on(';').join(
		// Lists.transform(Splitter.on(',').splitToList(str),
		// new Function<String, String>() {
		// @Override
		// public String apply(String input) {
		// StringBuilder sb = new StringBuilder("stack.");
		// sb.append(input);
		// if (!input.contains("(")) {
		// sb.append("()");
		// }
		// return sb.toString();
		// }
		// })));
	}
}
