package edu.xmu.test.joda;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.junit.Test;

public class DateFormatTest {

	@Test
	public void formatTest() throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		System.out.println(dateFormat.format(Calendar.getInstance().getTime()));
		System.out.println(dateFormat.parse("20151111"));
	}
	@Test
	public void formatTest2(){
		FastDateFormat fdf = FastDateFormat.getDateInstance(FastDateFormat.FULL);
		Date date = new Date(System.currentTimeMillis());
		System.out.println(fdf.format(System.currentTimeMillis()));
		date = DateUtils.addDays(date, -1);
		date = DateUtils.addDays(date, -1);
		System.out.println(date.toString());
		System.out.println(date.before(new Date(System.currentTimeMillis())));
	}
}
