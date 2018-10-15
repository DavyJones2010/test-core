package edu.xmu.test.javase;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Test;

import com.google.common.base.Charsets;

/**
 * <a href="http://www.javacodegeeks.com/2010/11/java-best-practices-char-to-byte-and.html">Char to Byte</a>
 *
 */
public class ByteToCharTest {

	@Test
	public void convertTest() {
		// int is signed, char is unsigned
		byte[] bytes = new byte[] { 97, 98, 99, 97 - 32, 98 - 32, 99 - 32 };
		String str = new String(bytes);
		assertEquals("abcABC", str);

		// there would be no data loss converting byte to char
		byte b = 97;
		char c = (char) b;
		assertEquals('a', c);

		// we've experienced data loss because it contains char bigger than 256
		bytes = "abcÈABC".getBytes(Charsets.US_ASCII);
		System.out.println(bytes.length);
		System.out.println(Arrays.toString(bytes));
		System.out.println(new String(bytes, Charsets.US_ASCII));

		// no data loss occurs
		bytes = "abcÈABC".getBytes(Charsets.UTF_8);
		System.out.println(bytes.length);
		System.out.println(Arrays.toString(bytes));
		System.out.println(new String(bytes, Charsets.UTF_8));
	}

	@Test
	public void convertTest2() {
		char a = 'È';

		byte b = (byte) a;
		assertEquals(-56, b);

		// char is unsigned 2 byte
		// -56(signed) -> 0X10MN -> 65480(unsigned)
		char c = (char) b;
		assertEquals(65480, c);

		c = (char) (0X00FF & c);

		System.out.println(c + "È" + (int) c);
	}
}
