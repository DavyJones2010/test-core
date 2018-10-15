package edu.xmu.test.designpattern.iterator;

import org.junit.Test;

public class LinkedListTest
{
	@Test
	public void test()
	{
		LinkedList list = new LinkedList();
		for (int i = 0; i < 15; i++)
		{
			list.add(new Cat(i, "Cat" + i));
		}

		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			System.out.println((Cat) iter.next());
		}
	}
}
