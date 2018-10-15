package edu.xmu.test.javase.javapuzzlers.sub;

public class API {
	public static interface SampleInterface {
	};

	static class KeyIterator implements SampleInterface {
		@Override
		public int hashCode() {
			return 12345;
		}
	}

	public static SampleInterface member = new KeyIterator();
}
