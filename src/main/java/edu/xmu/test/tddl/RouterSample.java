package edu.xmu.test.tddl;

import java.util.List;
import java.util.Map;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class RouterSample {
	static Map<String, List<String>> template = Maps.newHashMap();
	static {
		template.put("idx_open_account_mobile", Lists.newArrayList("index_value", "domain_id", "", ""));
		template.put("idx_open_account_email", Lists.newArrayList("index_value", "domain_id", "", ""));
		template.put("idx_open_account_isv_account_id", Lists.newArrayList("index_value", "domain_id", "", ""));
		template.put("idx_open_account_login_id", Lists.newArrayList("index_value", "domain_id", "K_TTKD|022049.364", "499137"));
		template.put("idx_open_account_open_id", Lists.newArrayList("index_value", "domain_id", "022049.364", "499137"));
		template.put("open_account", Lists.newArrayList("id", "4398107020088"));
		template.put("open_account_link", Lists.newArrayList("open_account_id", "domain_id", "4398046883654", "20112314503969"));
		template.put("idx_open_account_link_outerid",
				Lists.newArrayList("outer_id", "outer_platform", "domain_id", "3702166785", "1", "20112314513193"));
		template.put("refresh_token",
				Lists.newArrayList("refresh_token", "OA-4193e8280be14f369c8c185f5c593837"));
	}

	public static void main(String[] args) {
		String tablePrefix = "open_account";
		List<String> list = template.get(tablePrefix);
		List<List<String>> partition = Lists.partition(list, list.size() / 2);

		String generateSQL = RouterSample.generateSQL("open_account", tablePrefix, partition.get(0), partition.get(1));
		System.out.println(generateSQL);
	}

	public static String generateSQL(String dbPrefix, String tablePrefix, List<String> columnNames,
			List<String> columnValues) {
		String dBName = generateDBName(dbPrefix, columnNames, columnValues);
		String tableName = generateTableName(tablePrefix, columnNames, columnValues);
		Map<Object, Object> newHashMap = Maps.newHashMap();
		for (int i = 0; i < columnNames.size(); i++) {
			String colName = columnNames.get(i);
			String colValue = columnValues.get(i);
			newHashMap.put(colName, "\'" + colValue + "\'");
		}
		String condition = Joiner.on("\n     and ").withKeyValueSeparator("=").join(newHashMap);
		return String.format(" select * from %s.%s\n where %s;", dBName, tableName, condition);
	}

	private static String generateTableName(String tablePrefix, List<String> columnNames, List<String> columnValues) {
		return tablePrefix + "_" + new RouterGeneratorImpl().generateTableIndex(columnValues);
	}

	private static String generateDBName(String dbPrefix, List<String> columnNames, List<String> columnValues) {
		return dbPrefix + "_" + new RouterGeneratorImpl().generateDBIndex(columnValues);
	}
}
