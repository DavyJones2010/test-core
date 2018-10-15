package edu.xmu.leetcode;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/populating-next-right-pointers-in-each-node/">Populating Next Right Pointers in Each Node</a>}
 */
public class PopulateNextRightPointersInEachNode {
	static class TreeLinkNode {
		int val;
		TreeLinkNode left, right, next;

		TreeLinkNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "TreeLinkNode [val=" + val + "]";
		}

	}

	public void connect(TreeLinkNode root) {
		if (null == root) {
			return;
		}
		if (root.left != null && root.right != null) {
			TreeLinkNode currNode = root;
			while (currNode.next != null) {
				currNode.left.next = currNode.right;
				currNode.right.next = currNode.next.left;
				currNode = currNode.next;
			}
			currNode.left.next = currNode.right;
		}
		connect(root.left);
	}

	@Test
	public void test() {
		TreeLinkNode root = new TreeLinkNode(-1);

		root.left = new TreeLinkNode(0);
		root.left.left = new TreeLinkNode(2);
		root.left.left.left = new TreeLinkNode(6);
		root.left.left.right = new TreeLinkNode(7);

		root.left.right = new TreeLinkNode(3);
		root.left.right.left = new TreeLinkNode(8);
		root.left.right.right = new TreeLinkNode(9);

		root.right = new TreeLinkNode(1);
		root.right.left = new TreeLinkNode(4);
		root.right.left.left = new TreeLinkNode(10);
		root.right.left.right = new TreeLinkNode(11);
		root.right.right = new TreeLinkNode(5);
		root.right.right.left = new TreeLinkNode(12);
		root.right.right.right = new TreeLinkNode(13);

		connect(root);
		System.out.println(root);
	}
}
