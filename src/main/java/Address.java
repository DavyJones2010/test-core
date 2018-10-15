public class Address {
	String name;
	City city;

	public Address() {
		System.out.println("Address is loaded by: " + this.getClass().getClassLoader());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
