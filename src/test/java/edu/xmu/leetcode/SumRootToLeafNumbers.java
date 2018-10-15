package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/path-sum/">Path Sum</a>}
 */
public class SumRootToLeafNumbers {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}
	}

	public int sumNumbersTailRecursion(TreeNode root) {
		int sum = 0;
		if (null == root) {
			return sum;
		}
		return sumNumbersTailRecursion(root, 0);
	}

	int sumNumbersTailRecursion(TreeNode root, int val) {
		int sum = 0;
		if (root.left == null && root.right == null) {
			return root.val + val * 10;
		}
		if (root.left != null) {
			sum += sumNumbersTailRecursion(root.left, val * 10 + root.val);
		}
		if (root.right != null) {
			sum += sumNumbersTailRecursion(root.right, val * 10 + root.val);
		}
		return sum;
	}

	public int sumNumbers(TreeNode root) {
		List<String> ints = getNumber(root);
		return ints.stream().mapToInt(e -> Integer.valueOf(e)).sum();
	}

	List<String> getNumber(TreeNode node) {
		List<String> numbers = new ArrayList<String>();
		if (null == node) {
			return numbers;
		}
		String val = String.valueOf(node.val);
		if (null == node.left && null == node.right) {
			numbers.add(val);
		} else {
			if (null != node.left) {
				List<String> lefts = getNumber(node.left);
				for (String left : lefts) {
					numbers.add(val.concat(left));
				}
			}
			if (null != node.right) {
				List<String> rights = getNumber(node.right);
				for (String right : rights) {
					numbers.add(val.concat(right));
				}
			}
		}
		return numbers;
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(1);
		System.out.println(sumNumbersTailRecursion(root));
		root.left = new TreeNode(2);
		root.right = new TreeNode(2);
		System.out.println(sumNumbersTailRecursion(root));
		System.out.println(sumNumbersTailRecursion(null));
		root.right.left = new TreeNode(2);
		root.right.right = new TreeNode(2);
		System.out.println(sumNumbersTailRecursion(root));

		root = new TreeNode(5);
		root.left = new TreeNode(3);
		root.left.left = new TreeNode(7);
		root.left.right = new TreeNode(0);
		root.left.right.left = new TreeNode(0);
		root.right = new TreeNode(2);
		root.right.left = new TreeNode(6);
		System.out.println(sumNumbersTailRecursion(root));
	}
}
