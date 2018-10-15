package edu.xmu.test.javase.misc;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

/**
 * Created by davywalker on 16/10/9.
 */
public class BytesTest {
    @Test
    public void test(){
        String str = UUID.randomUUID().toString();
        byte[] bytes = str.getBytes();
        System.out.println(bytes);
        System.out.println(str);

        String str2 = "\001" + str;
        byte[] bytes2 = str2.getBytes();
        System.out.println(bytes2);
        System.out.println(str2);

        String str3 = "\u0001" + str;
        byte[] bytes3 = str3.getBytes();
        System.out.println(bytes3);
        System.out.println(str3);

        System.out.println(bytes instanceof byte[]);
        System.out.println(bytes2 instanceof byte[]);

        System.out.println(str2.equals(str));


        String token = "abc";
        byte[] tokenBytes = token.getBytes();
        System.out.println(tokenBytes.toString());

        tokenBytes = token.getBytes();
        System.out.println(tokenBytes.toString());
    }

    @Test
    public void bytesSerializeErrorSample() throws Exception {
        Map<Serializable, String> cache = Maps.newHashMap();
        String oldKey = "key";
        byte[] bytes = oldKey.getBytes();
        String newKey = addPrefix(bytes);
        cache.put(newKey, "value");

        bytes = oldKey.getBytes();
        newKey = addPrefix(bytes);
        String value = cache.get(newKey);
        System.out.println(value);
    }

    private String addPrefix(Serializable key) {
        return "prefix_" + key; // 关键错误在这里:
        // 1. byte[]不是按照内容变为String, 而是直接打印出对象地址.
        // 2. 同一个String, 每次调用getBytes, 都会返回新的byte[]对象.
    }
}
