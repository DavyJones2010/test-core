package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/best-time-to-buy-and-sell-stock/">Best Time to Buy and Sell Stock</a>}
 */
public class MergeTwoSortedLists {
	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "ListNode [val=" + val + "]";
		}
	}

	public ListNode mergeTwoLists(ListNode a, ListNode b) {
		ListNode root = null;
		ListNode rootCursor = null;
		if (a == null || b == null) {
			if (a == null) {
				return b;
			}
			if (b == null) {
				return a;
			}
		}
		if (a.val < b.val) {
			root = new ListNode(a.val);
			rootCursor = root;
			a = a.next;
		} else {
			root = new ListNode(b.val);
			rootCursor = root;
			b = b.next;
		}
		while (a != null && b != null) {
			if (a.val < b.val) {
				rootCursor.next = new ListNode(a.val);
				a = a.next;
			} else {
				rootCursor.next = new ListNode(b.val);
				b = b.next;
			}
			rootCursor = rootCursor.next;
		}
		if (a == null) {
			rootCursor.next = new ListNode(b.val);
			rootCursor.next.next = b.next;
		}
		if (b == null) {
			rootCursor.next = new ListNode(a.val);
			rootCursor.next.next = a.next;
		}
		return root;
	}

	@Test
	public void test() {
		ListNode a = new ListNode(1);

		ListNode b = new ListNode(3);
		b.next = new ListNode(4);
		System.out.println(mergeTwoLists(a, b));

		System.out.println(mergeTwoLists(null, null));
		System.out.println(mergeTwoLists(null, b));
	}
}
