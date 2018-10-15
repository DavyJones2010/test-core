package edu.xmu.test.guava.collect;

import java.util.Collections;
import java.util.Map;

import org.junit.Test;
import org.springframework.test.annotation.ExpectedException;

/**
 * Created by davywalker on 17/5/8.
 */
public class OASample {
    @Test(expected = UnsupportedOperationException.class)
    public void abstractMapTest() throws Exception {
        Map<String, String> stringStringMap = Collections.singletonMap("key", "value");
        stringStringMap.put("key2", "value2");
    }
}
