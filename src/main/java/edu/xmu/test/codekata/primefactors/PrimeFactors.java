package edu.xmu.test.codekata.primefactors;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * The recursive solution for: {@link <a href="http://butunclebob.com/ArticleS.UncleBob.ThePrimeFactorsKata">ThePrimeFactorsKata</a>}
 *
 */
public class PrimeFactors {
	public static List<Integer> generate(int n) {
		List<Integer> primes = Lists.newArrayList();
		for (int i = 2; i <= n; i++) {
			if (n % i == 0) {
				primes.add(i);
				primes.addAll(generate(n / i));
				break;
			}
		}
		return primes;
	}
}
