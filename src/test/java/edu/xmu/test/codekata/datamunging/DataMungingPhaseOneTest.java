package edu.xmu.test.codekata.datamunging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataMungingPhaseOneTest {
	DataMungingPhaseOne instance;

	// FastDateFormat formatter = FastDateFormat.getInstance("dd-MMM-yy");

	@Before
	public void setUp() throws ParseException {
		instance = new DataMungingPhaseOne();
		List<Map<String, Comparable<?>>> lines = Lists.newArrayList();

		Map<String, Comparable<?>> map = Maps.newHashMap();
		map.put("company", "LifeLock");
		map.put("numEmps", 10);
		map.put("category", "web");
		map.put("city", "Tempe");
		map.put("state", "AZ");
		map.put("fundedDate", new SimpleDateFormat("dd-MMM-yy").parse("1-May-07"));
		map.put("raisedAmt", 6850000);
		lines.add(map);

		map = Maps.newHashMap();
		map.put("company", "MyCityFaces");
		map.put("numEmps", 7);
		map.put("category", "web");
		map.put("city", "Scottsdale");
		map.put("state", "AZ");
		map.put("fundedDate", new SimpleDateFormat("dd-MMM-yy").parse("1-Jan-08"));
		map.put("raisedAmt", 50000);
		lines.add(map);

		instance.setLines(lines);
	}

	@Test
	public void getMaxTest() {
		System.out.println(instance.getMax("fundedDate"));
		System.out.println(instance.getMax("numEmps"));
		System.out.println(instance.getMax("raisedAmt"));
	}

	@Test
	public void getMinTest() {
		System.out.println(instance.getMin("fundedDate"));
		System.out.println(instance.getMin("numEmps"));
		System.out.println(instance.getMin("raisedAmt"));
	}

	@Test
	public void getSumTest() {
		System.out.println(instance.getSum("numEmps"));
		System.out.println(instance.getSum("raisedAmt"));
	}

	@Test
	public void getAvgTest() {
		System.out.println(instance.getAvg("numEmps"));
		System.out.println(instance.getAvg("raisedAmt"));
	}

	@Test
	public void getTest() {
		System.out.println(instance.getExact("state", "AZ"));
		System.out.println(instance.getExact("numEmps", Integer.valueOf(10)));
	}
}
