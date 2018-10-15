package edu.xmu.test.jmh;

import java.io.IOException;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class HelloWorld {
	public static void main(String[] args) throws RunnerException, IOException {
		Options opt = new OptionsBuilder()
				.include(".*" + HelloWorld.class.getSimpleName() + ".*")
				.forks(1).build();
		new Runner(opt).run();
	}

	@Benchmark
	public void hello() {
	}
}
