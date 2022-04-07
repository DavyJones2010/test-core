package edu.xmu.test.javase.concurrent;

import org.junit.Test;

public class StringBuilderTestTest {

    @Test
    public void name() {
        StringBuilder sb = new StringBuilder();
        sb.append("hello");
        sb.append(" ");
        sb.append("world");
        System.out.println(sb.toString());
        // StringBuilder 来复用空间
        sb.setLength(0);
        System.out.println(sb.toString());
        sb.append("a");
        System.out.println(sb.toString());
        System.out.println(sb.length());

    }
}