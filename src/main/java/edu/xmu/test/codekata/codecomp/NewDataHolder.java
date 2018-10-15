package edu.xmu.test.codekata.codecomp;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class NewDataHolder {

	private static int[] detal = new int[] { 0, 0, 0, 1, 10, 26, 36, 52 };

	public static int MAX_SOURCE = 1024 * 300;

	public Object[] source = null;
	public byte[] currentSource = null;
	public int sourceIndex = 0;
	public int lastedSourceIndex = 0;

	private long[] indexsA = null;
	private int[] indexsB = null;
	public int[][] sortedIndex = new int[20][];

	public int[] indexRows = new int[20];
	public ByteArrayOutputStream[] outputs = new ByteArrayOutputStream[20];
	public int row = -1;

	public NewDataHolder() {
		indexsA = new long[10000050 * 2];
		indexsB = new int[10000050 * 8];
		source = new Object[500];
		sourceIndex = 0;
		lastedSourceIndex = 0;
		currentSource = new byte[MAX_SOURCE];
		source[0] = currentSource;
	}

	public int putBlockData(byte[] currentBlock, byte[] previousBlock,
			int start, int blockEnd) {
		int end = 0;
		int base = 0;
		if (previousBlock != null) {
			byte[] line = new byte[128];
			int i = start;
			for (; i < previousBlock.length; i++) {
				line[i - start] = previousBlock[i];
			}
			int j = 0;
			i = i - start;
			for (; j < currentBlock.length; j++) {
				line[i + j] = currentBlock[j];
				if (currentBlock[j] == 10) {
					start = j + 1;
					base = putData(line, 0);
					processSource(line, 0, base);
					break;
				}
			}
		}

		end = getEnd(currentBlock, start, blockEnd);
		for (; start < end;) {
			base = putData(currentBlock, start);
			start = processSource(currentBlock, start, base);
			start++;
		}
		return end;
	}

	private int processSource(byte[] block, int start, int baseB) {
		int t = indexsB[baseB + 4];
		if (lastedSourceIndex + t > MAX_SOURCE) {
			currentSource = new byte[MAX_SOURCE];
			source[++sourceIndex] = currentSource;
			lastedSourceIndex = 0;
		}
		indexsB[baseB + 5] = lastedSourceIndex;
		for (; t > 0; t--) {
			currentSource[lastedSourceIndex++] = block[start++];
		}
		t = (int) indexsB[baseB + 6];
		indexsB[baseB + 6] = sourceIndex;
		return t;
	}

	private int getEnd(byte[] currentBlock, int start, int blockEnd) {
		if (blockEnd == -1) {
			int i = currentBlock.length - 1;
			while (currentBlock[i] != 10) {
				i--;
			}
			return i;
		}
		if (start == blockEnd) {
			return -1;
		}
		return blockEnd;
	}

	public void output(int count, int index) {
		for (int j = 0; j < count; j++) {
			int i = sortedIndex[index][j];
			long si = indexsB[i + 4];
			long ei = indexsB[i + 5];
			int a = (int) indexsB[i + 7];
			byte[] soi = (byte[]) source[a];
			for (long m = si; m <= ei; m++) {
				System.out.print((char) soi[(int) m]);
			}

		}
	}

	public static int[] first = new int[60];
	public static int[] scend = new int[60];

	static {
		scend[53] = 1;
		scend[54] = 1;
		scend[55] = 1;
		scend[56] = 1;
		scend[57] = 1;

		first[49] = 2;
		first[50] = 4;
		first[51] = 6;
		first[52] = 8;
		first[53] = 10;
		first[54] = 12;
		first[55] = 14;
		first[56] = 16;
		first[57] = 18;
	}

	public int putData(byte[] line, int start) {

		int baseA = (++row) * 2;
		int baseB = (row) * 8;

		indexsB[baseB + 7] = baseB;

		int cellStart = start;

		cellStart = testIndex0(line, start, baseA, baseB); // 0 - > out put base
															// B + 7 with group
															// identity

		cellStart = testIndex1(line, cellStart + 1, baseA + 1); // A0
		indexsB[baseB + 4] = cellStart - start + 1;

		cellStart = testIndex2(line, cellStart + 1, baseB);// B0

		cellStart = testIndexDate(line, cellStart + 1, baseB + 1);// B1

		cellStart = testIndexDate(line, cellStart + 1, baseB + 2);// B2

		indexsB[baseB + 6] = testIndex4(line, cellStart + 1, baseB + 3);// B3
		indexRows[(int) indexsB[baseB + 7]]++;
		return baseB;
	}

	public int maxIndexRows = 1;

	public void generateSortedIndex() {
		int index = 0;
		int[] _indexRows = new int[20];
		for (int i = 0; i < 20; i++) {
			sortedIndex[i] = new int[indexRows[i]];
			if (maxIndexRows < indexRows[i]) {
				maxIndexRows = indexRows[i];
			}
		}
		for (int i = 0; i <= row; i++) {
			index = indexsB[i * 8 + 7];
			sortedIndex[index][_indexRows[index]++] = i * 2;
		}
	}

	public void sort(int index) {
		if (indexRows[index] > 1) {
			quicksort(sortedIndex[index], indexRows[index]);
		}
	}

	public void outputStream(int index) throws IOException {
		outputs[index] = new ByteArrayOutputStream(
				sortedIndex[index].length * 60);
		OutputStream os = outputs[index];
		int baseB = 0;
		for (int lineIndex : sortedIndex[index]) {
			baseB = lineIndex * 4;
			os.write((byte[]) source[(int) indexsB[baseB + 6]],
					(int) indexsB[baseB + 4], (int) (indexsB[baseB + 5]
							- indexsB[baseB + 4] + 1));
		}
		os.flush();
	}

	public void outputStream_New_Sync(int index) throws IOException {
		outputs[index] = new ByteArrayOutputStream(2000 * 60);
		OutputStream os = outputs[index];
		outputStream_New(index, os);
	}

	public void outputStream_New(int index, OutputStream os) throws IOException {

		// outputs[index] = new ByteArrayOutputStream(sortedIndex[index].length
		// * 60);
		// OutputStream os = outputs[index];

		// process first line
		int baseA = sortedIndex[index][0];
		int baseB = baseA * 4;
		int preBaseA = baseA;
		int preBaseB = baseB;

		long preColumn1 = indexsA[baseA];
		long preColumn2 = indexsA[baseA + 1];
		long count = 1;

		long sum1 = indexsB[baseB];
		int maxDateInd = indexsB[baseB + 1];
		int minDateInd = indexsB[baseB + 2];
		long sum2 = indexsB[baseB + 3];

		for (int i = 1; i < sortedIndex[index].length; i++) {
			baseA = sortedIndex[index][i];
			baseB = baseA * 4;
			if (preColumn1 != indexsA[baseA]
					|| preColumn2 != indexsA[baseA + 1]) {
				byte[] s = (byte[]) source[(int) indexsB[preBaseB + 6]];
				int _base = (int) indexsB[preBaseB + 5];
				for (int j = 0; j < (int) indexsB[preBaseB + 4]; j++) {
					os.write(s[_base + j]);
				}
				outNumber(os, sum1);
				os.write(44);
				outData(os, maxDateInd);
				os.write(44);
				outData(os, minDateInd);
				os.write(44);
				outNumber(os, sum2);
				// os.write(44);
				// outNumber(os, count);
				os.write(13);
				os.write(10);
				preColumn1 = indexsA[baseA];
				preColumn2 = indexsA[baseA + 1];

				sum1 = indexsB[baseB];
				maxDateInd = indexsB[baseB + 1];
				minDateInd = indexsB[baseB + 2];
				sum2 = indexsB[baseB + 3];
				preBaseA = baseA;
				preBaseB = baseB;
				count = 1;
			} else {
				sum1 += indexsB[baseB];
				if (maxDateInd < indexsB[baseB + 1]) {
					maxDateInd = indexsB[baseB + 1];
				}
				if (minDateInd > indexsB[baseB + 2]) {
					minDateInd = indexsB[baseB + 2];
				}
				sum2 += indexsB[baseB + 3];
				count++;
			}
		}
		baseB = sortedIndex[index][sortedIndex[index].length - 1] * 4;
		byte[] s = (byte[]) source[(int) indexsB[preBaseB + 6]];
		int _base = (int) indexsB[preBaseB + 5];
		for (int j = 0; j < (int) indexsB[preBaseB + 4]; j++) {
			os.write(s[_base + j]);
		}
		outNumber(os, sum1);
		os.write(44);
		outData(os, maxDateInd);
		os.write(44);
		outData(os, minDateInd);
		os.write(44);
		outNumber(os, sum2);
		// os.write(44);
		// outNumber(os, count);
		os.write(13);
		os.write(10);
		os.flush();
	}

	private void outNumber(OutputStream os, long data) throws IOException {
		int size = (data < 0) ? stringSize(-data) + 1 : stringSize(data);
		char[] buf = new char[size];
		getChars(data, size, buf);
		for (int i = 0; i < size; i++) {
			os.write((int) buf[i]);
		}
	}

	final static char[] DigitTens = { '0', '0', '0', '0', '0', '0', '0', '0',
			'0', '0', '1', '1', '1', '1', '1', '1', '1', '1', '1', '1', '2',
			'2', '2', '2', '2', '2', '2', '2', '2', '2', '3', '3', '3', '3',
			'3', '3', '3', '3', '3', '3', '4', '4', '4', '4', '4', '4', '4',
			'4', '4', '4', '5', '5', '5', '5', '5', '5', '5', '5', '5', '5',
			'6', '6', '6', '6', '6', '6', '6', '6', '6', '6', '7', '7', '7',
			'7', '7', '7', '7', '7', '7', '7', '8', '8', '8', '8', '8', '8',
			'8', '8', '8', '8', '9', '9', '9', '9', '9', '9', '9', '9', '9',
			'9', };

	final static char[] DigitOnes = { '0', '1', '2', '3', '4', '5', '6', '7',
			'8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0',
			'1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3',
			'4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6',
			'7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
			'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0', '1', '2',
			'3', '4', '5', '6', '7', '8', '9', '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', };

	final static char[] digits = { '0', '1', '2', '3', '4', '5', '6', '7', '8',
			'9', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
			'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y',
			'z' };

	static int stringSize(long x) {
		long p = 10;
		for (int i = 1; i < 19; i++) {
			if (x < p)
				return i;
			p = 10 * p;
		}
		return 19;
	}

	static void getChars(long i, int index, char[] buf) {
		long q;
		int r;
		int charPos = index;
		char sign = 0;

		if (i < 0) {
			sign = '-';
			i = -i;
		}

		// Get 2 digits/iteration using longs until quotient fits into an int
		while (i > Integer.MAX_VALUE) {
			q = i / 100;
			// really: r = i - (q * 100);
			r = (int) (i - ((q << 6) + (q << 5) + (q << 2)));
			i = q;
			buf[--charPos] = DigitOnes[r];
			buf[--charPos] = DigitTens[r];
		}

		// Get 2 digits/iteration using ints
		int q2;
		int i2 = (int) i;
		while (i2 >= 65536) {
			q2 = i2 / 100;
			// really: r = i2 - (q * 100);
			r = i2 - ((q2 << 6) + (q2 << 5) + (q2 << 2));
			i2 = q2;
			buf[--charPos] = DigitOnes[r];
			buf[--charPos] = DigitTens[r];
		}

		// Fall thru to fast mode for smaller numbers
		// assert(i2 <= 65536, i2);
		for (;;) {
			q2 = (i2 * 52429) >>> (16 + 3);
			r = i2 - ((q2 << 3) + (q2 << 1)); // r = i2-(q2*10) ...
			buf[--charPos] = digits[r];
			i2 = q2;
			if (i2 == 0)
				break;
		}
		if (sign != 0) {
			buf[--charPos] = sign;
		}
	}

	private double compareSortedIndex(int i, int j) {
		if (indexsA[i] == indexsA[j]) {
			return indexsA[j + 1] - indexsA[i + 1];
		}
		return indexsA[i] - indexsA[j];
	}

	public void quicksort(int[] a, int size) {
		quicksort(a, 0, size - 1);
	}

	public void quicksort(int[] a, int left, int right) {
		int size = right - left + 1;
		switch (size) {
		case 0:
		case 1:
			break;
		case 2:
			if (compareSortedIndex(a[left], a[right]) > 0)
				swap(a, left, right);
			break;
		case 3:
			if (compareSortedIndex(a[left], a[right - 1]) > 0)
				swap(a, left, right - 1);
			if (compareSortedIndex(a[left], a[right]) > 0)
				swap(a, left, right);
			if (compareSortedIndex(a[left + 1], a[right]) > 0)
				swap(a, left + 1, right);
			break;
		default:
			int median = median(a, left, right);
			int partition = partition(a, left, right, median);
			quicksort(a, left, partition - 1);
			quicksort(a, partition + 1, right);
		}
	}

	private int median(int[] a, int left, int right) {
		int center = (left + right) / 2;
		if (compareSortedIndex(a[left], a[center]) > 0)
			swap(a, left, center);
		if (compareSortedIndex(a[left], a[right]) > 0)
			swap(a, left, right);
		if (compareSortedIndex(a[center], a[right]) > 0)
			swap(a, center, right);
		swap(a, center, right - 1);
		return right - 1;
	}

	private int partition(int[] a, int left, int right, int pivotIndex) {
		int leftIndex = left;
		int rightIndex = right - 1;
		while (true) {
			while (compareSortedIndex(a[++leftIndex], a[pivotIndex]) < 0)
				;
			while (compareSortedIndex(a[--rightIndex], a[pivotIndex]) > 0)
				;
			if (leftIndex >= rightIndex) {
				break; // pointers cross so partition done
			} else {
				swap(a, leftIndex, rightIndex);
			}
		}
		swap(a, leftIndex, right - 1); // restore pivot
		return leftIndex; // return pivot location
	}

	private static void swap(int[] a, int left, int right) {
		int t = a[left];
		a[left] = a[right];
		a[right] = t;
	}

	public int testIndex0(byte[] line, int start, int baseA, int baseB) {
		long l = 0;
		while (line[start] != ',') {
			l = l << 4 | (line[start++] - 48);
		}
		indexsA[baseA] = l;
		indexsB[baseB + 7] = (int) (l & 0xf0000) >> 16; // 9
		return start;
	}

	public int testIndex1(byte[] line, int start, int baseA) {
		long l = 0;
		int lenght = 10;
		while (line[start] != ',') {
			l = l << 6
					| (detal[(line[start] & 0xF0) >> 4] + (long) (line[start] & 0x0f));
			lenght--;
			start++;
		}
		indexsA[baseA] = l << (lenght * 6);
		return start;
	}

	public int testIndex2(byte[] line, int start, int baseB) {
		int l = 0;
		while (line[start] != ',') {
			l = l * 10 + (line[start++] - 48);
		}
		indexsB[baseB] = l;
		return start;

	}

	public int testIndexDate(byte[] line, int start, int base) {
		indexsB[base] = (((int) line[start + 6] & 0x0f) << 28)
				| (((int) line[start + 7] & 0x0f) << 24)
				| (((int) line[start + 8] & 0x0f) << 20)
				| (((int) line[start + 9] & 0x0f) << 16)
				| (((int) line[start + 0] & 0x0f) << 12)
				| (((int) line[start + 1] & 0x0f) << 8)
				| (((int) line[start + 3] & 0x0f) << 4)
				| ((int) line[start + 4] & 0x0f);
		return start + 10;
	}

	private void outData(OutputStream os, int data) throws IOException {
		os.write((data >> 12) & 0xf | 0x30);
		os.write((data >> 8) & 0xf | 0x30);
		os.write('/');
		os.write((data >> 4) & 0xf | 0x30);
		os.write(data & 0xf | 0x30);
		os.write('/');
		os.write((data >> 28) & 0xf | 0x30);
		os.write((data >> 24) & 0xf | 0x30);
		os.write((data >> 20) & 0xf | 0x30);
		os.write((data >> 16) & 0xf | 0x30);
		os.flush();
	}

	public static long testIndexDate1(char[] line, int start, int base) {
		return (long) ((long) line[start + 6] << 42)
				| (long) ((long) line[start + 7] << 36)
				| (long) ((long) line[start + 8] << 30)
				| (long) ((long) line[start + 9] << 24)
				| (long) ((long) line[start + 0] << 18)
				| (long) ((long) line[start + 1] << 12)
				| (long) ((long) line[start + 3] << 6)
				| (long) ((long) line[start + 4]);
	}

	public int testIndex4(byte[] line, int start, int base) {
		int f = 1;
		if (line[start] == '-') {
			start++;
			f = -1;
		}
		int l = 0;
		while (line[start] != 13) {
			l = l * 10 + (line[start++] - 48);
		}
		indexsB[base] = l * f;
		return start + 1;
	}

}
