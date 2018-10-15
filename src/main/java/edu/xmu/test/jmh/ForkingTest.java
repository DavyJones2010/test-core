package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>}
 *
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class ForkingTest {
	public interface Counter {
		int inc();
	}

	// c1 and c2 are Scope.Thread shared
	Counter c1 = new Counter1();
	Counter c2 = new Counter2();

	public class Counter1 implements Counter {
		private int x;

		@Override
		public int inc() {
			return x++;
		}
	}

	public class Counter2 implements Counter {
		private int x;

		@Override
		public int inc() {
			return x++;
		}
	}

	public int measure(Counter c) {
		int s = 0;
		for (int i = 0; i < 10; i++) {
			s += c.inc();
		}
		return s;
	}

	@Benchmark
	@Fork(1)
	public int measure_1_c1() {
		return measure(c1);
	}

	@Benchmark
	@Fork(1)
	public int measure_2_c2() {
		return measure(c2);
	}

	@Benchmark
	@Fork(1)
	public int measure_3_c1_again() {
		return measure(c1);
	}

	@Benchmark
	@Fork(1)
	public int measure_4_forked_c1() {
		return measure(c1);
	}

	@Benchmark
	@Fork(1)
	public int measure_5_forked_c2() {
		return measure(c2);
	}

	/**
	 * Sample result as below:
	 * 
	 * <pre>
	 * Benchmark                        Mode  Cnt   Score   Error  Units
	 * ForkingTest.measure_1_c1         avgt    5   8.918 ± 1.014  ns/op
	 * ForkingTest.measure_2_c2         avgt    5  64.559 ± 3.075  ns/op
	 * ForkingTest.measure_3_c1_again   avgt    5  63.440 ± 3.302  ns/op
	 * ForkingTest.measure_4_forked_c1  avgt    5  15.343 ± 3.563  ns/op
	 * ForkingTest.measure_5_forked_c2  avgt    5  15.184 ± 5.854  ns/op
	 * </pre>
	 * 
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ForkingTest.class.getSimpleName()).warmupIterations(5).measurementIterations(5).threads(5).build();
		new Runner(opt).run();
	}
}
