package edu.xmu.test.jmh;

import java.util.LinkedList;
import java.util.List;
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
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>}
 *
 */
@State(Scope.Thread)
@Fork(1)
public class BatchSizeTest {

	List<String> list = new LinkedList<String>();

	@Benchmark
	@Warmup(iterations = 5, time = 1)
	@Measurement(iterations = 5, time = 1)
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public List<String> measureWrong_1() {
		list.add(list.size() / 2, "something");
		return list;
	}

	@Benchmark
	@Warmup(iterations = 5, time = 5)
	@Measurement(iterations = 5, time = 5)
	@BenchmarkMode(Mode.AverageTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public List<String> measureWrong_5() {
		list.add(list.size() / 2, "something");
		return list;
	}

	@Benchmark
	@Warmup(iterations = 5, batchSize = 5000)
	@Measurement(iterations = 5, batchSize = 5000)
	@BenchmarkMode(Mode.SingleShotTime)
	@OutputTimeUnit(TimeUnit.MICROSECONDS)
	public List<String> measureRight() {
		list.add(list.size() / 2, "something");
		return list;
	}

	@Setup(Level.Iteration)
	public void setup() {
		list.clear();
	}

	/**
	 * For each iteration, 5000 invocations will be performed. And the time cost would recorded as as the total time of 5000 invocations <br/>
	 * The setup level is Level.Iteration, therefore 5000 invokes would share the same list <br/>
	 * measureWrong_1 and measureWrong_5 is measuring single invokes, and the results varies greatly depending on the iterations time.
	 * 
	 * <pre>
	 * Benchmark                     Mode  Cnt      Score      Error  Units
	 * BatchSizeTest.measureWrong_1  avgt    5     25.493 ±    1.394  us/op
	 * BatchSizeTest.measureWrong_5  avgt    5     57.035 ±    8.163  us/op
	 * BatchSizeTest.measureRight      ss    5  11202.597 ± 1420.537  us/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(BatchSizeTest.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
