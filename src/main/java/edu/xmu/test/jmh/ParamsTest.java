package edu.xmu.test.jmh;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>}
 *
 */
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@Fork(1)
public class ParamsTest {

	@Param({ "1", "31" })
	public int arg;
	@Param({ "0", "1" })
	public int certainty;

	@Benchmark
	public boolean bench() {
		return BigInteger.valueOf(arg).isProbablePrime(certainty);
	}

	/**
	 * <pre>
	 * Benchmark         (arg)  (certainty)  Mode  Cnt    Score   Error  Units
	 * ParamsTest.bench      1            0  avgt    2    7.919          ns/op
	 * ParamsTest.bench      1            1  avgt    2   12.964          ns/op
	 * ParamsTest.bench     31            0  avgt    2   20.435          ns/op
	 * ParamsTest.bench     31            1  avgt    2  948.295          ns/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ParamsTest.class.getSimpleName()).warmupIterations(1).warmupTime(TimeValue.milliseconds(200L)).measurementIterations(2)
				.measurementTime(TimeValue.milliseconds(200L)).forks(1).build();
		new Runner(opt).run();
	}
}
