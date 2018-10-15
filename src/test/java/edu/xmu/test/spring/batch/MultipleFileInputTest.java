package edu.xmu.test.spring.batch;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.batch.item.file.MultiResourceItemReader;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.oxm.xstream.XStreamMarshaller;

import edu.xmu.test.spring.batch.model.Trade;

public class MultipleFileInputTest {
	@Test
	public void readFileTest() throws UnexpectedInputException, ParseException, Exception {
		//MultiResourceItemReader<Trade> reader = new MultiResourceItemReader<>();
		//Resource rs = new FileSystemResource("src/main/resources/batch/xml/trade-generated.xml");
		//Resource rs2 = new FileSystemResource("src/main/resources/batch/xml/trade.xml");
		//reader.setResources(new Resource[] { rs, rs2 });
        //
		//StaxEventItemReader<Trade> tradeReader = new StaxEventItemReader<Trade>();
		//tradeReader.setFragmentRootElementName("trade");
		//Map<String, String> aliases = new HashMap<>();
		//aliases.put("trade", "edu.xmu.test.batch.model.Trade");
        //
		//XStreamMarshaller unmarshaller = new XStreamMarshaller();
		//unmarshaller.setAliases(aliases);
		//tradeReader.setUnmarshaller(unmarshaller);
        //
		//reader.setDelegate(tradeReader);
        //
		//Trade trade = null;
		//while (null != (trade = reader.read())) {
		//	System.out.println(trade);
		//}
	}
}
