package edu.xmu.test.algorithm.distributed;

import java.util.Date;

/**
 * Created by kunlun.ykl on 2018/10/18.
 */
public class DateTest {
    public static void main(String[] args) throws InterruptedException {

        Date date = new Date(System.currentTimeMillis());

        System.out.println(date);

        java.sql.Date date2 = new java.sql.Date(System.currentTimeMillis());
        System.out.println(date2);

        Date date3 = date2;
        System.out.println(date3);
        while(true){
            System.out.println("hello");
        }
    }
}
