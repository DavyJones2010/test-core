package edu.xmu.test.codekata.codecomp;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.atomic.AtomicInteger;

public class ExternalSort {

	public static int HEADER_START = 167;

	public static void sort(final File input, final File output,
			final File tmpFile) throws Exception {
		Long currentTime = System.currentTimeMillis();
		FileInputStream fileInputStream = new FileInputStream(input);
		final NewDataHolder dataHolder = new NewDataHolder();

		byte[] currentBlock = new byte[NewDataHolder.MAX_SOURCE];
		byte[] previousBlock = null;
		byte[] t = new byte[NewDataHolder.MAX_SOURCE];
		int start = 0;

		byte[] header = new byte[HEADER_START];
		fileInputStream.read(header);
		int blockCount = 0;
		while (true) {
			blockCount = fileInputStream.read(currentBlock);
			if (blockCount == -1) {
				break;
			} else if (blockCount == NewDataHolder.MAX_SOURCE) {
				start = dataHolder.putBlockData(currentBlock, previousBlock,
						start, -1);
			} else {
				start = dataHolder.putBlockData(currentBlock, previousBlock,
						start, blockCount);
			}
			start++;
			previousBlock = currentBlock;
			currentBlock = t;
			t = previousBlock;
		}

		fileInputStream.close();
		System.out.println(System.currentTimeMillis() - currentTime);
		System.out.println("Source Index : " + dataHolder.sourceIndex);
		dataHolder.generateSortedIndex();
		System.out.println(System.currentTimeMillis() - currentTime);
		mutliOutput(dataHolder, currentTime, output, currentBlock, header);
		// singleOutput(dataHolder, currentTime, output, currentBlock, header);

	}

	public static void mutliOutput(NewDataHolder dataHolder, long currentTime,
			File output, byte[] currentBlock, byte[] header) throws Exception {
		final AtomicInteger count = new AtomicInteger(0);
		for (int i = 0; i < 20; i++) {
			if (dataHolder.indexRows[i] > 0) {
				SortedOutputRunable sr = new SortedOutputRunable();
				setOneValue(count, i);
				sr.count = count;
				sr.dataHolder = dataHolder;
				sr.index = i;
				Thread th = new Thread(sr);
				th.start();
			}
		}
		OutputStream file = new FileOutputStream(output);
		file.write(header);
		int len = 0;
		currentBlock = new byte[1024 * 800];
		for (int i = 0; i < 20; i++) {
			if (dataHolder.indexRows[i] > 0) {
				while (!isZeorValue(count, i)) {
					Thread.currentThread().sleep(100);
				}
				System.out.println("output index ;" + i);
				ByteArrayInputStream is = new ByteArrayInputStream(
						dataHolder.outputs[i].toByteArray());
				len = is.read(currentBlock);
				while (len != -1) {
					file.write(currentBlock, 0, len);
					file.flush();
					len = is.read(currentBlock);
				}
				dataHolder.outputs[i].close();
				is.close();
			}
		}
		file.close();
		System.out.println("Finish : "
				+ (System.currentTimeMillis() - currentTime));
	}

	public static void singleOutput(NewDataHolder dataHolder, long currentTime,
			File output, byte[] currentBlock, byte[] header) throws Exception {
		final AtomicInteger count = new AtomicInteger(0);
		for (int i = 0; i < 20; i++) {
			if (dataHolder.indexRows[i] > 0) {
				SortedRunable sr = new SortedRunable();
				setOneValue(count, i);
				sr.count = count;
				sr.dataHolder = dataHolder;
				sr.index = i;
				Thread th = new Thread(sr);
				th.start();
			}
		}
		while (count.get() != 0) {
			Thread.currentThread().sleep(20);
		}

		System.out.println(System.currentTimeMillis() - currentTime);
		OutputStream file = new FileOutputStream(output);
		file.write(header);
		ByteArrayOutputStream os = new ByteArrayOutputStream(
				dataHolder.maxIndexRows * 60);
		currentBlock = new byte[1024 * 800];
		for (int j = 0; j < 20; j++) {
			if (dataHolder.indexRows[j] > 0) {
				os.reset();
				dataHolder.outputStream_New(j, os);
				ByteArrayInputStream is = new ByteArrayInputStream(
						os.toByteArray());
				int len = is.read(currentBlock);
				while (len != -1) {
					file.write(currentBlock, 0, len);
					file.flush();
					len = is.read(currentBlock);
				}
				is.close();
			}
		}
		os.close();
		file.close();
		System.out.println("Finish : "
				+ (System.currentTimeMillis() - currentTime));
	}

	public static class SortedRunable implements Runnable {
		AtomicInteger count;
		NewDataHolder dataHolder;
		int index = 0;

		@Override
		public void run() {
			long a = System.currentTimeMillis();
			dataHolder.sort(index);
			System.out.println("index :" + index + ", sorted "
					+ (System.currentTimeMillis() - a));
			setZeorValue(count, index);
		}
	}

	public static class SortedOutputRunable implements Runnable {
		AtomicInteger count;
		NewDataHolder dataHolder;
		int index = 0;

		@Override
		public void run() {
			long a = System.currentTimeMillis();
			dataHolder.sort(index);
			System.out.println("index :" + index + ", sorted "
					+ (System.currentTimeMillis() - a));
			a = System.currentTimeMillis();
			try {
				dataHolder.outputStream_New_Sync(index);
			} catch (IOException e) {
				e.printStackTrace();
			}
			setZeorValue(count, index);
			System.out.println("index :" + index + ", join "
					+ (System.currentTimeMillis() - a));
		}
	}

	public static int[] digs = new int[] { 0x00000001, 0x00000002, 0x00000004,
			0x00000008, 0x00000010, 0x00000020, 0x00000040, 0x00000080,
			0x00000100, 0x00000200, 0x00000400, 0x00000800, 0x00001000,
			0x00002000, 0x00004000, 0x00008000, 0x00010000, 0x00020000,
			0x00040000, 0x00080000, 0x00100000, 0x00200000, 0x00400000,
			0x00800000, 0x01000000, 0x02000000, 0x04000000, 0x08000000 };

	public static int[] _digs = new int[] { 0x7FFFFFFe, 0x7FFFFFFd, 0x7FFFFFFb,
			0x7FFFFFF7, 0x7FFFFFeF, 0x7FFFFFdF, 0x7FFFFFbF, 0x7FFFFF7F,
			0x7FFFFeFF, 0x7FFFFdFF, 0x7FFFFbFF, 0x7FFFF7FF, 0x7FFFeFFF,
			0x7FFFdFFF, 0x7FFFbFFF, 0x7FFF7FFF, 0x7FFeFFFF, 0x7FFdFFFF,
			0x7FFbFFFF, 0x7FF7FFFF, 0x7FeFFFFF, 0x7FdFFFFF, 0x7FbFFFFF,
			0x7F7FFFFF, 0x7eFFFFFF, 0x7dFFFFFF, 0x7bFFFFFF, 0x77FFFFFF };

	public static void setOneValue(AtomicInteger count, int digLoc) {
		int before = count.get();
		int after = before | digs[digLoc];
		count.compareAndSet(before, after);
	}

	public static void setZeorValue(AtomicInteger count, int digLoc) {
		int before = count.get();
		int after = before & _digs[digLoc];
		count.compareAndSet(before, after);
	}

	public static boolean isZeorValue(AtomicInteger count, int digLoc) {
		int before = count.get();
		return (before | _digs[digLoc]) == _digs[digLoc];
	}

}
