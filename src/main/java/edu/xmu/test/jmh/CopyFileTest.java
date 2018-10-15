package edu.xmu.test.jmh;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
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
import org.openjdk.jmh.infra.Blackhole;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import com.google.common.base.Charsets;

import edu.xmu.test.codekata.codecomp.DataGenerator;

/**
 * <b>Conclusion:</b> <br/>
 * 1> For ByteStream: <br/>
 * 1) The most efficient way to read file is via ByteBuffer in nio<br/>
 * 2) The most efficient way to write file is via <br/>
 * 2> For CharStream: <br/>
 * 1) The most efficient way to read file is via ByteBuffer in nio and then parse to CharBuffer <br/>
 * 2) The most efficient way to write file is via <br/>
 *
 */
@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 2, time = 10, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 5, time = 10, timeUnit = TimeUnit.SECONDS)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(1)
public class CopyFileTest {

	File inputFile;
	File outputFile;

	@Benchmark
	public void bufferedStreamFile() throws IOException {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(inputFile));
		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
		byte[] buffer = new byte[1024];
		int length = -1;
		while (-1 != (length = bis.read(buffer))) {
			bos.write(buffer, 0, length);
		}
		bis.close();
		bos.close();
	}

	@Benchmark
	public void bufferedReadWriteFile() throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(inputFile));
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outputFile));
		String line = null;
		while (null != (line = reader.readLine())) {
			bufferedWriter.write(line);
			bufferedWriter.newLine();
		}
		reader.close();
		bufferedWriter.close();
	}

	@Benchmark
	public void byteBufferReadWriteFileWithoutTraverse() throws IOException {
		FileChannel fc = FileChannel.open(inputFile.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
		fc.read(buffer);

		FileChannel outputFc = FileChannel.open(outputFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		buffer.flip();
		outputFc.write(buffer);
		fc.close();
		outputFc.close();
	}

	@Benchmark
	public void byteBufferReadBufferedOutputStreamWriteFile(Blackhole bh) throws IOException {
		FileChannel fc = FileChannel.open(inputFile.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
		fc.read(buffer);
		fc.close();

		BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile));
		byte[] b = new byte[1024];
		while (buffer.hasRemaining()) {
			int actualLength = b.length > buffer.remaining() ? buffer.remaining() : b.length;
			buffer.get(b, 0, actualLength);
			bos.write(b, 0, actualLength);
		}
		bos.close();
	}

	@Benchmark
	public void byteBufferReadWriteFileWithTraverse(Blackhole bh) throws IOException {
		FileChannel fc = FileChannel.open(inputFile.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
		fc.read(buffer);

		while (buffer.hasRemaining()) {
			buffer.get();
		}

		FileChannel outputFc = FileChannel.open(outputFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		buffer.flip();
		outputFc.write(buffer);
		fc.close();
		outputFc.close();
	}

	@Benchmark
	public void charBufferReadWriteFileWithTraverse(Blackhole bh) throws IOException {
		FileChannel fc = FileChannel.open(inputFile.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate((int) fc.size());
		fc.read(buffer);

		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();
		CharBuffer charBuffer = decoder.decode(buffer);
		while (charBuffer.hasRemaining()) {
			charBuffer.get();
		}

		FileChannel outputFc = FileChannel.open(outputFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		buffer.flip();
		outputFc.write(buffer);
		fc.close();
		outputFc.close();
	}

	@Setup(Level.Trial)
	public void setup() {
		DataGenerator.generate();
		inputFile = new File("src/test/resources/code-contest/fastsort/input-b.csv");
		outputFile = new File("src/test/resources/code-contest/fastsort/output-b.csv");
	}

	@TearDown(Level.Trial)
	public void teardown() {
		System.out.println("inputFile.length: " + inputFile.length() + ", outputFile.length: " + outputFile.length());
		inputFile.delete();
		outputFile.delete();
	}

	/**
	 * <pre>
	 * Benchmark                                                 Mode  Cnt    Score     Error  Units
	 * CopyFileTest.bufferedReadWriteFile                        avgt    5  351.332 ±  23.053  ms/op
	 * CopyFileTest.bufferedStreamFile                           avgt    5  159.196 ±  17.808  ms/op
	 * CopyFileTest.byteBufferReadBufferedOutputStreamWriteFile  avgt    5   43.539 ±   9.283  ms/op
	 * CopyFileTest.byteBufferReadWriteFileWithTraverse          avgt    5  653.405 ±  86.231  ms/op
	 * CopyFileTest.byteBufferReadWriteFileWithoutTraverse       avgt    5  701.428 ± 120.805  ms/op
	 * CopyFileTest.charBufferReadWriteFileWithTraverse          avgt    5  694.910 ±  60.319  ms/op
	 * </pre>
	 */
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(CopyFileTest.class.getSimpleName()).build();
		new Runner(opt).run();
	}
}
