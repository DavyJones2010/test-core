package edu.xmu.test.codekata.bloomfilter;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class SpellCheckerTest {
	@Test
	public void checkSpellTest() throws FileNotFoundException, IOException {
		File wordFile = new File("src/main/resources/code-contest/bloomfilter/wordlist.txt");
		SpellChecker checker = new SpellChecker();
		checker.initWithBloomFilter(wordFile);
		assertFalse(checker.checkSpellWithBloomFilter("dasta"));
		assertTrue(checker.checkSpellWithBloomFilter("dust"));
		assertTrue(checker.checkSpellWithBloomFilter("zurfs"));
		assertTrue(checker.checkSpellWithBloomFilter("zurf"));
		assertFalse(checker.checkSpellWithBloomFilter("zurfa"));
		assertFalse(checker.checkSpellWithBloomFilter("WTF"));
	}

	@Test
	public void checkSpellWithHashSetTest() throws FileNotFoundException, IOException {
		File wordFile = new File("src/main/resources/code-contest/bloomfilter/wordlist.txt");
		SpellChecker checker = new SpellChecker();
		checker.initWithHashSet(wordFile);
		assertFalse(checker.checkSpellWithHashSet("dasta"));
		assertTrue(checker.checkSpellWithHashSet("dust"));
		assertTrue(checker.checkSpellWithHashSet("zurfs"));
		assertTrue(checker.checkSpellWithHashSet("zurf"));
		assertFalse(checker.checkSpellWithHashSet("zurfa"));
		assertFalse(checker.checkSpellWithHashSet("WTF"));
	}
}
