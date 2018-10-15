package edu.xmu.test.designpattern.iterator;

public class LinkedList implements Collection
{
	private Node header = null;
	private int size = 0;

	public void add(Object obj)
	{
		if (null == header)
		{
			header = new Node(obj, null);
		} else
		{
			header.add(new Node(obj, null));
		}
		size++;
	}

	public int size()
	{
		return size;
	}

	public Iterator iterator()
	{
		return new LinkedListIterator();
	}

	private class LinkedListIterator implements Iterator
	{
		private Node currentNode = header;

		public Object next()
		{
			if (currentNode == header)
			{
				Node temp = header;
				currentNode = header.getNext();
				return temp.getData();
			} else
			{
				Node temp = currentNode.getNext();
				currentNode = temp;
				return temp.getData();
			}
		}

		public boolean hasNext()
		{
			if (null != currentNode.getNext())
			{
				return true;
			} else
			{
				return false;
			}
		}
	}
}
