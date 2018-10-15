package edu.xmu.test.designpattern.iterator;

import org.junit.Test;

public class ArrayListTest
{
	@Test
	public void test()
	{
		Collection list = new ArrayList();

		for (int i = 0; i < 15; i++)
		{
			list.add(new Cat(i, "Cat" + i));
		}
		for (Iterator iter = list.iterator(); iter.hasNext();)
		{
			Cat cat = (Cat) iter.next();
			System.out.println(cat);
		}
	}
}
