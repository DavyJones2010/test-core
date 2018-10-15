package edu.xmu.test.guava.basic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class JoinerTest {
	private Logger logger = Logger.getLogger(JoinerTest.class);

	@Test
	public void onTest() {
		String str = Joiner.on("ABC").join("1", "2", "3");
		assertEquals("1ABC2ABC3", str);

		String columns = Joiner.on(",").join("COLUMN_A", "COLUMN_B", "COLUMN_C");
		String values = Joiner.on(",").join("VALUE_A", "VALUE_B", "VALUE_C");
		String sql = "INSERT INTO TABLE (" + columns + ") VALUES (" + values + ")";
		assertEquals("INSERT INTO TABLE (COLUMN_A,COLUMN_B,COLUMN_C) VALUES (VALUE_A,VALUE_B,VALUE_C)", sql);
	}

	@Test
	public void useForNullTest() {
		String str = Joiner.on(",").skipNulls().join("1", null, "2", "3");
		assertEquals("1,2,3", str);

		str = Joiner.on(",").useForNull("0").join("1", null, "2", "3");
		assertEquals("1,0,2,3", str);
	}

	@Test
	public void appendToTest() {
		StringBuilder str = new StringBuilder();
		str = Joiner.on(',').appendTo(str, Lists.newArrayList("A", "B", "C"));
		assertEquals("A,B,C", str.toString());

		str = new StringBuilder();
		str.append(Joiner.on(',').join(Lists.newArrayList("A", "B", "C")));
		assertEquals("A,B,C", str.toString());
	}

	@Test
	public void witKeyValueSeparatorTest() throws IOException {
		Map<String, String> data = Maps.newHashMap();
		data.put("Name", "Davy");
		data.put("Gender", "Male");
		data.put("Age", "24");
		String str = Joiner.on(",").withKeyValueSeparator("=").join(data);
		logger.info(str);
		assertEquals("Name=Davy,Age=24,Gender=Male", str);
	}

}
