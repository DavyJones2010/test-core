package edu.xmu.test.effectivejava.sample.other;

import static org.junit.Assert.assertEquals;

import java.util.EnumMap;
import java.util.Map;

import org.junit.Test;

import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

public class Chapter6Enum33Test {

	@Test
	public void enumBasicTest() {
		System.out.println(RequestParam.USER_NAME);
		RequestParam param = RequestParam.fromString("username");
		System.out.println(param);
	}

	@Test
	public void enumOrdinalTest() {
		assertEquals(0, RequestParam.USER_NAME.ordinal());
		assertEquals(1, RequestParam.PASS_WORD.ordinal());
	}

	@Test
	public void enumMapTest() {
		EnumMap<RequestParam, String> map = Maps.newEnumMap(RequestParam.class);
		map.put(RequestParam.USER_NAME, "Davy");
		map.put(RequestParam.USER_NAME, "Yang");
		assertEquals("Yang", map.get(RequestParam.USER_NAME));
	}

	@SuppressWarnings("unused")
	private static class AnotherParam {
		private static final Map<String, AnotherParam> nameToInstance = Maps.newHashMap();
		private String name;

		public AnotherParam(String name) {
			// Can access static field for class in constructor
			nameToInstance.put(name, this);
			this.name = name;
		}
	}

	private static enum RequestParam {
		USER_NAME("username"), PASS_WORD("password");
		private String name;
		private static final Map<String, RequestParam> nameToInstance = Maps.newHashMap();
		static {
			for (RequestParam p : RequestParam.values()) {
				nameToInstance.put(p.toString(), p);
			}
		}

		private RequestParam(String name) {
			// Cannot access static field for enum in constructor
			// nameToInstance.put(name, this);
			this.name = name;
		}

		@Override
		public String toString() {
			return this.name;
		}

		public static RequestParam fromString(String str) {
			Preconditions.checkArgument(nameToInstance.containsKey(str));
			return nameToInstance.get(str);
		}
	}
}
