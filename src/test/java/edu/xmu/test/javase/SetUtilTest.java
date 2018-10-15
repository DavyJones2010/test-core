package edu.xmu.test.javase;

import java.io.File;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.google.common.collect.Sets;
import com.google.common.collect.Sets.SetView;

public class SetUtilTest {
	@Test
	public void diffTest() throws Exception {
		String fileA = "/Users/davyjones/Downloads/zm/cid_from_alipay.log";
		String fileB = "/Users/davyjones/Downloads/zm/cid_from_cic_2.log";
		Set<String> setA = Sets.newHashSet(FileUtils.readLines(new File(fileA)));
		Set<String> setB = Sets.newHashSet(FileUtils.readLines(new File(fileB)));

		SetView<String> diffA = Sets.difference(setA, setB);
		System.out.println(diffA.size());
		System.out.println(diffA);
		FileUtils.writeLines(new File("/Users/davyjones/Downloads/zm/a_diff_b.txt"), diffA);

		System.out.println("============================================");

		SetView<String> diffB = Sets.difference(setB, setA);
		System.out.println(diffB.size());
		System.out.println(diffB);
		FileUtils.writeLines(new File("/Users/davyjones/Downloads/zm/b_diff_a.txt"), diffB);

		SetView<String> intersection = Sets.intersection(setA, setB);
		System.out.println(intersection);
		FileUtils.writeLines(new File("/Users/davyjones/Downloads/zm/b_intersect_a.txt"), intersection);

		SetView<String> union = Sets.union(setA, setB);
		System.out.println(union);
		FileUtils.writeLines(new File("/Users/davyjones/Downloads/zm/b_union_a.txt"), union);
	}
}
