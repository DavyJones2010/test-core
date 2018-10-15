package edu.xmu.test.hibernate.service;

import java.util.Calendar;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.model.OptimisticLockTimeStampBean;
import edu.xmu.test.hibernate.model.OptimisticLockVersionBean;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class OptimisticLockTest {
	private static final Logger logger = Logger.getLogger(OptimisticLockTest.class);

	@Before
	public void setUp() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				if (session.createQuery("from OptimisticLockVersionBean").list().isEmpty()) {
					OptimisticLockVersionBean bean = new OptimisticLockVersionBean();
					bean.setName("A");
					session.save(bean);
				}
				if (session.createQuery("from OptimisticLockTimeStampBean").list().isEmpty()) {
					OptimisticLockTimeStampBean bean = new OptimisticLockTimeStampBean();
					bean.setName("A");
					session.save(bean);
				}
				return null;
			}
		});
	}

	/**
	 * The first thread that committed the change will update the data & version and over run other threads <br/>
	 * Other threads will throw StaleObjectStateException and roll back their own changes <br/>
	 */
	@Test
	public void optimisticLockVersionApproachTest() throws InterruptedException {
		Thread a = createUpdateThreadVersionImpl("A");
		Thread b = createUpdateThreadVersionImpl("B");
		Thread c = createUpdateThreadVersionImpl("C");
		Thread d = createUpdateThreadVersionImpl("D");

		a.start();
		b.start();
		c.start();
		d.start();

		Thread.sleep(12000L);
	}

	/**
	 * The first thread that committed the change will update the data & version and over run other threads <br/>
	 * Other threads will throw StaleObjectStateException and roll back their own changes <br/>
	 */
	@Test
	public void optimisticLockTimeStampApproachTest() throws InterruptedException {
		Thread a = createUpdateThreadTimeStampImpl("A");
		Thread b = createUpdateThreadTimeStampImpl("B");
		Thread c = createUpdateThreadTimeStampImpl("C");
		Thread d = createUpdateThreadTimeStampImpl("D");

		a.start();
		b.start();
		c.start();
		d.start();

		Thread.sleep(12000L);
	}

	Thread createUpdateThreadVersionImpl(String threadName) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
					@Override
					public Void apply(Session session) {
						OptimisticLockVersionBean bean = (OptimisticLockVersionBean) session.get(OptimisticLockVersionBean.class, 1);
						logger.info(Thread.currentThread() + " got bean");
						try {
							Thread.sleep((long) (10000 * Math.random()));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						bean.setName(threadName + "_" + Calendar.getInstance().getTimeInMillis());
						session.update(bean);
						session.flush();
						logger.info(Thread.currentThread() + " updated bean");
						return null;
					}
				});
			}
		}, threadName);
	}

	Thread createUpdateThreadTimeStampImpl(String threadName) {
		return new Thread(new Runnable() {
			@Override
			public void run() {
				SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
					@Override
					public Void apply(Session session) {
						OptimisticLockTimeStampBean bean = (OptimisticLockTimeStampBean) session.get(OptimisticLockTimeStampBean.class, 1);
						logger.info(Thread.currentThread() + " got bean");
						try {
							Thread.sleep((long) (10000 * Math.random()));
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						bean.setName(threadName + "_" + Calendar.getInstance().getTimeInMillis());
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
