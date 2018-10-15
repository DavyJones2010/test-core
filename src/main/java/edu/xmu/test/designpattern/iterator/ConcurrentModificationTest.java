package edu.xmu.test.designpattern.iterator;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class ConcurrentModificationTest {
    public static void main(String[] args) {
        Map<String, String> map = Maps.newHashMap();
        map.put("a", "a");
        map.put("b", "b");
        map.put("c", "c");
        List<String> tagNames = Lists.newArrayList(map.keySet());
        for (String tagName : tagNames) {
            if ("b".equalsIgnoreCase(tagName)) {
                // 不支持remove掉
                map.remove(tagName);
            }
        }
        System.out.println(JSON.toJSONString(map));
    }
}
