package edu.xmu.test.javase.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;

import org.junit.Test;

import com.google.common.base.Charsets;

public class WriteFileTest {

	private char[] prepareData() {
		char[] data = new char[1024 * 1024 * 20];
		for (int i = 0; i < 1024 * 1024 * 20; i++) {
			data[i] = (char) (0XFF * Math.random());
		}
		return data;
	}

	/**
	 * Time Consumption: 540ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void writeTest() throws IOException {
		char[] data = prepareData();
		File file = new File("src/test/resources/output.txt");
		long start = System.currentTimeMillis();
		System.out.println("Start writeTest");
		FileWriter fw = new FileWriter(file);
		fw.write(data);
		System.out.println("Finished writeTest, cost: "
				+ (System.currentTimeMillis() - start));
		fw.close();
	}

	/**
	 * Time Consumption: 520ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void writeTest2() throws IOException {
		char[] data = prepareData();

		File file = new File("src/test/resources/output.txt");
		long start = System.currentTimeMillis();
		System.out.println("Start writeTest2");
		BufferedWriter bw = new BufferedWriter(new FileWriter(file),
				data.length);
		bw.write(data);
		System.out.println("Finished writeTest2, cost: "
				+ (System.currentTimeMillis() - start));
		bw.close();
	}

	/**
	 * Time Consumption: 380ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void writeWithNIOTest() throws IOException {
		char[] data = prepareData();
		FileOutputStream fos = new FileOutputStream(
				"src/test/resources/output.txt");
		WritableByteChannel oc = Channels.newChannel(fos);

		ByteBuffer byteBuffer = Charsets.UTF_8.encode(CharBuffer.wrap(data));
		long start = System.currentTimeMillis();
		System.out.println("Start writeWithNIOTest");
		while (byteBuffer.hasRemaining()) {
			oc.write(byteBuffer);
			System.out.println("Wrote once");
		}
		System.out.println("Finished writeWithNIOTest, cost: "
				+ (System.currentTimeMillis() - start));
		fos.close();
	}

	/**
	 * Time Consumption: 20ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void writeWithMappedBufferTest() throws IOException {
		char[] data = prepareData();
		ByteBuffer byteBuffer = Charsets.UTF_8.encode(CharBuffer.wrap(data));

		RandomAccessFile file = new RandomAccessFile(
				"src/test/resources/output.txt", "rw");
		FileChannel fc = file.getChannel();

		long start = System.currentTimeMillis();
		System.out.println("Start writeWithMappedBufferTest");

		MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_WRITE, 0,
				byteBuffer.limit());
		while (byteBuffer.hasRemaining()) {
			buffer.put(byteBuffer);
			System.out.println("Wrote once");
		}
		System.out.println("Finished writeWithMappedBufferTest, cost: "
				+ (System.currentTimeMillis() - start));
		fc.close();
		file.close();
	}
}