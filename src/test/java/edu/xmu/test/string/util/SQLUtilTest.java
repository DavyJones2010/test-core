package edu.xmu.test.string.util;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.xmu.test.javase.util.SQLUtil;

public class SQLUtilTest {
	@Test
	public void replaceForInStatementTest() {
		List<String> paramList = new ArrayList<String>();
		paramList.add("BHC Base");
		paramList.add("FRB Base");
		String replacedSQL = SQLUtil.replaceForInStatement("SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN (%<LEGAL_VEHICLE>)", "%<LEGAL_VEHICLE>", paramList);
		String expectedSQL = "SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN ('BHC Base','FRB Base')";
		assertEquals(expectedSQL, replacedSQL);

		paramList = new ArrayList<String>();
		paramList.add("BHC Base");
		replacedSQL = SQLUtil.replaceForInStatement("SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN (%<LEGAL_VEHICLE>)", "%<LEGAL_VEHICLE>", paramList);
		expectedSQL = "SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN ('BHC Base')";
		assertEquals(expectedSQL, replacedSQL);

		paramList = new ArrayList<String>();
		replacedSQL = SQLUtil.replaceForInStatement("SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN (%<LEGAL_VEHICLE>)", "%<LEGAL_VEHICLE>", paramList);
		expectedSQL = "SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN (%<LEGAL_VEHICLE>)";
		assertEquals(expectedSQL, replacedSQL);
	}

	@Test
	public void replaceForInStatementTestForString() {
		String param = "BHC Base";
		String replacedSQL = SQLUtil.replaceForInStatement("SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN (%<LEGAL_VEHICLE>)", "%<LEGAL_VEHICLE>", param);
		String expectedSQL = "SELECT * FROM DUMMY_TABLE WHERE LEGAL_VEHICLE IN ('BHC Base')";
		assertEquals(expectedSQL, replacedSQL);
	}
}
