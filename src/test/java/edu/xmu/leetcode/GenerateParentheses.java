package edu.xmu.leetcode;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import com.google.common.collect.Sets;

/**
 * Solution for {@link <a href="https://leetcode.com/problems/generate-parentheses/">Generate Parentheses</a>}
 */
public class GenerateParentheses {

	public List<String> generateParenthesis(int n) {
		return new ArrayList<String>(generate(n));
	}

	Set<String> generate(int n) {
		Set<String> list = new HashSet<String>();
		if (n == 1) {
			list.add("()");
			return list;
		} else {
			Set<String> subList = generate(n - 1);
			for (String str : subList) {
				list.add("(" + str + ")");
				list.add(str + "()");
				list.add("()" + str);
			}
		}

		return list;
	}

	@Test
	public void test() {
		// ["","","","","","","","(())(())","","","","","",""]
		// ["","","","","","","","","","","","",""]
		List<String> list = generateParenthesis(3);
		System.out.println(list.size());
		System.out.println(Sets.newHashSet(list).size());
		System.out.println(list);
	}
}
