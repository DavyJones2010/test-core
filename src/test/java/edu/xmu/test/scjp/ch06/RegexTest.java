package edu.xmu.test.scjp.ch06;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.collect.Lists;

public class RegexTest {
	@Test
	public void simpleSearchTest() {
		Pattern pattern = Pattern.compile("ab");
		Matcher matcher = pattern.matcher("abaaaba");
		List<Integer> starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(0, 4), starts);

		pattern = Pattern.compile("aba");
		matcher = pattern.matcher("abababa");
		starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		/*
		 * The consumed character will not be evaluated again.
		 */
		assertEquals(Lists.newArrayList(0, 4), starts);
	}

	@Test
	public void simpleSearchTest2() {
		Pattern pattern = Pattern.compile("[a-cA-C]");
		Matcher matcher = pattern.matcher("cafeBABE");
		List<Integer> starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(0, 1, 4, 5, 6), starts);

		pattern = Pattern.compile("[^a-cA-C]");
		matcher = pattern.matcher("cafeBABE");
		starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(2, 3, 7), starts);
	}

	@Test
	public void quantifierTest() {
		Pattern pattern = Pattern.compile("[cB][a-cA-C]+");
		Matcher matcher = pattern.matcher("cafeBABE");
		List<Integer> starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(0, 4), starts);

		pattern = Pattern.compile("\\d\\d\\d([-\\s])?\\d\\d\\d\\d");
		matcher = pattern.matcher("123 4567");
		starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(0), starts);
	}

	@Test
	public void greedTest() {
		Pattern pattern = Pattern.compile(".*xx");
		Matcher matcher = pattern.matcher("yyxxxyxx");
		List<Integer> starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(0), starts);

		pattern = Pattern.compile(".*?xx");
		matcher = pattern.matcher("yyxxxyxx");
		starts = Lists.newArrayList();
		while (matcher.find()) {
			starts.add(matcher.start());
		}
		assertEquals(Lists.newArrayList(0, 4), starts);
	}

	@Test
	public void test1() {
		String str = "";
		String[] split = null;

		// 普通字符: 包括所有大写和小写字母、所有数字、所有标点符号和一些其他符号
		str = "asbscses";
		split = str.split("s");
		System.out.println(str);
		System.out.println(split.length);
		printArray(split);
		printDelimeter("byNormalChar");

		// 非打印字符: \s 匹配任何空白字符，包括空格、制表符、换页符等等
		str = "a b c e ";
		System.out.println(str);
		split = str.split("\\s");
		System.out.println(split.length);
		printArray(split);
		printDelimeter("bySpaceChar");

		str = "a\\sb\\sc\\se\\s";
		split = str.split("\\\\s");
		System.out.println(str);
		System.out.println(split.length);
		printArray(split);
		printDelimeter("by\\sChar");

		// 特殊字符: 一些有特殊含义的字符,例如:
		// $ 输入字符串的结尾位置
		// ^ 匹配输入字符串的开始位置
		// () 标记一个子表达式的开始和结束位置,
		// * 匹配前面的子表达式零次或多次
		str = "asssbssscsesh";
		split = str.split("s*");
		System.out.println(str);
		System.out.println(split.length);
		printArray(split);
		printDelimeter("by*");
		// + 匹配前面的子表达式一次或多次,
		str = "asssbssscsesh";
		split = str.split("s+");
		System.out.println(str);
		System.out.println(split.length);
		printArray(split);
		printDelimeter("by+");
		// [ 标记一个中括号表达式的开始

		// ? 匹配前面的子表达式零次或一次，或指明一个非贪婪限定符
		str = "13916620287";
		printMatches(str, ("(86)?1\\d{10}$"));
		str = "08613916620287";
		printMatches(str, ("0?(86)?1\\d{10}$"));
		str = "8613916620287";
		printMatches(str, ("(86)?1\\d{10}$"));
		str = "86139166202871";
		printMatches(str, ("(86)?1\\d{10}$"));
		printDelimeter("by?");

		// \ 转义字符

		// 限定符: 用来指定正则表达式的一个给定组件必须要出现多少次才能满足匹配
		// * 匹配前面的子表达式零次或多次
		str = "zoo";
		printMatches(str, ("zo*"));
		printMatches(str, ("zo+"));
		printMatches(str, ("zo?"));
		printMatches(str, ("zoo?"));
		printDelimeter("zoo");
		str = "zoozoo";
		printMatches(str, ("zo*"));
		printMatches(str, ("zo+"));
		printMatches(str, ("zo?"));
		printMatches(str, ("zoo?"));
		printMatches(str, ("(zoo)*"));
		printDelimeter("zoozoo");

		// + 匹配前面的子表达式一次或多次
		str = "/home/admin/profilesync/logs/alipay_stop_httpretry.log.2016-02-22-00:2016-02-22 00:05:49,947|2088702781344381,alipay.user.certify.info.query";
		printMatches(str, ("|\\d{15},"));

		str = "bob";
		printMatches(str, ("bo{1,4}b"));
		str = "boooob";
		printMatches(str, ("bo{1,4}b"));
		str = "booooob";
		printMatches(str, ("bo{1,4}b"));
		str = "basketball";
		printMatches(str, ("b[a-z]{5}b[a-z]{1,3}"));
		str = "basketba";
		printMatches(str, ("b[a-z]{5}b[a-z]{1,3}"));
		str = "Sun";
		printMatches(str, ("[Ss]un"));
		str = "sun";
		printMatches(str, ("[Ss]un"));
		str = "aun";
		printMatches(str, ("[a-zA-Z]un"));
		printMatches(str, ("[a-zA-Z]un"));
		str = "Aun";
		printMatches(str, ("[a-zA-Z]un"));
		str = "0un";
		printMatches(str, ("[a-zA-Z]un"));

	}

	private void printMatches(String str, String regex) {
		System.out.println("\"" + str + "\"" + ".matches(\"" + regex + "\") = " + str.matches(regex));
	}

	private void printDelimeter(String methodName) {
		System.out.println("------------------------------" + methodName + "------------------------------\n");
	}

	private void printArray(String[] str) {
		System.out.println(Arrays.asList(str));
	}

	@Test
	public void test2() {
		String str = "";
		str = "0";
		System.out.println(RegexUtil.isNumber(str));
		str = "0123";
		System.out.println(RegexUtil.isNumber(str));
		str = "abc123";
		System.out.println(RegexUtil.isNumber(str));
		str = "123abc";
		System.out.println(RegexUtil.isNumber(str));

		str = "a";
		System.out.println(RegexUtil.isAlphabeta(str));
		str = "Z";
		System.out.println(RegexUtil.isAlphabeta(str));
		str = "0";
		System.out.println(RegexUtil.isAlphabeta(str));

		str = "08613916620287";
		System.out.println(RegexUtil.isMobilePhone(str));
		str = "8613916620287";
		System.out.println(RegexUtil.isMobilePhone(str));

	}

	@Test
    public void mobileTest(){
        Pattern pattern = Pattern.compile("^(86){0,1}1\\\\d{10}$");
        String str = "13916620287";
        System.out.println(pattern.matcher(str).matches());
    }
}
