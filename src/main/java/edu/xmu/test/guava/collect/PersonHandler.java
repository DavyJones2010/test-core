package edu.xmu.test.guava.collect;

import com.google.common.eventbus.Subscribe;

public class PersonHandler {
	@Subscribe
	public void handlePerson(People p) {
		System.out.println(String.format("[%s] start handlePerson for p: [%s]", this, p));

		System.out.println(String.format("[%s] finished handlePerson for p: [%s]", this, p));
	}

}
