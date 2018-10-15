package edu.xmu.test.spring.batch.skip;

import org.apache.log4j.Logger;
import org.springframework.batch.core.listener.SkipListenerSupport;

import edu.xmu.test.spring.batch.model.Trade;

public class SkipListener extends SkipListenerSupport<Trade, Trade> {
	private static final Logger logger = Logger.getLogger(SkipListener.class);

	@Override
	public void onSkipInRead(Throwable t) {
		logger.info("Skipped item: " + t.toString());
	}
}
