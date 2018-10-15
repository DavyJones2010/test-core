package edu.xmu.test.spring.batch;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.oxm.xstream.XStreamMarshaller;

import com.google.common.collect.Lists;

import edu.xmu.test.spring.batch.model.Trade;

public class StaxEventItemWriterTest {
	@Test
	public void marshallerTest() throws Exception {
		//StaxEventItemWriter<Trade> writer = new StaxEventItemWriter<Trade>();
		//Resource resource = new FileSystemResource("src/main/resources/batch/xml/trade-generated.xml");
        //
		//Map<String, String> aliases = new HashMap<>();
		//aliases.put("trade", "edu.xmu.test.batch.model.Trade");
		//// aliases.put("price", "java.math.BigDecimal");
		//// aliases.put("customer", "java.lang.String");
        //
		//XStreamMarshaller marshaller = new XStreamMarshaller();
		//marshaller.setAliases(aliases);
		//writer.setMarshaller(marshaller);
		//writer.setResource(resource);
		//writer.setRootTagName("trades");
		//writer.setOverwriteOutput(true);
        //
		//List<Trade> trades = Lists.newArrayList(new Trade("C00001", 1, new BigDecimal("12.2"), "Yang"), new Trade(
		//		"C00002", 2, new BigDecimal("13.221"), "Davy"));
		//writer.open(new ExecutionContext());
		//writer.write(trades);
		//writer.close();
	}
}
