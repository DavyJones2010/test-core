package edu.xmu.test.spring.batch.chunk;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemWriter;

import edu.xmu.test.spring.batch.model.Trade;

public class ChunkWriter implements ItemWriter<Trade> {
	private static final Logger logger = Logger.getLogger(ChunkWriter.class);

	@Override
	public void write(List<? extends Trade> items) throws Exception {
		logger.info("Written " + items);
	}

}
