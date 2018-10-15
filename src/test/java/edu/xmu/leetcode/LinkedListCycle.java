package edu.xmu.leetcode;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class LinkedListCycle {
	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}
	}

	public boolean hasCycle(ListNode head) {
		while (head != null && null != head.next) {
			if (head.next.val == Integer.MAX_VALUE) {
				return true;
			} else {
				head.next.val = Integer.MAX_VALUE;
				head.next = head.next.next;
			}
		}
		return false;
	}

	@Test
	public void test() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		assertFalse(hasCycle(head));

		head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = head.next;
		assertTrue(hasCycle(head));

		head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = head.next;
		assertTrue(hasCycle(head));
	}
}
