package edu.xmu.test.concurrent;

public class ThreadTest {

    private static volatile int i = 0;

    public static void main(String[] args) {
        Object lock = new Object();

        Thread t1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long)(Math.random() * 1000L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock) {
                        if (i % 3 == 0) {
                            System.out.println("Thread1-" + i);
                            i++;
                            lock.notify();
                        }
                    }
                }
            }
        };

        Thread t2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long)(Math.random() * 1000L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock) {
                        if (i % 3 == 1) {
                            System.out.println("Thread2-" + i);
                            i++;
                            lock.notify();
                        }
                    }
                }
            }
        };

        Thread t3 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep((long)(Math.random() * 1000L));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized (lock) {
                        if (i % 3 == 2) {
                            System.out.println("Thread3-" + i);
                            i++;
                            lock.notify();
                        }
                    }
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
    }
}
