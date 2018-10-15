package edu.xmu.test.codekata.datamunging;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class DataMungingPhaseTwoTest {
	DataMungingPhaseTwo instance;

	// FastDateFormat formatter = FastDateFormat.getInstance("dd-MMM-yy");

	@Before
	public void setUp() throws ParseException {
		instance = new DataMungingPhaseTwo();
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
	public void getMaxEntriesTest() {
		System.out.println(instance.getMaxEntries("fundedDate"));
		System.out.println(instance.getMaxEntries("numEmps"));
		System.out.println(instance.getMaxEntries("raisedAmt"));
	}

	@Test
	public void getMaxChainedTest() {
		System.out.println(new DataMungingPhaseTwo(instance.getMaxEntries("fundedDate")).getMinEntries("raisedAmt"));
		System.out.println(new DataMungingPhaseTwo(instance.getMaxEntries("numEmps")).getMinEntries("raisedAmt"));
		System.out.println(new DataMungingPhaseTwo(instance.getMaxEntries("raisedAmt")).getMinEntries("numEmps"));
	}

	@Test
	public void getMinTest() {
		System.out.println(instance.getMin("fundedDate"));
		System.out.println(instance.getMin("numEmps"));
		System.out.println(instance.getMin("raisedAmt"));
	}

	@Test
	public void getMinEntriesTest() {
		System.out.println(instance.getMinEntries("fundedDate"));
		System.out.println(instance.getMinEntries("numEmps"));
		System.out.println(instance.getMinEntries("raisedAmt"));
	}

	@Test
	public void getMinChainedTest() {
		System.out.println(new DataMungingPhaseTwo(instance.getMinEntries("fundedDate")).getMaxEntries("raisedAmt"));
		System.out.println(new DataMungingPhaseTwo(instance.getMinEntries("numEmps")).getMaxEntries("raisedAmt"));
		System.out.println(new DataMungingPhaseTwo(instance.getMinEntries("raisedAmt")).getMaxEntries("numEmps"));
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
	public void limitTest() {
		System.out.println(instance.limit(1));
		System.out.println(instance.limit(10));
		System.out.println(new DataMungingPhaseTwo(new DataMungingPhaseTwo(instance.getMinEntries("fundedDate")).getMaxEntries("raisedAmt")).limit(1));
	}
}
