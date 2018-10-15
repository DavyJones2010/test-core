package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/">Populating Next Right Pointers in Each Node II</a>}
 */
public class PopulateNextRightPointersInEachNodeII {
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
		List<List<TreeLinkNode>> nodes = widthTraverse(root);
		link(nodes);
	}

	private void link(List<List<TreeLinkNode>> nodes) {
		for (List<TreeLinkNode> node : nodes) {
			for (int i = 0; i < node.size() - 1; i++) {
				node.get(i).next = node.get(i + 1);
			}
		}
	}

	private List<List<TreeLinkNode>> widthTraverse(TreeLinkNode root) {
		List<List<TreeLinkNode>> nodes = new ArrayList<List<TreeLinkNode>>();
		if (root.left == null && root.right == null) {
			return nodes;
		}
		List<List<TreeLinkNode>> leftSub = new ArrayList<List<TreeLinkNode>>();
		List<List<TreeLinkNode>> rightSub = new ArrayList<List<TreeLinkNode>>();
		List<TreeLinkNode> curr = new ArrayList<TreeLinkNode>();
		if (root.left != null) {
			leftSub = widthTraverse(root.left);
			curr.add(root.left);
		}
		if (root.right != null) {
			rightSub = widthTraverse(root.right);
			curr.add(root.right);
		}
		nodes.add(curr);

		int height = Math.max(leftSub.size(), rightSub.size());
		for (int i = 0; i < height; i++) {
			List<TreeLinkNode> tmp = new ArrayList<TreeLinkNode>();
			if (i < leftSub.size()) {
				tmp.addAll(leftSub.get(i));
			}
			if (i < rightSub.size()) {
				tmp.addAll(rightSub.get(i));
			}
			nodes.add(tmp);
		}
		return nodes;
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
