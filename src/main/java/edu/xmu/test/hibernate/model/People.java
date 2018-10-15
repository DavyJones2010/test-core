package edu.xmu.test.hibernate.model;

public class People {
	private int id;
	private String name;
	private IDCard idCard;

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

	public IDCard getIdCard() {
		return idCard;
	}

	public void setIdCard(IDCard idCard) {
		this.idCard = idCard;
	}

	@Override
	public String toString() {
		return "People [id=" + id + ", name=" + name + ", idCard=" + idCard + "]";
	}
	
}
