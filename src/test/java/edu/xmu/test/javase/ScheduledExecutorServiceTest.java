package edu.xmu.test.javase;

import org.junit.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created by davywalker on 16/10/26.
 */
public class ScheduledExecutorServiceTest {
    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(4);

    static interface ScheduledTask extends Runnable {
        void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService);

        void changeRate(long initialDelay,
                        long rate,
                        TimeUnit unit);

        boolean stop(boolean force);
    }

    static class StatTask implements ScheduledTask {
        ScheduledExecutorService scheduledExecutorService;
        ScheduledFuture scheduledFuture;
        long initialDelay;
        long rate;
        TimeUnit unit;

        public void start() {
            this.scheduledFuture = scheduledExecutorService.scheduleAtFixedRate(this, initialDelay, rate, unit);
        }

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis() + ": stat");
        }

        @Override
        public void setScheduledExecutorService(ScheduledExecutorService scheduledExecutorService) {
            this.scheduledExecutorService = scheduledExecutorService;
        }

        @Override
        public void changeRate(long initialDelay,
                               long rate,
                               TimeUnit unit) {
            scheduledFuture.cancel(true);
            this.initialDelay = initialDelay;
            this.rate = rate;
            this.unit = unit;
            start();
        }

        @Override
        public boolean stop(boolean force) {
            return scheduledFuture.cancel(!force);
        }
    }

    @Test
    public void test() throws InterruptedException {
        StatTask statTask = new StatTask();
        statTask.setScheduledExecutorService(scheduledExecutorService);
        statTask.initialDelay = 0;
        statTask.rate = 100;
        statTask.unit = TimeUnit.MILLISECONDS;
        statTask.start();
        Thread.sleep(1000L);
        System.out.println("=================================");
        statTask.changeRate(0, 10, TimeUnit.MILLISECONDS);
        Thread.sleep(1000L);
        System.out.println("=================================");
        statTask.changeRate(0, 1000, TimeUnit.MILLISECONDS);
        Thread.sleep(1000L);
        System.out.println("=================================");
        statTask.changeRate(0, 10, TimeUnit.MILLISECONDS);
        Thread.sleep(2L);
        System.out.println("=================================");
        statTask.changeRate(0, 10, TimeUnit.MILLISECONDS);
        Thread.sleep(2L);
        System.out.println("=================================");
        statTask.changeRate(0, 10, TimeUnit.MILLISECONDS);
        boolean stop = statTask.stop(true);
        System.out.println(stop);
        stop = statTask.stop(true);
        System.out.println(stop);
        stop = statTask.stop(true);
        System.out.println(stop);
        Thread.sleep(1000L);
    }

}
