package edu.xmu.test.javase.io;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.util.List;

import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.Lists;

public class EfficientIOTest {
	@Test
	@Ignore
	public void outputTest() throws IOException, InterruptedException {
		RandomAccessFile raf = new RandomAccessFile(new File(
				"src/test/resources/input-b.csv"), "r");
		ByteBuffer byteBuffer = ByteBuffer.allocate((int) raf.length());
		int num = raf.getChannel().read(byteBuffer);
		raf.close();
		byte[] bytes = byteBuffer.array();
		int threadCount = 8;
		int blockSize = num / threadCount;
		int remainderSize = num % threadCount;
		List<Thread> threads = Lists.newArrayList();
		List<ByteArrayOutputStream> osList = Lists.newArrayList();

		if (remainderSize != 0) {
			int startIndex = blockSize * threadCount;
			int endIndex = num;
			threadCount++;
			MyRunnable runnable = new MyRunnable(bytes, startIndex, endIndex);
			Thread t = new Thread(runnable);
			threads.add(t);
			t.start();
			osList.add(runnable.os);
		}

		for (int i = 0; i < 8; i++) {
			int startIndex = i * blockSize;
			int endIndex = (i + 1) * blockSize;
			MyRunnable runnable = new MyRunnable(bytes, startIndex, endIndex);
			Thread t = new Thread(runnable);
			threads.add(t);
			t.start();
			osList.add(runnable.os);
		}

		long start = System.currentTimeMillis();
		System.out.println("Start writeTest");
		OutputStream fos = new BufferedOutputStream(new FileOutputStream(
				new File("src/test/resources/output-b.csv")));
		for (int i = 0; i < threads.size(); i++) {
			Thread t = threads.get(i);
			t.join();
			ByteArrayOutputStream os = osList.get(i);
			fos.write(os.toByteArray());
		}
		fos.close();
		System.out.println("Finished writeTest, cost: "
				+ (System.currentTimeMillis() - start));
	}

	private static class MyRunnable implements Runnable {
		byte[] bytes;
		ByteArrayOutputStream os;

		int startIndex;
		int endIndex;

		public MyRunnable(byte[] bytes, int startIndex, int endIndex) {
			super();
			this.bytes = bytes;
			this.startIndex = startIndex;
			this.endIndex = endIndex;
			os = new ByteArrayOutputStream(endIndex - startIndex + 1);
		}

		@Override
		public void run() {
			for (int i = startIndex; i < endIndex; i++) {
				os.write(bytes[i]);
			}
			try {
				os.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
