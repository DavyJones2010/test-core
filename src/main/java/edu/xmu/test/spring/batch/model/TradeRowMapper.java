package edu.xmu.test.spring.batch.model;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class TradeRowMapper implements RowMapper<Trade> {
	@Override
	public Trade mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Trade(rs.getString(1), rs.getInt(2), rs.getBigDecimal(3), rs.getString(4));
	}
}
