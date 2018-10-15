package edu.xmu.test.guava.basic;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;

import edu.xmu.test.guava.collect.People;
import edu.xmu.test.guava.collect.PersonHandler;
import edu.xmu.test.guava.collect.PersonHandler2;

public class EventBusTest {
	@Test
	public void postTest() throws InterruptedException {
		PersonHandler handler = new PersonHandler();
		PersonHandler handler2 = new PersonHandler();
		PersonHandler handler3 = new PersonHandler();

		EventBus eventBus = new EventBus();
		eventBus.register(handler);
		eventBus.register(handler2);
		eventBus.register(handler3);
		eventBus.post(new People("Davy", 24));

		System.out.println("=================================================");

		handler = new PersonHandler2();
		handler2 = new PersonHandler2();
		handler3 = new PersonHandler2();

		ExecutorService executor = Executors.newFixedThreadPool(2);
		eventBus = new AsyncEventBus(executor);
		eventBus.register(handler);
		eventBus.register(handler2);
		eventBus.register(handler3);
		eventBus.post(new People("Cal", 22));

		System.out.println("=================================================");

		executor.shutdown();
		while (!executor.isTerminated()) {
			System.out.println("Wait 1 more second");
			executor.awaitTermination(1, TimeUnit.SECONDS);
		}
	}
}
