package edu.xmu.test.guava.basic;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openjdk.jmh.util.FileUtils;

import com.google.common.base.Function;

public class FunctionTest {
	private Logger logger = Logger.getLogger(FunctionTest.class);

	@Test
	public void functionTest() {
		String dateStr = new Function<Date, String>() {
			private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

			public String apply(Date input) {
				return dateFormat.format(input);
			}
		}.apply(Calendar.getInstance().getTime());
		logger.info(dateStr);
	}

	public String formatDate(Date date) {
		return new SimpleDateFormat("dd/MM/YYYY").format(date);
	}

	@Test
	public void date() throws ParseException, IOException {
		long time = 1464146635253L;
		// long time = 1464156000000L;
		System.out.println(new Date(time));

		// Date date = new Date("20160525 14:00:00");
		// System.out.println(date);

		Date parse = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2016-05-25 14:00:00");
		System.out.println(parse.getTime());

//		long tmp = 1464156000000L;

		time = parse.getTime();
		System.out.println(new Date(time));
		Collection<String> readAllLines = FileUtils.readAllLines(new File("src/test/resources/updateIsvAccountId.txt"));
		int count = 0;
		for (String string : readAllLines) {
			String timestamp = string.substring(349, 362);
			long parseLong = Long.parseLong(timestamp);
			if (parseLong >= 1464156000000L) {
				System.out.println(string);
				count ++;
			}
		}
		System.out.println(count);
	}
}
