package edu.xmu.test.javase.concurrent;

import java.util.Calendar;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 测试juc中Future的用法
 */
public class FutureTest {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        Future<String> submit = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000L);
                return Calendar.getInstance().getTime().toString();
            }
        });
        while (!submit.isDone()) {
            try {
                Thread.sleep(100L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("loop once");
        }
        try {
            String result = submit.get();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
