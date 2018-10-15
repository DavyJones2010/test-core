package edu.xmu.test.spring.batch.chunk;

import java.math.BigDecimal;
import java.util.Random;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;

import edu.xmu.test.spring.batch.model.Trade;

public class ChunkReader implements ItemReader<Trade> {
	private static final Random seed = new Random();

	@Override
	public Trade read() throws Exception, UnexpectedInputException, ParseException, NonTransientResourceException {
		return generateRandomTrade();
	}

	Trade generateRandomTrade() {
		if (seed.nextInt(20) == 0) {
			return null;
		} else {
			return new Trade("C" + seed.nextInt(100), seed.nextInt(100), BigDecimal.valueOf(100 * seed.nextDouble()), "Customer_" + seed.nextInt(100));
		}
	}
}
