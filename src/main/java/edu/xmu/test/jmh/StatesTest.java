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
public class StatesTest {

	/**
	 * Instance of this class will be instantiated on demand and reused during the entire benchmark trial. <br/>
	 * Scope.Benchmark: all instances of the same type will be shared across all worker threads.
	 */
	@State(Scope.Benchmark)
	public static class BenchmarkState {
		volatile double x = Math.PI;
	}

	/**
	 * Scope.Benchmark: With thread scope, all instances of the same type are distinct, even if multiple state objects are injected in the same benchmark
	 */
	@State(Scope.Thread)
	public static class ThreadState {
		volatile double x = Math.PI;
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	// Mode.Throughput is default
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	// TimeUnit.SECONDS is default
	public void measureUnshared(ThreadState state) {
		// All benchmark threads will call in this method.
		//
		// However, since ThreadState is the Scope.Thread, each thread
		// will have it's own copy of the state, and this benchmark
		// will measure unshared case.
		state.x++;
		System.out.println("measureUnshared: " + state.x);
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	// Mode.Throughput is default
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	// TimeUnit.SECONDS is default
	public void measureShared(BenchmarkState state) {
		// All benchmark threads will call in this method.
		//
		// Since BenchmarkState is the Scope.Benchmark, all threads
		// will share the state instance, and we will end up measuring
		// shared case.
		state.x++;
		System.out.println("measureShared: " + state.x);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(StatesTest.class.getSimpleName()).warmupIterations(5).measurementIterations(5).threads(3).forks(1).build();
		new Runner(opt).run();
	}
}
