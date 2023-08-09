package edu.xmu.test.apache.utils;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.PooledObjectFactory;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;

import java.util.UUID;

public class GenericObjectPoolTest {

    @Test
    public void name() throws Exception {
        PooledObjectFactory<String> factory = new MyFactory();
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMaxTotal(5);
        config.setMaxIdle(4);

        // Exhausted处理策略: 直接抛出异常; 一直等待, 底层使用LinkedBlockingDeque.takeFirst; 等待一定时间
        // Exhausted: 即对象池耗尽(池里idle的对象为空, 且无法新建对象);
        config.setBlockWhenExhausted(true);
        // 如果不设置timeout, 则s6会一直卡住; 该参数只有设置 BlockWhenExhausted=true 时才生效
        config.setMaxWaitMillis(100L);

        config.setTimeBetweenEvictionRunsMillis(1000);
        config.setMinIdle(4);
        config.setTestWhileIdle(true);

        GenericObjectPool<String> pool = new GenericObjectPool<String>(factory, config);

        Thread.sleep(10000L);

        String s = pool.borrowObject();
        System.out.println(s + " borrowed");
        Thread.sleep(1000L);

        String s2 = pool.borrowObject();
        System.out.println(s2 + " borrowed");
        Thread.sleep(1000L);


        String s3 = pool.borrowObject();
        System.out.println(s3 + " borrowed");
        Thread.sleep(1000L);

        String s4 = pool.borrowObject();
        System.out.println(s4 + " borrowed");
        Thread.sleep(1000L);

        String s5 = pool.borrowObject();
        System.out.println(s5 + " borrowed");
        Thread.sleep(1000L);

        // throw java.util.NoSuchElementException: Timeout waiting for idle object
        try {
            String s6 = pool.borrowObject();
            System.out.println(s6 + " borrowed");
        } catch (Exception e) {
            System.out.println(e);
        }

        // return s; s -> idle
        pool.returnObject(s);
        // return s2; s2 -> idle
        pool.returnObject(s2);

        // return s3; s3 -> idle
        pool.returnObject(s3);

        // return s4; s4 -> idle
        pool.returnObject(s4);

        s = pool.borrowObject();
        System.out.println(s + " borrowed");
    }

    class MyFactory extends BasePooledObjectFactory<String> {

        @Override
        public String create() throws Exception {
            String uuid = UUID.randomUUID().toString();
            System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + " created " + uuid);
            return uuid;
        }

        @Override
        public void destroyObject(PooledObject<String> p) throws Exception {
            System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + " destroyed " + p.getObject());
            super.destroyObject(p);
        }

        @Override
        public PooledObject<String> wrap(String obj) {
            return new DefaultPooledObject(obj);
        }

        @Override
        public boolean validateObject(PooledObject<String> p) {
            System.out.println(System.currentTimeMillis() + " " + Thread.currentThread().getName() + " validated " + p.getObject());
            return true;
        }
    }

}