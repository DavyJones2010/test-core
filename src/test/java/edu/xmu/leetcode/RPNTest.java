package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.junit.Test;

public class RPNTest {
	public static enum Operator {
		PLUS("+"), MINUS("-"), MULTIPLY("*"), DIVIDE("/");
		private final String operatorStr;
		private final static Map<String, Operator> operatorMap = new HashMap<String, Operator>();
		static {
			for (Operator operator : Operator.values()) {
				operatorMap.put(operator.operatorStr, operator);
			}
		}

		Operator(String operator) {
			this.operatorStr = operator;
		}

		public static Operator getInstance(String operator) {
			return operatorMap.get(operator);
		}

		public int calc(int a, int b) {
			switch (this) {
			case PLUS:
				return a + b;
			case MINUS:
				return a - b;
			case MULTIPLY:
				return a * b;
			case DIVIDE:
				return a / b;
			default:
				throw new RuntimeException("Illegal operator");
			}
		}
	}

	public int evalRPN(String[] tokens) {
		Stack<Integer> stack = new Stack<Integer>();
		for (String str : tokens) {
			if ("+".equals(str) || "-".equals(str) || "*".equals(str)
					|| "/".equals(str)) {
				int b = stack.pop();
				int a = stack.pop();
				int res = Operator.getInstance(str).calc(a, b);
				stack.push(res);
			} else {
				stack.push(Integer.valueOf(str));
			}
		}
		return stack.pop();
	}

	@Test
	public void evalRPNTest() {
		assertEquals(9, evalRPN(new String[] { "2", "1", "+", "3", "*" }));
		assertEquals(6, evalRPN(new String[] { "4", "13", "5", "/", "+" }));
	}
}
