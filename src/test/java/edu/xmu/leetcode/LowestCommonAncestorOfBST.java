package edu.xmu.leetcode;

import org.junit.Test;

public class LowestCommonAncestorOfBST {
	static class TreeNode {
		int val;
		TreeNode left;
		TreeNode right;

		TreeNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "TreeNode [val=" + val + "]";
		}

	}

	public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
		if (p.val == root.val) {
			return p;
		} else if (q.val == root.val) {
			return q;
		} else if ((p.val - root.val) * (q.val - root.val) < 0) {
			return root;
		} else if ((p.val - root.val) > 0 && (q.val - root.val) > 0) {
			return lowestCommonAncestor(root.right, p, q);
		} else {
			return lowestCommonAncestor(root.left, p, q);
		}
	}

	@Test
	public void test() {
		TreeNode root = new TreeNode(6);
		root.right = new TreeNode(8);
		root.right.left = new TreeNode(7);
		root.right.right = new TreeNode(9);

		root.left = new TreeNode(2);
		root.left.left = new TreeNode(-1);
		root.left.right = new TreeNode(3);

		TreeNode res = lowestCommonAncestor(root, root.left, root.right.right);
		System.out.println(res);
	}
}
