package edu.xmu.test.jmh;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 2, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class ReflectionTest {

	Person person;

	@Setup(Level.Trial)
	public void setUp() {
		person = new Person();
		person.setName("Davy");
	}

	static class Person {
		String name;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

	@Benchmark
	public void reflectionTest(Blackhole bh) throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		Person p = (Person) Class.forName("edu.xmu.test.jmh.ReflectionTest$Person").newInstance();
		bh.consume(p);
	}

	@Benchmark
	public void newTest(Blackhole bh) {
		Person p = new Person();
		bh.consume(p);
	}

	@Benchmark
	public void methodInvokeTest(Blackhole bh) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = person.getClass().getMethod("setName", new Class[] { String.class });
		m.invoke(person, new Object[] { "Yang" });
		bh.consume(m);
	}

	@Benchmark
	public void methodCallTest(Blackhole bh) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		person.setName("Yang");
		bh.consume(person);
	}

	@Benchmark
	public void methodInvokeTest2(Blackhole bh) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Method m = person.getClass().getMethod("getName", new Class[] {});
		String name = (String) m.invoke(person, new Object[] {});
		bh.consume(name);
	}

	@Benchmark
	public void methodCallTest2(Blackhole bh) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String name = person.getName();
		bh.consume(name);
	}

	/**
	 * <pre>
	 * Benchmark                         Mode  Cnt     Score     Error  Units
	 * ReflectionTest.methodCallTest     avgt    5     8.435 ±   0.821  ns/op
	 * ReflectionTest.methodCallTest2    avgt    5     7.417 ±   2.546  ns/op
	 * ReflectionTest.methodInvokeTest   avgt    5   820.724 ± 158.951  ns/op
	 * ReflectionTest.methodInvokeTest2  avgt    5   880.591 ± 189.080  ns/op
	 * ReflectionTest.newTest            avgt    5     6.616 ±   0.557  ns/op
	 * ReflectionTest.reflectionTest     avgt    5  2594.893 ± 575.389  ns/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ReflectionTest.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
