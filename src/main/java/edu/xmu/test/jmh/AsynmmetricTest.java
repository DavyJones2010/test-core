package edu.xmu.test.jmh;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Group;
import org.openjdk.jmh.annotations.GroupThreads;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

/**
 * {@link <a href="http://hg.openjdk.java.net/code-tools/jmh/file/3387560164f9/jmh-samples/src/main/java/org/openjdk/jmh/samples">JMH Samples</a>}
 *
 */
@State(Scope.Group)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class AsynmmetricTest {
	private AtomicInteger counter;

	@Setup
	public void up() {
		counter = new AtomicInteger();
	}

	/**
	 * 3 threads will run this methods concurrently
	 */
	@Benchmark
	@Group("g")
	@GroupThreads(2)
	public int inc() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100L);
		System.out.println(Thread.currentThread() + " inc()");
		return counter.incrementAndGet();
	}

	@Benchmark
	@Group("g")
	@GroupThreads(3)
	public int get() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100L);
		System.out.println(Thread.currentThread() + " get()");
		return counter.get();
	}

	@Benchmark
	@Group("e")
	@GroupThreads(2)
	public int inc_2() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100L);
		System.out.println(Thread.currentThread() + " inc()");
		return counter.incrementAndGet();
	}

	/**
	 * Group("g") and Group("e") would be executed sequentially.<br/>
	 * Within Group("g"), 5 threads would be started, inc() and get() would be executed simultaneously. <br/>
	 * And then within Group("e"), 2 threads would run inc_2() concurrently.<br/>
	 * 7 different threads would be created in total. <br/>
	 * 
	 * <pre>
	 * edu.xmu.test.jmh.AsynmmetricTest.e-jmh-worker-1,5,main
	 * edu.xmu.test.jmh.AsynmmetricTest.e-jmh-worker-2,5,main
	 * 
	 * edu.xmu.test.jmh.AsynmmetricTest.g-jmh-worker-1,5,main
	 * edu.xmu.test.jmh.AsynmmetricTest.g-jmh-worker-2,5,main
	 * edu.xmu.test.jmh.AsynmmetricTest.g-jmh-worker-3,5,main
	 * edu.xmu.test.jmh.AsynmmetricTest.g-jmh-worker-4,5,main
	 * edu.xmu.test.jmh.AsynmmetricTest.g-jmh-worker-5,5,main
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(AsynmmetricTest.class.getSimpleName()).warmupIterations(1).warmupTime(TimeValue.milliseconds(200L)).measurementIterations(2)
				.measurementTime(TimeValue.milliseconds(200L)).forks(1).build();
		new Runner(opt).run();
	}
}
