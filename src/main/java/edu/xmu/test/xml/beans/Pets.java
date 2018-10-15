package edu.xmu.test.xml.beans;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "pets")
@XmlAccessorType(XmlAccessType.FIELD)
public class Pets implements Serializable {
	@XmlElement(name = "pet")
	List<Pet> pets;

	public Pets() {
		super();
	}

	public Pets(List<Pet> pets) {
		super();
		this.pets = pets;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

}
