package edu.xmu.leetcode;

import org.junit.Test;

public class ReverseLinkedList {
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

	public ListNode reverseList(ListNode head) {
		return reverseList(head, null);
	}

	ListNode reverseList(ListNode head, ListNode next) {
		if (null == head) {
			return head;
		}
		ListNode tmp = head.next;
		if (tmp == null) {
			head.next = next;
			return head;
		}
		head.next = next;
		return reverseList(tmp, head);
	}

	@Test
	public void test() {
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
		node.next.next.next = new ListNode(4);
		System.out.println(reverseList(node));
	}
}
