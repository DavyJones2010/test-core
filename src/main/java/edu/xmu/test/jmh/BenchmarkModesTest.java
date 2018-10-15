package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>}
 *
 */
public class BenchmarkModesTest {
	static final int DEFAULT_WARMUP_ITERATIONS = 10;
	static final int DEFAULT_MEASURE_ITERATIONS = 10;
	static final int DEFAULT_FORKS = 1;

	/**
	 * Throughput: measure how many operations executed in one @OutputTimeUnit
	 */
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void measureThroughput() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	/**
	 * AverageTime: measure average @OutputTimeUnit cost for executing this method with default iteration num(20)
	 */
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureAvgTime() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	/**
	 * SampleTime: measure average/sampled @OutputTimeUnit cost for executing this method
	 */
	@Benchmark
	@BenchmarkMode(Mode.SampleTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureSampleTime() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	/**
	 * SingleShotTime: measure average @OutputTimeUnit cost for executing this method with only one iteration and NO warmup
	 */
	@Benchmark
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureSingleShotTime() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	/**
	 * Apply multiple benchmark modes at once.
	 */
	@Benchmark
	@BenchmarkMode({ Mode.Throughput, Mode.SingleShotTime })
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureMultiple() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	/**
	 * Apply all benchmark modes at once.
	 */
	@Benchmark
	@BenchmarkMode(Mode.All)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public void measureAll() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BenchmarkModesTest.class.getSimpleName()).forks(1).build();
		new Runner(opt).run();
	}
}
