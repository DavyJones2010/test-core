package edu.xmu.test.guava.collect;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

public class IpUtils {
    private static final List<String> ipPool = Lists.newArrayList();

    static {
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.1");
        ipPool.add("127.0.0.2");
        ipPool.add("127.0.0.2");
        ipPool.add("127.0.0.2");
        ipPool.add("127.0.0.2");
        ipPool.add("127.0.0.2");
        ipPool.add("127.0.0.2");
        ipPool.add("127.0.0.3");
        ipPool.add("127.0.0.4");
        ipPool.add("127.0.0.4");
        ipPool.add("127.0.0.4");
        ipPool.add("127.0.0.4");
        ipPool.add("127.0.0.5");
        ipPool.add("127.0.0.5");
        ipPool.add("127.0.0.6");
        ipPool.add("127.0.0.7");
        ipPool.add("127.0.0.8");
        ipPool.add("127.0.0.8");
        ipPool.add("127.0.0.8");
        ipPool.add("127.0.0.8");
        ipPool.add("127.0.0.8");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.9");
        ipPool.add("127.0.0.10");
        ipPool.add("127.0.0.11");
        ipPool.add("127.0.0.12");
        ipPool.add("127.0.0.13");
        ipPool.add("127.0.0.14");
    }

    public static String poolRandomIp() {
        int size = ipPool.size();
        Random random = new Random();
        return ipPool.get(random.nextInt(size));
    }

    public static void main(String[] args) {
        //for (int i = 0; i < 100; i++) {
        //    System.out.println(IpUtils.poolRandomIp());
        //}

        Object a = new Object();
        Object b = new Object();
        System.out.println(a);
        System.out.println(b);

        b = a;
        a = new Object();
        System.out.println(a);
        System.out.println(b);
    }

}
