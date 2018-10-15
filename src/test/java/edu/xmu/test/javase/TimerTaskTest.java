package edu.xmu.test.javase;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

public class TimerTaskTest {

	@Test
	public void timerTest() {
		Timer timer = new Timer();
		timer.schedule(new FetchTask(), 0L, 100L);
	}

	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		FetchTask fetchTask = new FetchTask();
		timer.schedule(fetchTask, 0L, TimeUnit.SECONDS.toMillis(1));
		Thread.sleep(TimeUnit.SECONDS.toMillis(5));
		fetchTask.cancel();
		System.out.println("canceled");
		fetchTask = new FetchTask();
		timer.schedule(fetchTask, 0L, TimeUnit.SECONDS.toMillis(1));
	}

	public static class FetchTask extends TimerTask {
		@Override
		public void run() {
			System.out.println("hello");
		}
	}
}
