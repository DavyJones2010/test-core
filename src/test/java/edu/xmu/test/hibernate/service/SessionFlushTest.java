package edu.xmu.test.hibernate.service;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import edu.xmu.test.hibernate.model.User;
import edu.xmu.test.hibernate.model.UserWithNativeID;

public class SessionFlushTest {
	PersonService service;

	@Before
	public void setUp() {
		service = new PersonService();
	}

	@Test
	public void uuidSaveFlushTest() {
		User u = new User();
		u.setName("Yang, Kunlun");
		u.setPassword("Hallo");
		u.setDateCreated(Calendar.getInstance().getTime());
		u.setDateExpired(Calendar.getInstance().getTime());
		// Using UUID as id generator, session will be flushed when executing
		// commit().
		service.save(u);
	}

	@Test
	public void nativeIdSaveFlushTest() {
		UserWithNativeID u = new UserWithNativeID();
		u.setName("Yang, Kunlun");
		u.setPassword("Hallo");
		u.setDateCreated(Calendar.getInstance().getTime());
		u.setDateExpired(Calendar.getInstance().getTime());
		// Using native as id generator, session will be flushed when executing
		// session.save().
		service.save(u);
	}
}
