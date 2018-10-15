package edu.xmu.test.hibernate.model;

import java.util.Date;

public class UserWithNativeID {
	private int id;
	private String name;
	private String password;

	private Date dateCreated;
	private Date dateExpired;

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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateExpired() {
		return dateExpired;
	}

	public void setDateExpired(Date dateExpired) {
		this.dateExpired = dateExpired;
	}

	@Override
	public String toString() {
		return "UserWithNativeID [id=" + id + ", name=" + name + ", password=" + password + ", dateCreated=" + dateCreated + ", dateExpired=" + dateExpired + "]";
	}

}
