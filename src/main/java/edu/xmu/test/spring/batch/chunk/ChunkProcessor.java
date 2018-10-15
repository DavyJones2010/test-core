package edu.xmu.test.spring.batch.chunk;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import edu.xmu.test.spring.batch.model.Trade;

public class ChunkProcessor implements ItemProcessor<Trade, Trade> {
	private static final Logger logger = Logger.getLogger(ChunkProcessor.class);

	@Override
	public Trade process(Trade item) throws Exception {
		logger.info("process: " + item);
		return item;
	}
}
