package edu.xmu.test.javax.lucene;

import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.Query;
import org.apache.lucene.util.Version;
import org.junit.Before;
import org.junit.Test;

public class SearcherUtilTest {
	private SearcherUtil searcherUtil = null;

	@Before
	public void setUp() {
		searcherUtil = new SearcherUtil();
	}

	private void testBuildIndex() {
		List<Student> studentList = new ArrayList<Student>();
		Student student = new Student("11", "Davy", "Jones", "Male aaa Female", 100);
		studentList.add(student);
		student = new Student("22", "Davy", "Jones", "Male bbb Female", 110);
		studentList.add(student);
		student = new Student("33", "Jones", "Davy", "Male Female", 120);
		studentList.add(student);
		student = new Student("44", "Calyp", "Jones", "Female aa bb Male", 130);
		studentList.add(student);
		student = new Student("55", "Pso", "Caly", "Female cc dd ee Male", 140);
		studentList.add(student);

		searcherUtil.buildIndex(studentList);
	}

	@Test
	public void testSearchByTerm() {
		testBuildIndex();
		searcherUtil.searchByTerm("gender", "Female");
	}

	@Test
	public void testSearchByTermRange() {
		testBuildIndex();
		searcherUtil.searchByTermRange("id", "1", "3", 100);
	}

	@Test
	public void testSearchByNumericRange() {
		testBuildIndex();
		searcherUtil.searchByNumericRange("score", 100, 120, 100);
	}

	@Test
	public void testSearchByPrefix() {
		testBuildIndex();
		searcherUtil.searchByPrefix("gender", "Ma", 100);
	}

	@Test
	public void testSearchByWildcard() {
		testBuildIndex();
		searcherUtil.searchByWildcard("gender", "M*", 100);
	}

	@Test
	public void testSearchByBoolean() {
		testBuildIndex();
		searcherUtil.searchByBooleanQuery(100);
	}

	@Test
	public void testSearchByPhrase() {
		testBuildIndex();
		searcherUtil.searchByPhrase("gender", "Male", "Female", 1, 100);
	}

	@Test
	public void testSearchByFuzzy() {
		testBuildIndex();
		searcherUtil.searchByFuzzy("gender", "Femall", 100);
		System.out.println("======================================");
		searcherUtil.searchByFuzzy("gender", "Bemale", 100);
		System.out.println("======================================");
		searcherUtil.searchByFuzzy("gender", "Femile", 100);
	}

	@Test
	public void testSearchByQueryParser() {
		testBuildIndex();

		QueryParser parser = new QueryParser(Version.LUCENE_35, "gender", new SimpleAnalyzer(Version.LUCENE_35));
		Query query = null;
		try {
			query = parser.parse("score: [100 TO 130]");
		} catch (ParseException e) {
			e.printStackTrace();
		}
		searcherUtil.searchByQueryParser(query, 100);
	}
}
