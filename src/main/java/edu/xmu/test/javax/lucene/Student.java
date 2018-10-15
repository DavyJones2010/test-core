package edu.xmu.test.javax.lucene;

public class Student {
	private String id;
	private String name;
	private String password;
	private String gender;
	private int score;

	public Student() {
		super();
	}

	public Student(String id, String name, String password, String gender, int score) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.gender = gender;
		this.score = score;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

}
