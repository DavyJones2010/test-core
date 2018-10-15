package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/submissions/detail/36174295/">Convert Sorted List to Binary Search Tree</a>}
 */
public class ConvertSortedListToBinarySearchTree {
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

	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}
	}

	public TreeNode sortedListToBST(ListNode head) {
		return null == head ? null : sortedListToBST(head, null);
	}

	TreeNode sortedListToBST(ListNode start, ListNode end) {
		ListNode slowCursor = start;
		ListNode fastCursor = start.next;
		while (fastCursor != end) {
			fastCursor = fastCursor.next;
			if (fastCursor != end) {
				fastCursor = fastCursor.next;
			} else {
				break;
			}
			slowCursor = slowCursor.next;
		}

		TreeNode node = new TreeNode(slowCursor.val);
		if (start == slowCursor) {
			node.left = null;
		} else {
			node.left = sortedListToBST(start, slowCursor);
		}
		if (slowCursor.next == end) {
			node.right = null;
		} else {
			node.right = sortedListToBST(slowCursor.next, end);
		}
		return node;
	}

	@Test
	public void test() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		TreeNode bst = sortedListToBST(head);
		System.out.println(bst);

	}
}
