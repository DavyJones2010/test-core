package edu.xmu.test.javase.timer;

import java.util.Timer;
import java.util.TimerTask;

public class TimerSample {
	public static void main(String[] args) {
		Timer timer = new Timer();
		System.out.println("Start");
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				System.out.println("Hello World");
			}
		}, 1000L);
	}
}
