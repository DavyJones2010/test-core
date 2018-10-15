package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;

import org.apache.poi.ss.util.CellReference;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
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

import edu.xmu.test.excel.util.CellReferenceUtil;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class CellReferenceEfficiencyTest {

	@Benchmark
	public void measureUtil(Blackhole bh) {
		bh.consume(CellReferenceUtil.getRowIndexByCoordName("C8"));
	}

	@Benchmark
	public void measureNew(Blackhole bh) {
		CellReference cr = new CellReference("C8");
		bh.consume(cr.getRow());
	}

	/**
	 * Sample result as below:
	 * 
	 * <pre>
	 * Benchmark                                Mode  Cnt    Score    Error  Units
	 * CellReferenceEfficiencyTest.measureNew   avgt    5  226.368 ± 58.100  ns/op
	 * CellReferenceEfficiencyTest.measureUtil  avgt    5  121.722 ± 16.828  ns/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(CellReferenceEfficiencyTest.class.getSimpleName()).warmupIterations(3).measurementIterations(5).threads(4).forks(1).build();
		new Runner(opt).run();
	}
}
