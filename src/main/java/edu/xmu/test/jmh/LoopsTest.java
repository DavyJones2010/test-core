package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
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
public class LoopsTest {
	int x = 1;
	int y = 2;

	private int reps(int reps) {
		int s = 0;
		for (int i = 0; i < reps; i++) {
			s += (x + y);
		}
		return s;
	}

	@Benchmark
	@OperationsPerInvocation(1)
	public void measureWrong_1(Blackhole bh) {
		bh.consume(reps(1));
	}

	@Benchmark
	@OperationsPerInvocation(10)
	public void measureWrong_10(Blackhole bh) {
		bh.consume(reps(10));
	}

	@Benchmark
	@OperationsPerInvocation(100)
	public void measureWrong_100(Blackhole bh) {
		bh.consume(reps(100));
	}

	@Benchmark
	@OperationsPerInvocation(1000)
	public void measureWrong_1000(Blackhole bh) {
		bh.consume(reps(1000));
	}

	@Benchmark
	@OperationsPerInvocation(10000)
	public void measureWrong_10000(Blackhole bh) {
		bh.consume(reps(10000));
	}

	@Benchmark
	public double measureRight() {
		return x + y;
	}

	/**
	 * Sample result as below:
	 * 
	 * <pre>
	 * Benchmark                     Mode  Cnt   Score   Error  Units
	 * LoopsTest.measureRight        avgt    5  10.103 ± 8.751  ns/op
	 * LoopsTest.measureWrong_1      avgt    5   7.753 ± 3.884  ns/op
	 * LoopsTest.measureWrong_10     avgt    5   0.998 ± 0.659  ns/op
	 * LoopsTest.measureWrong_100    avgt    5   0.130 ± 0.077  ns/op
	 * LoopsTest.measureWrong_1000   avgt    5   0.130 ± 0.138  ns/op
	 * LoopsTest.measureWrong_10000  avgt    5   0.067 ± 0.021  ns/op
	 * </pre>
	 * 
	 * We can find out that the more loops the less time is takes. It is because optimizer will hoist operation from the loop. <br/>
	 * <b>Don't overuse loops, rely on JMH to get the measurement right</b>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(LoopsTest.class.getSimpleName()).warmupIterations(5).measurementIterations(5).threads(4).forks(1).build();
		new Runner(opt).run();
	}
}
