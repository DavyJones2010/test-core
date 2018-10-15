package edu.xmu.test.guava.collect;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.common.base.Function;

public class DateFormatFunction implements Function<Date, String> {
	private final static SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/YYYY");

	public String apply(Date input) {
		return dateFormat.format(input);
	}
}
