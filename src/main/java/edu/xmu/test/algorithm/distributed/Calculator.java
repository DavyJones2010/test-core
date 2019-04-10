package edu.xmu.test.algorithm.distributed;

/**
 * Created by kunlun.ykl on 2018/11/9.
 */
public class Calculator {
    public Calculator() {
    }

    public Calculator(String name) {
        System.out.println(name);
    }

    public static void main(String[] args) {
        int i = 100;
        String str = echo("hello world");
        System.out.println("hello world i=" + i + " str=" + str);
    }

    public static String echo(String str) {
        return str;
    }
}
