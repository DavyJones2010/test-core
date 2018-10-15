package edu.xmu.test.javase.java8;

import static org.junit.Assert.assertEquals;

import java.util.function.Supplier;

import org.junit.Test;

public class InterfaceTest {
	@Test
	public void defaultMethodTest() {
		Defaultable impl = new DefaultableImpl();
		assertEquals("echo", impl.echo());
		assertEquals("davy", impl.echo("davy"));
	}

	@Test
	public void staticMethodTest() {
		Defaultable impl = DefaultableFactory.create(DefaultableImpl::new);
		assertEquals("echo", impl.echo());
		assertEquals("davy", impl.echo("davy"));

		impl = DefaultableFactory.create(() -> new DefaultableImpl());
		assertEquals("echo", impl.echo());
		assertEquals("davy", impl.echo("davy"));

		impl = DefaultableFactory.create(() -> {
			return new DefaultableImpl();
		});
		assertEquals("echo", impl.echo());
		assertEquals("davy", impl.echo("davy"));
	}

	public static interface DefaultableFactory {
		static Defaultable create(Supplier<Defaultable> supplier) {
			return supplier.get();
		}
	}

	public static interface Defaultable {
		String echo(String val);

		default String echo() {
			return "echo";
		}
	}

	public static class DefaultableImpl implements Defaultable {

		public DefaultableImpl() {
			System.out.println("DefaultableImpl() invoked");
		}

		@Override
		public String echo(String val) {
			return val;
		}
	}

}
