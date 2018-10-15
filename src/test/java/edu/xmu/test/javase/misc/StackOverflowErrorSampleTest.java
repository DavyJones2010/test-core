package edu.xmu.test.javase.misc;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.util.Map;

/**
 * Created by davywalker on 16/10/10.
 */
public class StackOverflowErrorSampleTest {
    class Cache{
        Map<String, String> cache = Maps.newHashMap();
        public String getValue(String key){
            return cache.get(key);
        }
        public String getValueNoException(String key){
            try{
                return getValue(key);
            }catch (Exception e){
                System.err.println("getValueError." + e.getMessage());
                return null;
            }
        }
    }
    class MyCache extends  Cache{
        @Override
        public String getValue(String key) {
            return super.getValueNoException(key);
        }

        @Override
        public String getValueNoException(String key) {
            return super.getValueNoException(key);
        }
    }

    @Test
    public void stackOverflowErrorReproduce(){
        MyCache myCache = new MyCache();
        String value = myCache.getValue("hello");
        System.out.println(value);
        /**
         * 调用栈是:
         * MyCache.getValue()
         * Cache.getValueNoException()
         * MyCache.getValue()
         */
    }

}
