package edu.xmu.test.effectivejava.sample.object;

import org.junit.Test;

public class Chapter2ConstructorEscape1Test {

	private static class ImmutableObject {
		private int age;
		private String name;

		public ImmutableObject(Handler handler, int age, String name)
				throws InterruptedException {
			Thread.sleep((long) (1000 * Math.random()));
			this.age = age;
			this.name = name;
		}

		public void setAge(int age) {
			this.age = age;
		}

		public void setName(String name) {
			this.name = name;
		}

		@Override
		public String toString() {
			return "ImmutableObject [age=" + age + ", name=" + name + "]";
		}

	}

	private static interface Handler {
		public void handle(ImmutableObject object);
	}

	@Test
	public void test() throws InterruptedException {
		ImmutableObject obj = new ImmutableObject(new Handler() {
			@Override
			public void handle(final ImmutableObject object) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						try {
							Thread.sleep(1400L);
							object.setAge(25);
							object.setName("Li");
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}).start();
				;
			}
		}, 24, "Yang");
		Thread.sleep(2000L);
		// Thread.sleep(1000L);
		System.out.println(obj);
	}
}
