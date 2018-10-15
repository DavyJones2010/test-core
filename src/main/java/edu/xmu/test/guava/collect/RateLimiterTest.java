package edu.xmu.test.guava.collect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

public class RateLimiterTest {

    private static class Req {
        private String ip;
        private String payload;

        public Req(String ip, String payload) {
            this.ip = ip;
            this.payload = payload;
        }

        public String getIp() {
            return ip;
        }

        public void setIp(String ip) {
            this.ip = ip;
        }

        public String getPayload() {
            return payload;
        }

        public void setPayload(String payload) {
            this.payload = payload;
        }
    }

    public void smoothRateLimiterTest() {

    }

    public static void main(String[] args) throws InterruptedException {
        MyRateLimiter myRateLimiter = new MyRateLimiter(1000L, 50L);
        Bucket bucket = new Bucket(myRateLimiter);
        Producer producer = new Producer(bucket);
        Producer producer2 = new Producer(bucket);
        Producer producer3 = new Producer(bucket);
        Consumer consumer = new Consumer(bucket);
        new Thread(producer).start();
        new Thread(producer2).start();
        new Thread(producer3).start();
        new Thread(consumer).start();
    }

    /**
     * 线程安全, 使用计数器法.
     */
    private static class MyRateLimiter {
        /**
         * 单位时间内的统计map信息
         */
        Map<String, Long> map = Maps.newConcurrentMap();
        Map<String, Long> snapshotMap = Maps.newConcurrentMap();
        /**
         * IP黑名单
         */
        Set<String> blacks = Sets.newConcurrentHashSet();

        /**
         * 统计时间窗口, 单位是毫秒
         */
        Long statInterval = 1000L;
        /**
         * 请求量阈值, 大于阈值的会被加入黑名单, 从而禁止请求
         */
        Long threshold = 50L;

        public MyRateLimiter(Long statInterval, Long threshold) {
            this.statInterval = statInterval;
            this.threshold = threshold;
            new Thread(new Stat()).start();
        }

        private class Stat implements Runnable {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(statInterval);
                        System.out.println(JSON.toJSONString(map));
                        /*
                         * 将前一秒钟的map信息放入到snapshotMap中, 同时清空map信息
                         */
                        snapshotMap = map;
                        map = Maps.newConcurrentMap();

                        /*
                         * 统计
                         */
                        for (Entry<String, Long> stringLongEntry : snapshotMap.entrySet()) {
                            if (stringLongEntry.getValue() > threshold) {
                                blacks.add(stringLongEntry.getKey());
                                System.out.println(
                                    stringLongEntry.getKey() + " added to blacks, req num: " + stringLongEntry
                                        .getValue());
                            }
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        public void onInBucket(Req req) throws Exception {
            if (blacks.contains(req.getIp())) {
                /*
                 * 在这里直接被限流阻断
                 */
                return;
            }
            Long aLong = map.get(req.getIp());
            if (null == aLong) {
                map.put(req.getIp(), 1L);
            } else {
                map.put(req.getIp(), aLong + 1);
            }
        }

        public void onOutBucket() {
            // do nothing
        }
    }

    private static class Bucket {
        MyRateLimiter myRateLimiter;
        List<Req> reqs = Lists.newCopyOnWriteArrayList();

        public Bucket(MyRateLimiter myRateLimiter) {
            this.myRateLimiter = myRateLimiter;
        }

        public void inBucket(Req req) throws Exception {
            myRateLimiter.onInBucket(req);
            reqs.add(req);
        }

        public Req outBucket() {
            myRateLimiter.onOutBucket();
            if (!reqs.isEmpty()) {
                return reqs.remove(reqs.size() - 1);
            } else {
                return null;
            }
        }
    }

    private static class Consumer implements Runnable {
        Bucket bucket;

        public Consumer(Bucket bucket) {
            this.bucket = bucket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 先随机sleep 100ms
                    Thread.sleep((long)(Math.random() * 10L));
                    //System.out.println(JSON.toJSONString(bucket.outBucket()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class Producer implements Runnable {
        Bucket bucket;

        public Producer(Bucket bucket) {
            this.bucket = bucket;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    // 先随机sleep 10ms
                    Thread.sleep((long)(Math.random() * 10L));
                    Req req = new Req(IpUtils.poolRandomIp(), UUID.randomUUID().toString());
                    bucket.inBucket(req);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            }
        }
    }

}
