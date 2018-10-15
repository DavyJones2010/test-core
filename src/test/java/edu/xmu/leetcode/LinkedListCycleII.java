package edu.xmu.leetcode;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/linked-list-cycle-ii/">Linked List Cycle II</a>}
 */
public class LinkedListCycleII {
	class ListNode {
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

	public ListNode detectCycle(ListNode head) {
		Set<ListNode> visited = new HashSet<ListNode>();
		ListNode curr = head;
		while (null != curr) {
			if (visited.contains(curr)) {
				return curr;
			}
			visited.add(curr);
			curr = curr.next;
		}
		return null;
	}

	@Test
	public void test() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		System.out.println(detectCycle(head));

		head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);
		head.next.next.next.next = head.next;
		System.out.println(detectCycle(head));

		head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = head.next;
		System.out.println(detectCycle(head));
	}
}
