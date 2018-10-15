package edu.xmu.test.hibernate.service;

import static org.junit.Assert.assertTrue;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import edu.xmu.test.hibernate.model.User;

public class BeanLifeCycleManagementTest {
	private static final Logger logger = Logger.getLogger(BeanLifeCycleManagementTest.class);

	BeanLifeCycleManagement manager;

	@Before
	public void setUp() {
		manager = new BeanLifeCycleManagement();
	}

	@Test
	public void objectStateTransitionTest() throws InterruptedException {
		manager.objectStateTransition();
	}

	@Test
	public void getUserTest() {
		manager.getUser();
	}

	@Test
	public void updateUserTest() {
		manager.updateUser();
	}

	@Test
	public void delUserTest() {
		manager.delUser();
	}

	@SuppressWarnings({ "null" })
	@Test(expected = NullPointerException.class)
	public void nullCastTest() {
		Object u = null;
		logger.info((User) u);
		assertTrue(null == u);
		// Exception thrown
		logger.info(((User) u).getId());
	}
}
