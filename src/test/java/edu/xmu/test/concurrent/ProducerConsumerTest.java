package edu.xmu.test.concurrent;

import org.junit.Test;

public class ProducerConsumerTest {
    Object o = new Object();
    static boolean isEmpty = true;

    @Test
    public void name() throws InterruptedException {
        ConsumerThread t1 = new ConsumerThread("consumer", o);
        ProducerThread t2 = new ProducerThread("producer", o);
        t1.start();
        t2.start();
        t1.join();
        t2.join();
    }

    public static class ConsumerThread extends Thread {
        Object lock;

        public ConsumerThread(String name, Object lock) {
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    while (true) {
                        while (isEmpty) {
                            System.out.println("consumer is waiting");
                            lock.wait();
                        }
                        System.out.println("start consuming");
                        Thread.sleep((long) (Math.random() * 10000L));
                        System.out.println("finished consuming");
                        isEmpty = true;
                        lock.notify();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static class ProducerThread extends Thread {
        Object lock;

        public ProducerThread(String name, Object lock) {
            super(name);
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    while (true) {
                        while (!isEmpty) {
                            System.out.println("producer is waiting");
                            lock.wait();
                        }

                        System.out.println("start producing");
                        Thread.sleep((long) (Math.random() * 10000L));
                        isEmpty = false;
                        System.out.println("finished producing");

                        lock.notify();
                    }
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
