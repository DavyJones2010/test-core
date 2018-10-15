package edu.xmu.test.jmh;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
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
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.google.common.base.Charsets;

import edu.xmu.test.codekata.codecomp.DataGenerator;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 5, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 5, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class WriteFileTest {

	ByteBuffer buffer;
	File outputFile;
	File inputFile;

	/**
	 * write(byte[]) is provided by FilteredOutputStream and not by BufferedOutputStream, therefore it doesn't utilize BUFFER facilitate <br/>
	 * The performance turned out to be <b>POOR</b>
	 */
	@Benchmark
	public void bufferedStreamWithoutBufferTest() throws IOException {
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
		bufferedOutputStream.write(buffer.array());
		bufferedOutputStream.close();
	}

	/**
	 * write(byte[], offset, length) is provided by BufferedOutputStream, therefore it does utilize BUFFER facilitate <br/>
	 * The performance turned out to be <b>GOOD</b>
	 */
	@Benchmark
	public void bufferedStreamWithBufferTest() throws IOException {
		BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream(outputFile));
		byte[] b = new byte[1024];
		while (buffer.hasRemaining()) {
			int actualLength = b.length > buffer.remaining() ? buffer.remaining() : b.length;
			buffer.get(b, 0, actualLength);
			bufferedOutputStream.write(b, 0, actualLength);
		}
		bufferedOutputStream.close();
	}

	/**
	 * 
	 */
	@Benchmark
	public void fileChannelMemoryMappedTest() throws IOException {
		// FIXME: Error occurs!
		FileChannel writerChannel = FileChannel.open(outputFile.toPath(), StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.CREATE, StandardOpenOption.READ,
				StandardOpenOption.WRITE);
		ByteBuffer writeBuffer = writerChannel.map(FileChannel.MapMode.READ_WRITE, 0, buffer.limit());
		writeBuffer.put(buffer.array(), 0, buffer.limit());
		System.out.println("Mapped once");
		writerChannel.close();
	}

	/**
	 * We do not need wrap buffer for Writer, becase writer has ALREADY provided BUFFER capability implicitly with buffer size: 1024<br/>
	 * {@link java.io.Writer.writeBuffer}
	 */
	@Benchmark
	public void fileWriterWithoutBufferTest() throws IOException {
		FileWriter fileWriter = new FileWriter(outputFile);

		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
		CharBuffer charBuffer = decoder.decode(buffer);

		fileWriter.write(charBuffer.array(), 0, charBuffer.limit());
		fileWriter.close();
	}

	/**
	 * write(char[], offset, length) is overrided by BufferedWriter with default buffer size: 8192
	 */
	@Benchmark
	public void fileWriterWithBufferTest() throws IOException {
		BufferedWriter fileWriter = new BufferedWriter(new FileWriter(outputFile));

		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
		CharBuffer charBuffer = decoder.decode(buffer);
		/*
		 * BufferedWriter.write(char[], offset, length): If length > bufferSize(default 8192), then the buffer takes no effect<br/>
		 * FileWriter.write(char[], offset, length) is called directly
		 */
		char[] b = new char[1024];
		while (charBuffer.hasRemaining()) {
			int actualLength = b.length > charBuffer.remaining() ? charBuffer.remaining() : b.length;
			charBuffer.get(b, 0, actualLength);
			fileWriter.write(charBuffer.array(), 0, actualLength);
		}
		fileWriter.close();
	}

	@Setup(Level.Invocation)
	public void invoSetUp() throws IOException {
		FileChannel fc = FileChannel.open(inputFile.toPath(), StandardOpenOption.READ);
		buffer = ByteBuffer.allocate((int) fc.size());
		fc.close();
	}

	@Setup(Level.Trial)
	public void setup() throws IOException {
		DataGenerator.generate();
		inputFile = new File("src/test/resources/code-contest/fastsort/input-b.csv");
		outputFile = new File("src/test/resources/code-contest/fastsort/output-b.csv");
	}

	@TearDown(Level.Trial)
	public void teardown() {
		outputFile.delete();
	}

	/**
	 * <pre>
	 * Benchmark                                      Mode  Cnt    Score     Error  Units
	 * WriteFileTest.bufferedStreamWithBufferTest     avgt    5   77.090 ±  11.076  ms/op
	 * WriteFileTest.bufferedStreamWithoutBufferTest  avgt    5  569.640 ± 153.154  ms/op
	 * WriteFileTest.fileWriterWithBufferTest         avgt    5  217.370 ±  28.940  ms/op
	 * WriteFileTest.fileWriterWithoutBufferTest      avgt    5  155.365 ±  14.989  ms/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(WriteFileTest.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
