package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class ZigZagConversionTest {
	public String convert(String s, int nRows) {
		Map<Integer, Map<Integer, Character>> convertedMap = new TreeMap<Integer, Map<Integer, Character>>();

		char[] chars = s.toCharArray();
		int prevRowNum = 0;
		int prevColNum = 0;
		boolean shouldIncreaseRowNum = true;
		for (int i = 1; i <= chars.length; i++) {
			char c = chars[i - 1];
			int currRowNum = 0;
			int currColNum = 0;
			if (shouldIncreaseRowNum) {
				currRowNum = prevRowNum + 1;
				currColNum = prevColNum;
			} else {
				currRowNum = prevRowNum - 1;
				currColNum = prevColNum + 1;
			}

			if (convertedMap.containsKey(currRowNum)) {
				if (!convertedMap.get(currRowNum).containsKey(currColNum)) {
					convertedMap.get(currRowNum).put(currColNum, c);
				} else {
					throw new RuntimeException("Unexpected error");
				}
			} else {
				Map<Integer, Character> row = new TreeMap<Integer, Character>();
				row.put(currColNum, c);
				convertedMap.put(currRowNum, row);
			}
			prevRowNum = currRowNum;
			prevColNum = currColNum;
			if (prevRowNum == 1) {
				shouldIncreaseRowNum = true;
			} else if (prevRowNum == nRows) {
				shouldIncreaseRowNum = false;
			}
		}

		String str = "";
		for (Map<Integer, Character> row : convertedMap.values()) {
			for (char val : row.values()) {
				str = str.concat("" + val);
			}
		}
		return str;
	}

	@Test
	public void zigZagConversionTest() {
		assertEquals("PAHNAPLSIIGYIR", convert("PAYPALISHIRING", 3));
	}
}
