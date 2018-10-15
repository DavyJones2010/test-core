package edu.xmu.test.hibernate.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.model.UserWithNativeID;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class PessimisticLockTest {
	private static final Logger logger = Logger.getLogger(PessimisticLockTest.class);

	@Before
	public void setUp() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				if (session.createQuery("from UserWithNativeID").list().isEmpty()) {
					UserWithNativeID bean = new UserWithNativeID();
					bean.setName("NAME_" + Calendar.getInstance().getTimeInMillis());
					bean.setPassword("PWD_" + Calendar.getInstance().getTimeInMillis());
					session.save(bean);
				}
				return null;
			}
		});
	}

	/**
	 * We're using pessimistic lock which is actually using the exclusive lock provided by db <br/>
	 * SQL is translated to: "select ... from ... for update" <br/>
	 * No other connection/session could access the data/row unless the current lock holder(connection) release the lock
	 */
	@Test
	public void pessimisticLockTest() throws InterruptedException {
		Thread a = createUpdateThreadImpl("A");
		Thread b = createUpdateThreadImpl("B");
		Thread c = createUpdateThreadImpl("C");
		Thread d = createUpdateThreadImpl("D");

		a.start();
		b.start();
		c.start();
		d.start();

		a.join();
		b.join();
		c.join();
		d.join();
	}

	Thread createUpdateThreadImpl(String threadName) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
					@Override
					public Void apply(Session session) {
						UserWithNativeID bean = (UserWithNativeID) session.get(UserWithNativeID.class, 1, LockOptions.UPGRADE);
						logger.info(Thread.currentThread() + " got bean");
						try {
							Thread.sleep((long) (10000 * Math.random()));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						bean.setName(threadName + "_" + Calendar.getInstance().getTimeInMillis());
						bean.setPassword("PWD_" + Calendar.getInstance().getTimeInMillis());
						session.update(bean);
						session.flush();
						logger.info(Thread.currentThread() + " updated bean");
						return null;
					}
				});
			}
		}, threadName);
	}
}
