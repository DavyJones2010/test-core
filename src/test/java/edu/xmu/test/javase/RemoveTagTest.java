package edu.xmu.test.javase;

import org.junit.Test;

public class RemoveTagTest {

	public static Integer removeTag(Integer sourceTag, Integer tag) {
		if (null == sourceTag) {
			return 0;
		}
		if (null == tag) {
			return sourceTag;
		}
		return sourceTag & ~tag;
	}

	@Test
	public void test() {
		System.out.println(removeTag(9, 2));
	}
}
