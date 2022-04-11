package edu.xmu.test.javase.concurrent;

import java.util.Calendar;

public class CFSSample {
    public static void main(String[] args) {
        for (int i = 0; i < 24; i++) {
            MyRunnable r = new MyRunnable();
            r.start();
        }
    }

    static class MyRunnable extends Thread {

        @Override
        public void run() {
            long last = Calendar.getInstance().getTimeInMillis();
            while (true) {
                long newMills = Calendar.getInstance().getTimeInMillis();
                // 如果超过10ms, 则可以认为发生了调度
                if (newMills - last >= 10) {
                    System.out.println(Thread.currentThread().getName() + " switched");
                }
                last = newMills;
                try {
                    // sleep 1ms
                    Thread.sleep(1L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
