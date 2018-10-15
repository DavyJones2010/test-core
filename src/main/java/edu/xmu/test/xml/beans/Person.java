package edu.xmu.test.xml.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Person implements Serializable {
	int id;
	String name;
	int age;
	Address address;
	Pets pets;

	public Person() {
		super();
	}

	public Person(int id, String name, int age, Address address) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
	}

	@XmlAttribute
	public void setId(int id) {
		this.id = id;
	}

	@XmlElement
	public void setName(String name) {
		this.name = name;
	}

	@XmlElement
	public void setAge(int age) {
		this.age = age;
	}

	@XmlElement
	public void setAddress(Address address) {
		this.address = address;
	}

	@XmlElement
	public void setPets(Pets pets) {
		this.pets = pets;
	}

	public Pets getPets() {
		return pets;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public Address getAddress() {
		return address;
	}

	public int getId() {
		return id;
	}

}
