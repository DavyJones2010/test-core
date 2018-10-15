package edu.xmu.test.algorithm.distributed;

import java.io.FileNotFoundException;

/**
 * Created by kunlun.ykl on 2018/8/21.
 */
public class ExceptionSample {
    public static void main(String[] args) throws Exception {
        try {
            System.out.println("A");
            return;
            //throw new FileNotFoundException();
        } catch (Exception e) {
            System.out.println("B");
            throw new Exception();
        } finally {
            System.out.println("C");
        }
    }
}
