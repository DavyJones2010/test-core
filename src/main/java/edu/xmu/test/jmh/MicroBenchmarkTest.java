package edu.xmu.test.jmh;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>}
 *
 */
@BenchmarkMode(Mode.Throughput)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@State(Scope.Thread)
public class MicroBenchmarkTest {
	static final int DEFAULT_WARMUP_ITERATIONS = 10;
	static final int DEFAULT_MEASURE_ITERATIONS = 10;
	static final int DEFAULT_FORKS = 1;

	private List<Integer> list;
	private Set<Integer> hashSet;
	private TreeSet<Integer> treeSet;

	@Param({ "999", "999999" })
	private int length;

	@Setup(Level.Trial)
	public void setup() {
		list = new ArrayList<>(length);
		for (int i = 0; i < length; i++) {
			list.add(i);
		}
		hashSet = new HashSet<>(list);
		treeSet = new TreeSet<>(list);
	}

	@Benchmark
	public void indexof() {
		list.indexOf(length);
	}

	@Benchmark
	public void binarySearch() {
		Collections.binarySearch(list, length);
	}

	@Benchmark
	public void foreach() {
		int size = list.size();
		for (int i = 0; i < size; i++) {
			if (list.get(i).equals(length)) {
				return;
			}
		}
	}

	@Benchmark
	public void hashSetSearch() {
		hashSet.contains(length);
	}

	@Benchmark
	public void TreeSetSearch() {
		treeSet.contains(length);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(MicroBenchmarkTest.class.getSimpleName()).forks(1).build();
		new Runner(opt).run();
	}
}
