package edu.xmu.test.spring.batch;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.oxm.xstream.XStreamMarshaller;

import edu.xmu.test.spring.batch.model.Trade;

public class StaxEventItemReaderTest {
	//@Test
	//public void unmarshallerTest() throws UnexpectedInputException, ParseException, Exception {
	//	StaxEventItemReader<Trade> reader = new StaxEventItemReader<>();
	//	Resource rs = new FileSystemResource("src/main/resources/batch/xml/trade.xml");
    //
	//	Map<String, String> aliases = new HashMap<>();
	//	aliases.put("trade", "edu.xmu.test.batch.model.Trade");
	//	// aliases.put("price", "java.math.BigDecimal");
	//	// aliases.put("customer", "java.lang.String");
    //
	//	XStreamMarshaller unmaMarshaller = new XStreamMarshaller();
	//	unmaMarshaller.setAliases(aliases);
    //
	//	reader.setResource(rs);
	//	reader.setUnmarshaller(unmaMarshaller);
	//	reader.setFragmentRootElementName("trade");
	//	reader.open(new ExecutionContext());
	//	Trade t = null;
	//	while ((t = reader.read()) != null) {
	//		System.out.println(t);
	//	}
	//}
}
