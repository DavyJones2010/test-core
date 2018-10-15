package edu.xmu.test.xml.beans;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;


public class Address implements Serializable {
	String country;
	String province;
	String city;
	String streetName;
	int streetNo;

	public Address() {
		super();
	}

	public Address(String country, String province, String city,
			String streetName, int streetNo) {
		super();
		this.country = country;
		this.province = province;
		this.city = city;
		this.streetName = streetName;
		this.streetNo = streetNo;
	}

	@XmlElement
	public void setCountry(String country) {
		this.country = country;
	}

	@XmlElement
	public void setProvince(String province) {
		this.province = province;
	}

	@XmlElement
	public void setCity(String city) {
		this.city = city;
	}

	@XmlElement
	public void setStreetName(String streetName) {
		this.streetName = streetName;
	}

	@XmlElement
	public void setStreetNo(int streetNo) {
		this.streetNo = streetNo;
	}

	public String getCountry() {
		return country;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getStreetName() {
		return streetName;
	}

	public int getStreetNo() {
		return streetNo;
	}

}
