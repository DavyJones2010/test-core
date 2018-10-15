package edu.xmu.test.javase.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

import org.junit.Test;

public class ReadFileTest {

	/**
	 * Time Consumption: 75ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void readTest() throws IOException {
		File file = new File("src/test/resources/input.csv");
		long start = System.currentTimeMillis();
		System.out.println("Start readTest");
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[(int) file.length()];
		fis.read(buffer);
		System.out.println("Finished readTest, cost: "
				+ (System.currentTimeMillis() - start));
		fis.close();
	}

	/**
	 * Time Consumption: 30ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void readTest2() throws IOException {
		File file = new File("src/test/resources/input.csv");
		long start = System.currentTimeMillis();
		System.out.println("Start readTest2");
		FileInputStream fis = new FileInputStream(file);
		byte[] buffer = new byte[1024 * 64];
		int i = 0;
		int size = 0;
		while (-1 != (i = fis.read(buffer))) {
			size += i;
		}
		fis.read(buffer);
		System.out.println("Finished readTest2, cost: "
				+ (System.currentTimeMillis() - start) + ", size: " + size);
		fis.close();
	}

	/**
	 * Time Consumption: 100ms
	 * 
	 * @throws IOException
	 */
	@Test
	public void readWithNIOTest() throws IOException {
		long start = System.currentTimeMillis();
		System.out.println("Start readWithNIOTest");

		RandomAccessFile f = new RandomAccessFile(
				"src/test/resources/input.csv", "r");
		FileChannel fc = f.getChannel();

		ByteBuffer buffer = ByteBuffer.allocate((int) f.length());
		int bytesRead = fc.read(buffer);

		while (bytesRead != -1) {
			buffer.flip();
			buffer.clear();
			bytesRead = fc.read(buffer);
		}
		System.out.println("Finished readWithNIOTest, cost: "
				+ (System.currentTimeMillis() - start));
		f.close();
	}

	/**
	 * Time Consumption: 16ms
	 * 
	 * @throws IOException
	 */
	@Test(timeout = 100)
	public void readWithMappedBufferTest() throws IOException {
		long start = System.currentTimeMillis();
		System.out.println("Start readWithMappedBufferTest");

		FileInputStream fis = new FileInputStream(
				"src/test/resources/input.csv");
		FileChannel fc = fis.getChannel();

		MappedByteBuffer buffer = fc.map(FileChannel.MapMode.READ_ONLY, 0,
				fc.size());

		System.out.println("Finished readWithMappedBufferTest, cost: "
				+ (System.currentTimeMillis() - start) + ", size: "
				+ buffer.limit());
		fis.close();
	}
}