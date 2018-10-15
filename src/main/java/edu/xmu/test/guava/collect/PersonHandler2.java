package edu.xmu.test.guava.collect;

import com.google.common.eventbus.Subscribe;

public class PersonHandler2 extends PersonHandler {
	@Subscribe
	public void handlePerson(People p) {
		System.out.println(String.format("PersonHandler2 [%s] start handlePerson for p: [%s]", this, p));
		long time = System.currentTimeMillis();
		long targetTime = (long) (time + 5000 * Math.random());

		for (long i = time; i <= targetTime;) {
			i = System.currentTimeMillis();
		}
		System.out.println(String.format("PersonHandler2 [%s] finished handlePerson for p: [%s]", this, p));
	}
}
