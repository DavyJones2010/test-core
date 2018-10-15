package edu.xmu.test.algorithm.throttle;

import java.util.concurrent.TimeUnit;

import com.google.common.util.concurrent.RateLimiter;
import org.junit.Test;

/**
 * Created by kunlun.ykl on 2018/10/12.
 */
public class GuavaRateLimiterTest {
    final RateLimiter rateLimiter = RateLimiter.create(20.0, 4, TimeUnit.SECONDS);
    //final RateLimiter rateLimiter = RateLimiter.create(10.0);

    @Test
    public void name() throws Exception {
        //1. 没有后台线程往桶里放令牌, 完全由请求驱动, 根据时间戳进行限流处理.
        //2. max
        System.out.println("start");
        Thread.sleep(2000L);
        System.out.println("start2 " + System.currentTimeMillis());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    //while (!rateLimiter.tryAcquire()) {
                    //    try {
                    //        Thread.sleep(2000L);
                    //    } catch (InterruptedException e) {
                    //        e.printStackTrace();
                    //    }
                    //}
                    rateLimiter.acquire();
                    System.out.println("acquired " + System.currentTimeMillis());
                }
            }
        });
        thread.start();
        thread.join();
    }

}
