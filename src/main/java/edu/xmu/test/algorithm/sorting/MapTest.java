package edu.xmu.test.algorithm.sorting;

import java.util.Map;

import com.google.common.collect.Maps;

/**
 * Created by davywalker on 17/5/3.
 */
public class MapTest {
    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("key", null);
        map.put("key2", null);
        System.out.println(map);
    }
}
