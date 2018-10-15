package edu.xmu.test.concurrent;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

/**
 * @author davywalker
 */
public class FuturePatternTest {
    @Test
    public void name() throws Exception {
        ExecutorService executorService =
            Executors.newSingleThreadExecutor();
        Future<String> submit =
            executorService.submit(new MyTask());
        while (null != submit.get(100, TimeUnit.MILLISECONDS)) {
            System.out.println("wait");
        }
    }

    static class MyTask implements Callable<String> {

        @Override
        public String call() throws Exception {
            Thread.sleep(2000L);
            return UUID.randomUUID().toString();
        }
    }
}
