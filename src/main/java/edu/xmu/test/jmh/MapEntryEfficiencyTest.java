package edu.xmu.test.jmh;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@Warmup(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 5, timeUnit = TimeUnit.SECONDS)
@Threads(1)
@State(Scope.Thread)
public class MapEntryEfficiencyTest {
	@Param({ "1000", "100000", "1000000" })
	private int mapSize;

	private Map<String, String> map;

	@Setup(Level.Trial)
	public void setup() {
		map = generateMap();
	}

	Map<String, String> generateMap() {
		Map<String, String> generatedMap = Maps.newHashMap();
		for (int i = 0; i < mapSize; i++) {
			generatedMap.put("key_" + i, "value_" + i);
		}
		return generatedMap;
	}

	@Benchmark
	public void keyIterTest(Blackhole bh) {
		List<String> value = Lists.newArrayList();
		for (String key : map.keySet()) {
			value.add(map.get(key));
		}
		bh.consume(value);
	}

	@Benchmark
	public void entryIterTest(Blackhole bh) {
		List<String> value = Lists.newArrayList();
		for (Entry<String, String> entry : map.entrySet()) {
			value.add(entry.getValue());
		}
		bh.consume(value);
	}

	/**
	 * The result shows entryIter is more efficient.
	 * 
	 * <pre>
	 * Benchmark                             (mapSize)  Mode  Cnt      Score      Error  Units
	 * MapEntryEfficiencyTest.entryIterTest       1000  avgt   10     17.521 ±    0.469  us/op
	 * MapEntryEfficiencyTest.entryIterTest     100000  avgt   10   2960.522 ±  245.226  us/op
	 * MapEntryEfficiencyTest.entryIterTest    1000000  avgt   10  47828.892 ± 1258.763  us/op
	 * MapEntryEfficiencyTest.keyIterTest         1000  avgt   10     22.554 ±    1.862  us/op
	 * MapEntryEfficiencyTest.keyIterTest       100000  avgt   10   3625.754 ±  166.063  us/op
	 * MapEntryEfficiencyTest.keyIterTest      1000000  avgt   10  59477.037 ± 1799.775  us/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(MapEntryEfficiencyTest.class.getSimpleName()).forks(1).build();
		new Runner(opt).run();
	}
}
