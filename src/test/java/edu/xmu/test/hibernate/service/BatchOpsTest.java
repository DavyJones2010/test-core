package edu.xmu.test.hibernate.service;

import java.util.Calendar;
import java.util.List;

import org.hibernate.CacheMode;
import org.hibernate.ScrollMode;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Lists;

import edu.xmu.test.hibernate.model.UserWithNativeID;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class BatchOpsTest {
	private static final int BATCH_SIZE = 1000;

	@Test
	public void batchInsertTest() {
		final List<UserWithNativeID> users = getUsers(10000);
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				int c = 0;
				for (UserWithNativeID user : users) {
					session.save(user);
					if (0 == ++c % BATCH_SIZE) {
						session.flush();
						session.clear();
					}
				}
				return null;
			}
		});
	}

	@Test
	public void batchUpdateTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				ScrollableResults users = session.createQuery("from UserWithNativeID").setCacheMode(CacheMode.IGNORE).scroll(ScrollMode.FORWARD_ONLY);
				int count = 0;
				while (users.next()) {
					UserWithNativeID user = (UserWithNativeID) users.get(0);
					user.setName("NAME_" + Calendar.getInstance().getTimeInMillis());
					user.setPassword("PWD_" + Calendar.getInstance().getTimeInMillis());
					user.setDateCreated(Calendar.getInstance().getTime());
					user.setDateExpired(Calendar.getInstance().getTime());
					if (0 == ++count % BATCH_SIZE) {
						session.flush();
						// Transform updated user to detached state
						session.clear();
					}
				}

				return null;
			}
		});
	}

	/**
	 * @see <a href="https://docs.jboss.org/hibernate/orm/3.3/reference/en-US/html/batch.html#batch-statelesssession">stateless session</a>
	 */
	@Test
	public void batchUpdateStatelessSessionTest() {
		StatelessSession session = SessionFactoryUtil.getSessionFactory().openStatelessSession();
		Transaction tx = session.beginTransaction();
		// all users loaded are in detached state
		ScrollableResults users = session.createQuery("from UserWithNativeID").scroll(ScrollMode.FORWARD_ONLY);
		while (users.next()) {
			UserWithNativeID user = (UserWithNativeID) users.get(0);
			user.setName("NAME_" + Calendar.getInstance().getTimeInMillis());
			user.setPassword("PWD_" + Calendar.getInstance().getTimeInMillis());
			user.setDateCreated(Calendar.getInstance().getTime());
			user.setDateExpired(Calendar.getInstance().getTime());

			session.update(user);
		}
		tx.commit();
		session.close();
	}

	List<UserWithNativeID> getUsers(int count) {
		List<UserWithNativeID> users = Lists.newArrayList();
		for (int i = 0; i < count; i++) {
			UserWithNativeID user = new UserWithNativeID();
			user.setName("NAME_" + Calendar.getInstance().getTimeInMillis());
			user.setPassword("PWD_" + Calendar.getInstance().getTimeInMillis());
			user.setDateCreated(Calendar.getInstance().getTime());
			user.setDateExpired(Calendar.getInstance().getTime());
			users.add(user);
		}
		return users;
	}
}
