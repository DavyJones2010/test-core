package edu.xmu.test.javase.programmingpearls.ch2;

import org.junit.Test;

/**
 * Chapter2.2 Example
 * 
 * @author davyjones
 *
 */
public class FSMTest {
	@Test
	public void test() {
		// true: LastInputOne; false: LastInputZero
		boolean state = false;
		byte[] input = new byte[] { 0, 1, 1, 0, 1, 0, 1, 1, 1 };
		byte[] output = new byte[input.length];
		for (int i = 0; i < input.length; i++) {
			byte b = input[i];
			if (state == false && b == 0) {
				state = false;
				output[i] = 0;
			} else if (state == false && b == 1) {
				state = true;
				output[i] = 0;
			} else if (state == true && b == 0) {
				state = false;
				output[i] = 0;
			} else if (state == true && b == 1) {
				state = true;
				output[i] = 1;
			}
		}
		System.out.println(output);
	}
}
