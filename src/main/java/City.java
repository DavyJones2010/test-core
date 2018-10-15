public class City {
	String name;

	public City() {
		System.out.println("City is loaded by: " + this.getClass().getClassLoader());
	}

	public City(String name) {
		super();
		this.name = name;
	}
}
