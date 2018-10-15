package edu.xmu.test.javase;

import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.DefaultValueProcessor;

public class JSONObjectTest {
	JsonConfig jsonConfig = new JsonConfig();

	@Before
	@SuppressWarnings("rawtypes")
	public void setup() {
		jsonConfig.registerDefaultValueProcessor(Integer.class, new DefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
		jsonConfig.registerDefaultValueProcessor(Long.class, new DefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
		jsonConfig.registerDefaultValueProcessor(Date.class, new DefaultValueProcessor() {
			public Object getDefaultValue(Class type) {
				return null;
			}
		});
	}

	@Test
	public void fromObjectTest() {
		JSONObject jsonObject = JSONObject.fromObject((Object) new DummyBean(26, "davy"), jsonConfig);
		System.out.println(jsonObject);
		System.out.println(jsonObject.getString("gender"));
		System.out.println(jsonObject.getString("age"));
		System.out.println(jsonObject.get("age"));
	}

	@Test
	public void fromObjectTest2() {
		List<String> data = Lists.newArrayList("A", "B", "C");
		JSONArray json = JSONArray.fromObject(data, jsonConfig);
		Object array = JSONArray.toArray(json);
		System.out.println(array);
	}
}
