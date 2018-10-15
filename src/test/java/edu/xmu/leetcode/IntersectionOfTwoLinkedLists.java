package edu.xmu.leetcode;

import org.junit.Test;

/**
 * Solution for
 * {@link <a href="https://leetcode.com/problems/intersection-of-two-linked-lists/">Intersection of Two Linked Lists</a>}
 */
public class IntersectionOfTwoLinkedLists {
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

	public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
		ListNode currA = headA;
		ListNode currB = headB;
		if (currA == null || currB == null) {
			return null;
		}
		while (currA.next != null && currB.next != null) {
			currA = currA.next;
			currB = currB.next;
		}
		if (currA.next == null && currB.next == null) {
			if (currA == currB) {
				currA = headA;
				currB = headB;
				while (currA != currB) {
					currA = currA.next;
					currB = currB.next;
				}
				return currA;
			} else {
				return null;
			}
		}
		int count = 0;
		if (currA.next == null) {
			while (currB.next != null) {
				currB = currB.next;
				count++;
			}
			if (currB == currA) {
				currB = headB;
				currA = headA;
				while (count != 0) {
					currB = currB.next;
					count--;
				}
				while (currA != currB) {
					currA = currA.next;
					currB = currB.next;
				}
				return currA;
			} else {
				return null;
			}
		} else if (currB.next == null) {
			while (currA.next != null) {
				currA = currA.next;
				count++;
			}
			if (currB == currA) {
				currB = headB;
				currA = headA;
				while (count != 0) {
					currA = currA.next;
					count--;
				}
				while (currA != currB) {
					currA = currA.next;
					currB = currB.next;
				}
				return currA;
			} else {
				return null;
			}
		} else {
			throw new RuntimeException("You will never reach here");
		}
	}

	@Test
	public void test() {
		ListNode head = new ListNode(1);
		head.next = new ListNode(2);
		head.next.next = new ListNode(3);
		head.next.next.next = new ListNode(4);

		System.out.println(getIntersectionNode(head, head.next));
	}
}
