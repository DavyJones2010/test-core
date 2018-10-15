package edu.xmu.test.javase.javapuzzlers;

import org.junit.Test;

public class Puzzler8DosEquis {

	/**
	 * 三元表达式: <br>
	 * 1> 如果第二个和第三个操作数具有相同类型,返回类型即为此类型. <br>
	 * 2> 如果第一个操作数的类型是T,T表示byte,short或char,而另一个操作数是int类型的常量表达式,它的值可以用类型T表示的,那么条件表达式的类型就是T. <br>
	 * 3> 否则,将对操作数类型运用二进制数字提升,而条件表达式的类型就是第二个和第三个操作数被提升之后的类型. <br>
	 */
	@SuppressWarnings("unused")
	@Test
	public void test() throws Exception {
		char x = 'X';
		int i = 0;
		// output is: X
		System.out.println(true ? x : 0);
		// output is: 88
		System.out.println(false ? i : x);
	}
}
