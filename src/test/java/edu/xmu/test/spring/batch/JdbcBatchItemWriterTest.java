package edu.xmu.test.spring.batch;

import java.math.BigDecimal;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.google.common.collect.Lists;

import edu.xmu.test.spring.batch.model.Trade;

public class JdbcBatchItemWriterTest {
	//@Test
	//public void writeTest() throws Exception {
	//	String[] springConfig = { "classpath:batch/context.xml" };
	//	ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(springConfig);
	//	DataSource dataSource = (DataSource) context.getBean("dataSource");
    //
	//	JdbcBatchItemWriter<Trade> writer = new JdbcBatchItemWriter<>();
	//	writer.setDataSource(dataSource);
	//	writer.setSql("INSERT INTO TRADE(ISIN, QUANTITY, PRICE, CUSTOMER_NAME) VALUES(:isin, :quantity, :price, :customer)");
	//	writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
	//	// Explicitly call afterPropertiesSet(). It is not needed if the writer
	//	// is injected by Spring
	//	writer.afterPropertiesSet();
	//	writer.write(Lists.newArrayList(new Trade("C00012", 1, BigDecimal.valueOf(1.22), "Yang, Kunlun"), new Trade("C00013", 1, BigDecimal.valueOf(1.22), "Davy")));
    //
	//	context.close();
	//}
}
