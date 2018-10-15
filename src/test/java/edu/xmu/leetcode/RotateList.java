package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for: {@link <a href="https://leetcode.com/problems/rotate-list/">Rotate List</a>} <br>
 */
public class RotateList {
	static class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
			next = null;
		}

		@Override
		public String toString() {
			return "ListNode [val=" + val + "]";
		}

	}

	public ListNode rotateRight(ListNode head, int k) {
		if (k == 0 || null == head) {
			return head;
		}
		int size = 1;
		ListNode cursor = head;
		while (cursor.next != null) {
			cursor = cursor.next;
			size++;
		}
		cursor.next = head;
		cursor = head.next;
		ListNode tail = head;
		for (int i = 1; i < (size - k % size); i++) {
			cursor = cursor.next;
			tail = tail.next;
		}
		tail.next = null;
		return cursor;
	}

	@Test
	public void test() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		System.out.println(rotateRight(head, 6));

		head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		System.out.println(rotateRight(head, 2));

		head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = new ListNode(5);
		System.out.println(rotateRight(head, 3));
	}
}
