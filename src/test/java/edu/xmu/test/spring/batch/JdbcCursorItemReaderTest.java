package edu.xmu.test.spring.batch;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemStreamException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jdbc.core.RowMapper;

import edu.xmu.test.spring.batch.model.Trade;

public class JdbcCursorItemReaderTest {
	private static final Logger logger = Logger.getLogger(JdbcCursorItemReaderTest.class);

	//@Test
	//public void readTest() throws UnexpectedInputException, ParseException, ItemStreamException, Exception {
	//	String[] springConfig = { "classpath:batch/context.xml" };
	//	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
	//	DataSource dataSource = (DataSource) context.getBean("dataSource");
	//	JdbcCursorItemReader<Trade> reader = new JdbcCursorItemReader<>();
	//	reader.setDataSource(dataSource);
	//	reader.setSql("SELECT ISIN, QUANTITY, PRICE, CUSTOMER_NAME FROM TRADE");
	//	// verifyCursorPosition has to be set as false for embeded derby db
	//	reader.setVerifyCursorPosition(false);
	//	reader.setRowMapper(new RowMapper<Trade>() {
	//		@Override
	//		public Trade mapRow(ResultSet rs, int rowNum) throws SQLException {
	//			return new Trade(rs.getString(1), rs.getInt(2), rs.getBigDecimal(3), rs.getString(4));
	//		}
	//	});
    //
	//	ExecutionContext executionContext = new ExecutionContext();
	//	reader.open(executionContext);
	//	Trade t = null;
	//	while (null != (t = reader.read())) {
	//		logger.info(t);
	//	}
	//	reader.close();
	//	context.close();
	//}
}
