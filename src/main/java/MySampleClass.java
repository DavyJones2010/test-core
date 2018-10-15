public class MySampleClass {
	public void hallo() {
		System.out.println("MySampleClass is loaded by: " + this.getClass().getClassLoader());
		Address addr = new Address();
		addr.setCity(new City());
		addr.setName("Hallo");
		Entrance e = new Entrance("EE", 12, addr);
		System.out.println(e.toString());
	}

	public static void main(String[] args) {
		new MySampleClass().hallo();
	}
}
