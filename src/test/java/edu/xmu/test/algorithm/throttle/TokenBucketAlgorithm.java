package edu.xmu.test.algorithm.throttle;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;
import org.junit.Test;

/**
 * 令牌桶. 基于数量的限流
 *
 * <li>优点: 很好的处理突发请求</li>
 * <li>缺点: 如果buffer设置的过大, 瞬时可能导致系统压力毛刺</li>
 *
 * Created by kunlun.ykl on 2018/10/12.
 */
public class TokenBucketAlgorithm {
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
                        Thread.sleep(new Random().nextInt(50));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if (bucket.size() < threshold) {
                        // accept request in queue
                        bucket.add(bucket.size(), new Object());
                    } else {
                        // discard request
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
                        System.out.println("Handled " + System.currentTimeMillis());
                        try {
                            Thread.sleep(new Random().nextInt(1000));
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.println("Throttled " + System.currentTimeMillis());
                    try {
                        Thread.sleep(new Random().nextInt(1000));
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
