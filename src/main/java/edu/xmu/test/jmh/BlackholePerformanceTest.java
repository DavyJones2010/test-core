package edu.xmu.test.jmh;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class BlackholePerformanceTest {

	@Benchmark
	public void loopWithoutBlackhole() throws IOException {
		// do nothing
	}

	@Benchmark
	public void loopWithBlackhole(Blackhole bh) throws IOException {
		bh.consume(100);
	}

	/**
	 * It proved that blackhole rarely affects the benchmark criteria. <br/>
	 * 
	 * <pre>
	 * Benchmark                                      Mode  Cnt  Score   Error  Units
	 * BlackholePerformanceTest.loopWithBlackhole     avgt    2  2.927          ns/op
	 * BlackholePerformanceTest.loopWithoutBlackhole  avgt    2  0.370          ns/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BlackholePerformanceTest.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
