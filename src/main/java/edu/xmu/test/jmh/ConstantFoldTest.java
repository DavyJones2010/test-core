package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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
public class ConstantFoldTest {
	private final double wrongX = Math.PI;
	private double x = Math.PI;

	@Benchmark
	public double baseline() {
		return Math.PI;
	}

	@Benchmark
	public double measureWrong_1() {
		return Math.log(Math.PI);
	}

	@Benchmark
	public double measureWrong_2() {
		// wrongX will be calculated and replaced with Math.PI because it is predictable
		return Math.log(wrongX);
	}

	@Benchmark
	public double measureRight() {
		// x will not be replaced with Math.PI because it is not predictable
		return Math.log(x);
	}

	/**
	 * Sample result as below:
	 * 
	 * <pre>
	 * Benchmark                        Mode  Cnt   Score    Error  Units
	 * ConstantFoldTest.baseline        avgt    5   6.655 ±  2.248  ns/op
	 * ConstantFoldTest.measureRight    avgt    5  57.776 ± 31.492  ns/op
	 * ConstantFoldTest.measureWrong_1  avgt    5   7.205 ±  4.382  ns/op
	 * ConstantFoldTest.measureWrong_2  avgt    5   6.765 ±  2.952  ns/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ConstantFoldTest.class.getSimpleName()).warmupIterations(5).measurementIterations(5).threads(4).forks(1).build();
		new Runner(opt).run();
	}
}
