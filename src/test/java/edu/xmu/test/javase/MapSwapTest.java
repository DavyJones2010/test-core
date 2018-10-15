package edu.xmu.test.javase;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * Created by davywalker on 16/10/24.
 */
public class MapSwapTest {
    @Test
    public void swapTest() {
        Map<String, String> map = Maps.newHashMap();
        map.put("key", "value");
        Map<String, String> map2 = map;
        map = Maps.newHashMap();
        System.out.println(map.size());
        System.out.println(map2.size());
    }
}
