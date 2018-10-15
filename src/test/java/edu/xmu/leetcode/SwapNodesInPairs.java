package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/submissions/detail/34771151/">Swap Nodes in Pairs</a>}
 */
public class SwapNodesInPairs {
	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}

		@Override
		public String toString() {
			return "ListNode [val=" + val + ", next=" + next + "]";
		}

	}

	public ListNode swapPairs(ListNode head) {
		ListNode cursor1 = head;
		if (null == cursor1) {
			return null;
		}
		ListNode cursor2 = head.next;
		if (cursor2 == null) {
			return cursor1;
		}
		ListNode cursor3 = head.next.next;

		cursor2.next = cursor1;
		cursor1.next = swapPairs(cursor3);
		return cursor2;
	}

	@Test
	public void test() {
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
		node.next.next.next = new ListNode(4);
		System.out.println(swapPairs(node));

		node = new ListNode(1);
		System.out.println(swapPairs(node));
	}
}
