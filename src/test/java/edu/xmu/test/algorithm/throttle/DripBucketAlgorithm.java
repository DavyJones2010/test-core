package edu.xmu.test.algorithm.throttle;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * 滴桶法限流. 基于频率的限流
 * <li>优点: 平稳地处理请求.</li>
 * <li>缺点: 对突发请求的处理不好.</li>
 *
 * 优缺点与令牌桶刚好相反.
 * 到底使用哪种看系统需求. 一般都是使用令牌桶.
 * Created by kunlun.ykl on 2018/10/12.
 */
public class DripBucketAlgorithm {
    /**
     * 队列
     */
    final List<Object> bucket = Lists.newArrayList();
    final static int threshold = 10;

    @Test
    public void testHandleRequest() throws Exception {
        Thread generateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(new Random().nextInt(5000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (bucket.size() < threshold) {
                        bucket.add(bucket.size(), new Object());
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    System.out.println("Start " + System.currentTimeMillis());

                    while (!bucket.isEmpty()) {
                        bucket.remove(bucket.size() - 1);
                        System.out.println("Consumed " + System.currentTimeMillis());
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Throttled " + System.currentTimeMillis());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        generateThread.start();
        generateThread.join();
    }
}
