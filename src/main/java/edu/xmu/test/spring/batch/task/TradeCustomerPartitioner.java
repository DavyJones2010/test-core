package edu.xmu.test.spring.batch.task;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.batch.core.partition.support.Partitioner;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.JdbcTemplate;

import com.google.common.collect.Maps;

public class TradeCustomerPartitioner implements Partitioner {
	private final JdbcOperations jdbcTemplate;

	public TradeCustomerPartitioner(DataSource dataSource) {
		super();
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Map<String, ExecutionContext> partition(int gridSize) {
		//List<String> customers = jdbcTemplate.queryForList("SELECT DISTINCT CUSTOMER_NAME FROM TRADE", String.class);

		Map<String, ExecutionContext> results = Maps.newLinkedHashMap();
		//for (String customer : customers) {
		//	ExecutionContext ec = new ExecutionContext();
		//	ec.put("customer", customer);
		//	results.put("partition." + customer, ec);
		//}
		return results;
	}
}
