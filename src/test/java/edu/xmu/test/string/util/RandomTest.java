package edu.xmu.test.string.util;

import org.junit.Test;

import java.util.Random;

/**
 * Created by davywalker on 16/10/25.
 */
public class RandomTest {
    @Test
    public void test(){
        Random random = new Random();
        while(true){
            random.nextInt(16);
        }
    }
}
