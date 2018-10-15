package edu.xmu.test.designpattern.iterator;

public class ArrayList implements Collection
{
	private Object[] objects = new Object[10];
	private int index = 0;

	public void add(Object obj)
	{
		if (index == objects.length)
		{
			Object[] newObjects = new Object[objects.length * 2];
			System.arraycopy(objects, 0, newObjects, 0, objects.length);
			objects = newObjects;
		}
		objects[index] = obj;
		index++;
	}

	public int size()
	{
		return index;
	}

	public Iterator iterator()
	{
		return new ArrayListIterator();
	}

	private class ArrayListIterator implements Iterator
	{
		private int currentIndex = 0;

		public Object next()
		{
			Object currentObject = objects[currentIndex];
			currentIndex++;
			return currentObject;
		}

		public boolean hasNext()
		{
			if (currentIndex > index || null == objects[currentIndex])
			{
				System.out.println("no next");
				return false;
			} else
			{
				System.out.println("has next");
				return true;
			}
		}
	}
}
