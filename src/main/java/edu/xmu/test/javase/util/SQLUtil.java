package edu.xmu.test.javase.util;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;

public class SQLUtil {
	private final static Logger logger = Logger.getLogger(SQLUtil.class);

	private SQLUtil() {
	}

	public static String replaceForInStatement(String originalSQL, String placeholder, String param) {
		logger.info(String.format("Start replaceForIn. originalSQL: [%s], placeholder: [%s], param: [%s]", originalSQL, placeholder, param));

		List<String> paramList = new ArrayList<String>();
		paramList.add(param);

		String replacedSQL = replaceForInStatement(originalSQL, placeholder, paramList);

		logger.info(String.format("Finished replaceForIn. replacedSQL: [%s]", replacedSQL));

		return replacedSQL;
	}

	/**
	 * Replace placeholder in originalSQL which contains keyword: 'IN' with
	 * params<br/>
	 * <br/>
	 * If input paramList is null/empty, then no changes will be made <br/>
	 * 
	 * <pre>
	 * Eg. Transform from: "SELECT * FROM TABLE WHERE SCENARIO IN (${SCENARIO})"
	 *     to "SELECT * FROM TABLE WHERE SCENARIO IN ('BHC Base', 'FRB Base')"
	 * </pre>
	 * 
	 * @param originalSQL
	 * @param placeholder
	 * @param paramList
	 * @return
	 */
	public static String replaceForInStatement(String originalSQL, String placeholder, List<String> paramList) {
		logger.info(String.format("Start replaceForIn. originalSQL: [%s], placeholder: [%s], paramList: [%s]", originalSQL, placeholder, paramList));

		if (null == paramList || 0 == paramList.size()) {
			logger.warn(String.format("paramList is empty, thus originalSQL: [%s] will be returned", originalSQL));
			return originalSQL;
		}

		String replacedSQL = StringUtils.replace(originalSQL, placeholder, Joiner.on(',').join(Lists.transform(paramList, a -> "'".concat(a).concat("'"))));

		logger.info(String.format("Finished replaceForIn. replacedSQL: [%s]", replacedSQL));
		return replacedSQL;
	}
}
