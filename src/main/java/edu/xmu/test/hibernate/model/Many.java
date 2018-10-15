package edu.xmu.test.hibernate.model;

import java.util.Set;

public class Many {
	private int id;
	private String name;
	private Set<One> ones;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<One> getOnes() {
		return ones;
	}

	public void setOnes(Set<One> ones) {
		this.ones = ones;
	}
}
