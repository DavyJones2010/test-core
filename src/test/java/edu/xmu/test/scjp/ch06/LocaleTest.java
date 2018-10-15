package edu.xmu.test.scjp.ch06;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.junit.Test;

public class LocaleTest {
	@Test
	public void localeTest() {
		System.out.println(Locale.getDefault().getCountry());
		System.out.println(TimeZone.getDefault().getDisplayName());
		System.out.println(Calendar.getInstance(TimeZone.getTimeZone("CST"),
				Locale.ITALIAN).getTime());
	}
	@Test
	public void addTest() {
		Calendar c = Calendar.getInstance();
		System.out.println(c.getTime());
		c.roll(Calendar.MONTH, 10);
		System.out.println(c.getTime());
		c.add(Calendar.MONTH, 3);
		System.out.println(c.getTime());
	}
	@Test
	public void dateFormatTest() throws ParseException {
		Date d = Calendar.getInstance().getTime();
		DateFormat formatter = DateFormat.getDateInstance(DateFormat.MEDIUM,
				Locale.CHINESE);
		System.out.println(formatter.format(d));

		// d = formatter.parse("Dec 29, 2014");
		// System.out.println(d);
	}
	@Test
    public void localSample(){
        System.out.println(Locale.CHINA);
        System.out.println(Locale.TAIWAN);
        System.out.println(Locale.ENGLISH);
        System.out.println(Locale.US);
        System.out.println(Locale.CANADA);
    }
}
