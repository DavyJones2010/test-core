package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;
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
public class DeadCodeTest {
	private double x = Math.PI;

	@Benchmark
	public void baseline() {
		// do nothing, this is a baseline
	}

	@Benchmark
	public void measureWrong() {
		// this line will be skipped because the result is never used which makes it a DEADCODE
		Math.log(x);
	}

	@Benchmark
	public double measureRight() {
		// this line will not be skipped because the result is used
		return Math.log(x);
	}

	@Benchmark
	public void measureRight2(Blackhole bh) {
		// this line will not be skipped because the result is consumed by blackhole
		bh.consume(Math.log(x));
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(DeadCodeTest.class.getSimpleName()).warmupIterations(5).measurementIterations(5).threads(3).forks(1).build();
		new Runner(opt).run();
	}
}
