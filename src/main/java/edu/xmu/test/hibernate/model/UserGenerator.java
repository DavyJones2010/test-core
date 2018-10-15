package edu.xmu.test.hibernate.model;

import java.util.Date;
import java.util.Random;

import org.apache.commons.lang3.time.DateUtils;

public class UserGenerator {
	private static final Random seed = new Random();

	public static User getUser() {
		User user = new User();
		int no = seed.nextInt(1000);
		user.setName("User_" + no);
		user.setPassword("Pwd_" + no);
		user.setDateCreated(new Date());
		user.setDateExpired(DateUtils.addDays(new Date(), 10));
		return user;
	}
}
