package edu.xmu.test.concurrent;

import org.junit.Test;

public class ThreadWaitingTest {
    @Test
    public void name() throws InterruptedException {
        MyThread t = new MyThread();
        t.start();
//        t.join();
        t.join(30000L);
    }

    static class MyThread extends Thread {

        @Override
        public void run() {
            this.setName("MyThread");
            try {
                Thread.sleep(100000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
