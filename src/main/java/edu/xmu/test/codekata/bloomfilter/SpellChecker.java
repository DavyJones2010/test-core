package edu.xmu.test.codekata.bloomfilter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Set;

import com.google.common.base.Charsets;
import com.google.common.collect.Sets;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.Funnels;
import com.google.common.hash.PrimitiveSink;

/**
 * Solution for {@link <a href="http://codekata.com/kata/kata05-bloom-filters/">Kata05: Bloom Filters</a>}
 */
public class SpellChecker {
	private static final BloomFilter<String> filter = BloomFilter.create(Funnels.stringFunnel(Charsets.UTF_8), 409600);
	private static final Set<String> set = Sets.newHashSet();

	static enum WordFunnel implements Funnel<String> {
		INSTANCE;
		@Override
		public void funnel(String from, PrimitiveSink into) {
			into.putUnencodedChars(from);
		}
	}

	public void initWithBloomFilter(File file) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			while (null != (line = br.readLine())) {
				filter.put(line);
			}
		}
	}

	public void initWithHashSet(File file) throws FileNotFoundException, IOException {
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line = null;
			while (null != (line = br.readLine())) {
				set.add(line);
			}
		}
	}

	public boolean checkSpellWithBloomFilter(String word) {
		return filter.mightContain(word);
	}

	public boolean checkSpellWithHashSet(String word) {
		return set.contains(word);
	}
}
