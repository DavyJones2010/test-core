package edu.xmu.test.algorithm.distributed;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by kunlun.ykl on 2018/12/5.
 */
public class SampleTest {
    public static ConcurrentHashMap<String, String> map = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        map.put("aaa", "bbb");
        Set<String> keys = map.keySet();
        System.out.println(keys);
    }
}
