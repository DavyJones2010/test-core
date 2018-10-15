package edu.xmu.test.guava.basic;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.collect.Maps;

public class FunctionsTest {
	private Logger logger = Logger.getLogger(FunctionsTest.class);

	@Test
	public void forMapTest() {
		Map<String, State> map = Maps.newHashMap();
		Function<String, State> stateLookUpFunction = Functions.forMap(map, new State("UN", "Unknown"));
		map.put("NY", new State("NY", "New York"));
		map.put("CA", new State("CA", "California"));
		map.put("LA", new State("LA", "Los Angeles"));
		State state = stateLookUpFunction.apply("NY");
		logger.info(state);

		state = stateLookUpFunction.apply("DA");
		logger.info(state);
	}

	@Test
	public void composeTest() {
		Map<String, State> map = Maps.newHashMap();
		map.put("NY", new State("NY", "New York"));
		map.put("CA", new State("CA", "California"));
		map.put("LA", new State("LA", "Los Angeles"));

		Function<String, String> function = Functions.compose(new Function<State, String>() {
			public String apply(State input) {
				return input.getStateAddress();
			}
		}, Functions.forMap(map, new State("UN", "Unknown")));

		logger.info(function.apply("NY"));
		logger.info(function.apply("DA"));
	}
}
