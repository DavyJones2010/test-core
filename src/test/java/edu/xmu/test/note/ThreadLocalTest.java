package edu.xmu.test.note;

import java.util.Map;
import java.util.WeakHashMap;

/**
 * Created by davywalker on 17/2/13.
 */
public class ThreadLocalTest {
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    static MyThreadLocal<String> threadLocal2 = new MyThreadLocal<>();

    public static void main(String[] args) throws InterruptedException {
        threadLocal.set("hello");
        threadLocal2.set("world");
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(threadLocal.get());
                System.out.println(threadLocal2.get());
            }
        });
        threadLocal.get();
        threadLocal2.get();
        Thread.sleep(1000L);
    }

    private static class MyThreadLocal<T> {
        private Map<Thread, T> values = new WeakHashMap<>();

        public synchronized void set(T value) {
            values.put(Thread.currentThread(), value);
        }

        public synchronized T get() {
            return values.get(Thread.currentThread());
        }
    }
}
