package edu.xmu.leetcode;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * {@link <a href="https://leetcode.com/problems/compare-version-numbers/">Compare Version Numbers</a>} <br>
 */
public class CompareVersionNumber {
	@Test
	public void compareVersionTest() {
		assertEquals(-1, compareVersion("0.1", "1.3"));
		assertEquals(-1, compareVersion("1.1", "1.3"));
		assertEquals(-1, compareVersion("0", "1.3"));
		assertEquals(-1, compareVersion("0", "1"));
		assertEquals(0, compareVersion("01", "1"));
		assertEquals(-1, compareVersion("0.0.1", "0.1"));
		assertEquals(0, compareVersion("1", "1.0"));
		assertEquals(1, compareVersion("3.2.1.9.8144", "3.2"));
	}

	public int compareVersion(String version1, String version2) {
		if (null == version1 && null == version2) {
			return 0;
		} else if (null == version1 && null != version2) {
			version1 = "0";
		} else if (null != version1 && null == version2) {
			version2 = "0";
		}
		int mainVersionNo = 0;
		int mainVersionNo2 = 0;
		String subVersion1 = null;
		String subVersion2 = null;
		int index1 = version1.indexOf('.');
		int index2 = version2.indexOf('.');
		if (-1 == index1) {
			mainVersionNo = Integer.parseInt(version1);
		} else {
			mainVersionNo = Integer.parseInt(version1.substring(0, index1));
			subVersion1 = version1.substring(index1 + 1);
		}
		if (-1 == index2) {
			mainVersionNo2 = Integer.parseInt(version2);
		} else {
			mainVersionNo2 = Integer.parseInt(version2.substring(0, index2));
			subVersion2 = version2.substring(index2 + 1);
		}
		return mainVersionNo == mainVersionNo2 ? compareVersion(subVersion1, subVersion2) : normalizeResult(mainVersionNo - mainVersionNo2);
	}

	private int normalizeResult(int i) {
		return i == 0 ? 0 : (i > 0 ? 1 : -1);
	}
}
