package edu.xmu.test.designpattern.iterator;

public class Node
{
	private Object data;
	private Node next;

	public Node()
	{
		super();
	}

	public Node(Object currentObject, Node next)
	{
		super();
		this.data = currentObject;
		this.next = next;
	}

	public void add(Node node)
	{
		if (null == next)
		{
			next = node;
		} else
		{
			next.add(node);
		}
	}

	public Object getData()
	{
		return data;
	}

	public void setData(Object data)
	{
		this.data = data;
	}

	public Node getNext()
	{
		return next;
	}

	public void setNext(Node next)
	{
		this.next = next;
	}

}
