package edu.xmu.test.codekata.datamunging;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.tuple.Pair;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import edu.xmu.test.codekata.datamunging.DataMungingInterface.GatherType;
import edu.xmu.test.codekata.datamunging.DataMungingInterface.Operator;

public class DataMungingPhaseThreeRefactorTest {
	List<Map<String, Comparable<?>>> lines;
	DataMungingInterface chain;

	@Before
	public void setUp() throws ParseException, IOException {
		lines = loadLines();
	}

	private List<Map<String, Comparable<?>>> loadLines() throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(new File("src/main/resources/code-contest/datamunging/TechCrunchcontinentalUSA.csv")));
		br.readLine();// skip header
		String line;
		List<String> headers = Lists.newArrayList("permalink", "company", "numEmps", "category", "city", "state", "fundedDate", "raisedAmt", "raisedCurrency", "round");
		List<Map<String, Comparable<?>>> out = Lists.newArrayList();
		while ((line = br.readLine()) != null) {
			List<String> vals = Splitter.on(',').splitToList(line);
			Map<String, Comparable<?>> map = Maps.toMap(headers, new Function<String, Comparable<?>>() {
				int c = 0;

				@Override
				public Comparable<?> apply(String input) {
					String val = vals.get(c);
					Comparable compVal = null;
					if (c == 2 || c == 7) {
						compVal = Double.parseDouble(val);
					} else if (c == 6) {
						try {
							compVal = new SimpleDateFormat("dd-MMM-yy").parse(val);
						} catch (ParseException e) {
							e.printStackTrace();
						}
					} else {
						compVal = val;
					}
					c++;
					return compVal;
				}
			});
			out.add(map);
		}
		br.close();
		return out;
	}

	@Test
	public void gatherTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).gather("state", GatherType.EQUALS, "CA").gather("city", GatherType.NOT_EQUALS, "Palo Alto").limit(5).end()
				.getLines());
	}

	@Test
	public void getMaxTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).max("fundedDate").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).max("numEmps").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).max("raisedAmt").end().getLines());
	}

	@Test
	public void getMaxChainedTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).max("fundedDate").min("raisedAmt").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).max("numEmps").min("raisedAmt").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).max("raisedAmt").min("numEmps").end().getLines());
	}

	@Test
	public void getMinTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).min("fundedDate").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).min("numEmps").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).min("raisedAmt").end().getLines());
	}

	@Test
	public void getMinChainedTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).min("fundedDate").max("raisedAmt").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).min("numEmps").max("raisedAmt").end().getLines());
		System.out.println(DataMungingStreamBasedImpl.start(lines).min("raisedAmt").max("numEmps").end().getLines());
	}

	@Test
	public void getSumTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).sum("numEmps"));
		System.out.println(DataMungingStreamBasedImpl.start(lines).sum("raisedAmt"));
	}

	@Test
	public void getAvgTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).avg("numEmps"));
		System.out.println(DataMungingStreamBasedImpl.start(lines).avg("raisedAmt"));
	}

	@Test
	public void getDistinctTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).distinct("state"));
	}

	@Test
	public void limitTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).limit(1).end().getLines());
	}

	@Test
	public void populateColumnTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).enrich(Pair.of("numEmps", "raisedAmt"), Operator.ADD).end().getLines().get(0));
	}

	@Test
	public void orderByTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines)
				.orderBy(Pair.of("category", true), Pair.of("state", true), Pair.of("city", false), Pair.of("raisedCurrency", true), Pair.of("round", true)).limit(10).end()
				.getLines());
	}

	@Test
	public void getAggregatedPopulateColumnTest() {
		System.out.println(DataMungingStreamBasedImpl.start(lines).enrich(Pair.of("numEmps", "raisedAmt"), Operator.ADD).max(Operator.ADD.getOps("numEmps", "raisedAmt")));
		System.out
				.println(DataMungingStreamBasedImpl.start(lines).enrich(Pair.of("numEmps", "raisedAmt"), Operator.SUBTRACT).max(Operator.SUBTRACT.getOps("numEmps", "raisedAmt")));
		System.out
				.println(DataMungingStreamBasedImpl.start(lines).enrich(Pair.of("numEmps", "raisedAmt"), Operator.MULTIPLY).max(Operator.MULTIPLY.getOps("numEmps", "raisedAmt")));
		System.out.println(DataMungingStreamBasedImpl.start(lines).enrich(Pair.of("numEmps", "raisedAmt"), Operator.DIVIDE).max(Operator.DIVIDE.getOps("numEmps", "raisedAmt")));
	}
}
