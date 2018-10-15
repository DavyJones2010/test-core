package edu.xmu.test.javax.rfc6749.oauth2;

public class Oauth2Resource {
	private String id;
	private String picture;

	public Oauth2Resource(String id, String picture) {
		this.id = id;
		this.picture = picture;
	}

	public String getId() {
		return id;
	}

	public String getPicture() {
		return picture;
	}

	@Override
	public String toString() {
		return "Oauth2Resource [id=" + id + ", picture=" + picture + "]";
	}

}
