package edu.xmu.test.javase.logger;

import org.apache.log4j.Logger;

public class LoggerSample {
	static Logger logger = Logger.getLogger(LoggerSample.class);

	public static void main(String[] args) {
		logger.info("Hello world");
	}
}
