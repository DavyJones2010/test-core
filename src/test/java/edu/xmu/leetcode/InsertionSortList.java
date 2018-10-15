package edu.xmu.leetcode;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/insertion-sort-list/">Insertion Sort List</a>}
 */
public class InsertionSortList {
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

	public ListNode insertionSortList(ListNode head) {
		// TODO: Solution needed
		return head;
	}

	@Test
	public void test() {
		ListNode node = new ListNode(1);
		node.next = new ListNode(2);
		node.next.next = new ListNode(3);
		node.next.next.next = new ListNode(4);
		System.out.println(insertionSortList(node));
	}
}
