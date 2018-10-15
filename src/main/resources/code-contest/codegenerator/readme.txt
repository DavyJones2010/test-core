Code Generator for Simple Compare

You need to generate code to compare different type of classes and different number of parameters.
for example : compare 2 int
The code generated should be as below:

public class SimpleCompare {
	public int compare(int a1, int a2) {
		if (a1 > a2) {
			return a1;
		} else {
			return a2;
		}
	}
}

What should you do :
1.Class name should be "SimpleCompare"
2.Class of variable should be same as given.
3.Variable names should be a1, a2...
4.You should print pretty code
5.int, long and Comparable classes should be supported.
6.count of "return", "if", "else" and parameter declaration will be checked.

The code generated will be under output/java/
The compiled classes will be under output/classes/