package edu.xmu.test.jmh;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.StandardOpenOption;
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
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Timeout;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import edu.xmu.test.codekata.codecomp.DataGenerator;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class ReadFileTest {

	File file;

	@Benchmark
	public void bufferedReadFile(Blackhole bh) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		while (null != (line = reader.readLine())) {
			bh.consume(line);
		}
		reader.close();
	}

	@Benchmark
	@Timeout(time = 20, timeUnit = TimeUnit.SECONDS)
	public void byteReadFile(Blackhole bh) throws IOException {
		InputStream is = new FileInputStream(file);
		int b = 0;
		while (-1 != (b = is.read())) {
			bh.consume(b);
		}
		is.close();
	}

	@Benchmark
	public void byteBufferedReadFile(Blackhole bh) throws IOException {
		InputStream is = new BufferedInputStream(new FileInputStream(file));
		int b = 0;
		while (-1 != (b = is.read())) {
			bh.consume(b);
		}
		is.close();
	}

	@Benchmark
	public void nioReadFile(Blackhole bh) throws IOException {
		FileInputStream is = new FileInputStream(file);
		FileChannel fc = is.getChannel();
		ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
		fc.read(buffer);
		while (buffer.remaining() > 0) {
			bh.consume(buffer.get());
		}
		buffer.clear();
		fc.close();
		is.close();
	}

	@Benchmark
	public void nioFileChannel(Blackhole bh) throws IOException {
		FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
		fc.read(buffer);
		while (buffer.remaining() > 0) {
			bh.consume(buffer.get());
		}
		buffer.clear();
		fc.close();
	}

	@Benchmark
	public void nioFileChannelDirectBuffer(Blackhole bh) throws IOException {
		FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocateDirect((int) fc.size());
		fc.read(buffer);
		while (buffer.remaining() > 0) {
			bh.consume(buffer.get());
		}
		buffer.clear();
		fc.close();
	}

	@Benchmark
	public void nioFileChannelMap(Blackhole bh) throws IOException {
		FileChannel fc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = fc.map(MapMode.READ_ONLY, 0, fc.size());
		while (buffer.remaining() > 0) {
			bh.consume(buffer.get());
		}
		buffer.clear();
		fc.close();
	}

	@Benchmark
	@Warmup(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
	@Measurement(iterations = 10, time = 10, timeUnit = TimeUnit.SECONDS)
	@OutputTimeUnit(TimeUnit.MILLISECONDS)
	public void channelReadFile(Blackhole bh) throws IOException {
		FileInputStream is = new FileInputStream(file);
		FileChannel channel = is.getChannel();

		is.close();
		channel.close();
	}

	@Setup(Level.Trial)
	public void setup() {
		DataGenerator.generate();
		file = new File("src/test/resources/code-contest/fastsort/input-b.csv");
	}

	@TearDown(Level.Trial)
	public void teardown() {
		file.delete();
	}

	/**
	 * <pre>
	 * Benchmark                          Mode  Cnt    Score    Error  Units
	 * ReadFileTest.bufferedReadFile      avgt   10  197.554 ± 10.728  ms/op
	 * ReadFileTest.byteBufferedReadFile  avgt   10  406.221 ±  7.443  ms/op
	 * ReadFileTest.nioFileChannel  	  avgt   10    42.017 ± 0.489  ms/op
	 * ReadFileTest.nioReadFile     	  avgt   10    43.411 ± 1.010  ms/op
	 * ReadFileTest.nioFileChannelDirectBuffer  avgt   10   42.886 ± 0.579  ms/op
	 * ReadFileTest.nioFileChannelMap           avgt   10  196.472 ± 5.811  ms/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(ReadFileTest.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
