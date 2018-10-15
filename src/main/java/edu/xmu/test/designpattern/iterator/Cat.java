package edu.xmu.test.designpattern.iterator;

public class Cat
{
	private int id;
	private String name;

	public Cat(int id, String name)
	{
		super();
		this.id = id;
		this.name = name;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public String toString()
	{
		return "Cat [id=" + id + ", name=" + name + "]";
	}

}
