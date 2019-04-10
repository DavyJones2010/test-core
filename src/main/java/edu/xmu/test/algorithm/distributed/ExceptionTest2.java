package edu.xmu.test.algorithm.distributed;

import org.junit.Test;

/**
 * Created by kunlun.ykl on 2018/10/18.
 */
public class ExceptionTest2 {
    Exception e;

    @Test
    public void name() throws Exception {
        startTransaction();
        doSomething();
        commit();
    }

    private void commit() {
        e.printStackTrace();
    }

    private void doSomething() {
        System.out.println("hello");
    }

    private void startTransaction() {
        e = new Exception();
    }
}
