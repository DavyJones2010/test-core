package edu.xmu.test.algorithm.distributed;

import org.junit.Test;

public class NewLineTest {

    @Test
    public void name() {
        System.out.println("line1" + System.getProperty("line.separator") + "line2");
    }
}
