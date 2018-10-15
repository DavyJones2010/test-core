package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>} <br/>
 * Pay attention to the difference between "Scope" and "Level", and how multiple threads work to run benchmark method.<br/>
 * Time spent in fixture methods does not count into the performance metrics, so you can use this to do some heavy-lifting.
 */
@State(Scope.Thread)
public class StatesFixtureTest {

	volatile double x;

	@Setup(Level.Iteration)
	public void prepare() {
		x = 0;
		System.out.println(Thread.currentThread() + " Prepare");
	}

	@TearDown(Level.Iteration)
	public void check() {
		System.out.println(Thread.currentThread() + " Check");
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void measure() throws InterruptedException {
		System.out.println(Thread.currentThread() + ". x=" + (x++));
		TimeUnit.MILLISECONDS.sleep(10L);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(StatesFixtureTest.class.getSimpleName()).warmupIterations(0).warmupTime(TimeValue.milliseconds(100L)).measurementIterations(5)
				.measurementTime(TimeValue.milliseconds(100L)).threads(3).forks(1).build();
		new Runner(opt).run();
	}
}
