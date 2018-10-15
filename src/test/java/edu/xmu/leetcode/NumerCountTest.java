package edu.xmu.leetcode;

import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Maps;
import org.junit.Test;

/**
 * Created by davywalker on 17/8/25.
 */
public class NumerCountTest {

    @Test
    public void countTest() throws Exception {
        Map<Character, Integer> map = Maps.newHashMap();
        String s = "Hello world";
        char[] chars = s.toCharArray();
        for (char aChar : chars) {
            if (map.containsKey(aChar)) {
                int count = map.get(aChar);
                count++;
                map.put(aChar, count);
            } else {
                map.put(aChar, 1);
            }
        }
        //System.out.println(JSON.toJSON(map));
        System.out.println(map.get('l'));
    }

    @Test
    public void test2() throws Exception {
        Long value = null;
        System.out.println(value);
    }
}
