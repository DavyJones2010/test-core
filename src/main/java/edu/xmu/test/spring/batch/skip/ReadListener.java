package edu.xmu.test.spring.batch.skip;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ItemReadListener;
import org.springframework.batch.core.ItemWriteListener;

import edu.xmu.test.spring.batch.model.Trade;

public class ReadListener implements ItemReadListener<Trade>, ItemWriteListener<Trade> {
	private static final Logger logger = Logger.getLogger(ReadListener.class);

	@Override
	public void beforeRead() {
		logger.info("beforeRead");
	}

	@Override
	public void afterRead(Trade item) {
		logger.info("afterRead: " + item);
	}

	@Override
	public void onReadError(Exception ex) {
		logger.info("onReadError");
	}

	@Override
	public void beforeWrite(List<? extends Trade> items) {
		logger.info("beforeWrite: " + items);
	}

	@Override
	public void afterWrite(List<? extends Trade> items) {
		logger.info("afterWrite: " + items);
	}

	@Override
	public void onWriteError(Exception exception, List<? extends Trade> items) {
		logger.info("onWriteError: " + items);
	}

}
