package edu.xmu.test.javase.io;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.CharBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.charset.CharsetDecoder;
import java.nio.file.StandardOpenOption;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;

public class ByteArrayStreamVSByteBufferTest {
	/**
	 * 1) The scope of byte is [-128, +128), but the write() also accept int value, and the int value will be trimed implicitly<br/>
	 * 2) We should not use ByteArray***Stream in the performance critical code because all methods in it are synchronized in order to be thread safe<br/>
	 * Use java.nio.ByteBuffer instead
	 */
	@Test
	public void outputTest() {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		baos.write(-129);
		baos.write(-128);
		baos.write(0);
		baos.write(128);
		baos.write(129);
		// toByteArray() will create a COPY of the original array
		byte[] bytes = baos.toByteArray();
		Assert.assertArrayEquals(new byte[] { 127, -128, 0, -128, -127 }, bytes);
	}

	@Test
	public void inputTest() throws IOException {
		ByteArrayInputStream bais = new ByteArrayInputStream(new byte[] { (byte) -129, (byte) -128, (byte) 0, (byte) 128, (byte) 129 });
		byte[] b = new byte[5];
		int length = bais.read(b);
		assertEquals(5, length);
		Assert.assertArrayEquals(new byte[] { 127, -128, 0, -128, -127 }, b);

		bais.reset();
		assertEquals(127 & 0XFF, bais.read());
		assertEquals(-128 & 0XFF, bais.read());
		assertEquals(0 & 0XFF, bais.read());
		assertEquals(-128 & 0XFF, bais.read());
		assertEquals(-127 & 0XFF, bais.read());

		// -1 marks the stream end
		assertEquals(-1, bais.read());
	}

	/**
	 * 1) put() accept only byte type, if we want put int value, use putInt() and it will NOT perform trim at all<br/>
	 * 2) It is not threadsafe and methods are not aynchronized<br/>
	 * {@link <a href="http://stackoverflow.com/questions/2256887/java-using-type-punning-on-primitive-arrays">Pitfall of conversion ByteBuffer to IntBuffer</a>}
	 */
	@Test
	public void byteBufferTest() {
		ByteBuffer buffer = ByteBuffer.allocate(5);
		buffer.put((byte) -129);
		buffer.put((byte) -128);
		buffer.put((byte) 0);
		buffer.put((byte) 128);
		buffer.put((byte) 129);
		Assert.assertArrayEquals(new byte[] { 127, -128, 0, -128, -127 }, buffer.array());

		// Each int will occupy 4 bytes
		buffer = ByteBuffer.allocate(20);
		buffer.putInt(-129);
		buffer.putInt(-128);
		buffer.putInt(0);
		buffer.putInt(128);
		buffer.putInt(129);
		buffer.rewind();

		IntBuffer intBuffer = buffer.asIntBuffer();
		assertEquals(-129, intBuffer.get(0));
		assertEquals(-128, intBuffer.get(1));
		assertEquals(0, intBuffer.get(2));
		assertEquals(128, intBuffer.get(3));
		assertEquals(129, intBuffer.get(4));

		intBuffer.put(0, 122);
		assertEquals(122, intBuffer.get(0));
		assertEquals(122, buffer.getInt());

		// asIntBuffer(): converted IntBuffer's "final int[] hb" is null!
		assertFalse(intBuffer.hasArray());
		// Assert.assertArrayEquals(new int[] { -129, -128, 0, 128, 129 }, intBuffer.array());

		// transform IntBuffer (transformed from ByteBuffer) to IntArray
		int[] targetIntArray = new int[intBuffer.limit()];
		intBuffer.get(targetIntArray);
		Assert.assertArrayEquals(new int[] { 122, -128, 0, 128, 129 }, targetIntArray);

		// each char will occupy 2 bytes
		buffer = ByteBuffer.allocate(10);
		buffer.putChar('a');
		buffer.putChar('b');
		buffer.putChar('c');
		buffer.putChar('d');
		buffer.putChar('e');
		buffer.rewind();
		if (ByteOrder.BIG_ENDIAN == buffer.order()) {
			assertEquals((char) 97, buffer.get(1));
			assertEquals((char) 98, buffer.get(3));
			assertEquals((char) 99, buffer.get(5));
			assertEquals((char) 100, buffer.get(7));
			assertEquals((char) 101, buffer.get(9));
		} else if (ByteOrder.LITTLE_ENDIAN == buffer.order()) {
			assertEquals((char) 97, buffer.get(0));
			assertEquals((char) 98, buffer.get(2));
			assertEquals((char) 99, buffer.get(4));
			assertEquals((char) 100, buffer.get(6));
			assertEquals((char) 101, buffer.get(8));
		}
		CharBuffer charBuffer = buffer.asCharBuffer();
		assertEquals('a', charBuffer.get(0));
		assertEquals('b', charBuffer.get(1));
		assertEquals('c', charBuffer.get(2));
		assertEquals('d', charBuffer.get(3));
		assertEquals('e', charBuffer.get(4));
		char[] targetCharArray = new char[charBuffer.limit()];
		charBuffer.get(targetCharArray);
		Assert.assertArrayEquals(new char[] { 'a', 'b', 'c', 'd', 'e' }, targetCharArray);
	}

	@Test
	public void efficientByteBufferTest() throws IOException {
		File file = new File("src/test/resources/input.csv");
		FileChannel inputFc = FileChannel.open(file.toPath(), StandardOpenOption.READ);
		ByteBuffer buffer = inputFc.map(MapMode.READ_ONLY, 0, inputFc.size());
		CharsetDecoder decoder = Charsets.UTF_8.newDecoder();

		CharBuffer charBuffer = decoder.decode(buffer);
		int i = 0;
		while (i++ <= 100000 && charBuffer.hasRemaining()) {
			charBuffer.get();
			// char c = charBuffer.get();
			// System.out.print(c);
		}
		inputFc.close();

		buffer.flip();
		File outputFile = new File("src/test/resources/output.csv");
		FileChannel outputFc = FileChannel.open(outputFile.toPath(), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
		outputFc.write(buffer);
		outputFc.close();
	}
}
