package edu.xmu.test.hibernate.service;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;
import com.google.common.collect.Maps;

import edu.xmu.test.hibernate.model.User;
import edu.xmu.test.hibernate.model.UserGenerator;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class BeanLifeCycleManagement {
	private static final Logger logger = Logger.getLogger(BeanLifeCycleManagement.class);

	public void objectStateTransition() {
		Session session = SessionFactoryUtil.getSession();
		User user = null;
		try {
			session.beginTransaction();
			// transient state
			user = UserGenerator.getUser();

			// persistent state
			session.save(user);
			user.setName("Yang, Kunlun");
			user.setPassword("My PWD");

			// detached state
			// session.evict(user);
			// user.setName("Davy, Jones");

			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			session.getTransaction().rollback();
		} finally {
			SessionFactoryUtil.closeSession(session);
		}
		// detached state
		user.setName("Davy, Jones");

		try {
			session = SessionFactoryUtil.getSession();
			session.beginTransaction();

			session.saveOrUpdate(user);
			user.setPassword("Davy's pwd");

			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			session.getTransaction().rollback();
		} finally {
			SessionFactoryUtil.closeSession(session);
		}
	}

	public void getUser() {
		sessionOps(new Function<Session, User>() {
			@Override
			public User apply(Session input) {
				User user = (User) input.get("edu.xmu.test.hibernate.model.User", "4028dbf64c5ba02d014c5ba02eca0000");
				logger.info(user);
				// user is still persistant state
				user.setPassword("Davy's new pwd");
				return user;
			}
		});
		sessionOps(new Function<Session, User>() {
			@Override
			public User apply(Session input) {
				// return null if user doesn't exists
				User user = (User) input.get("edu.xmu.test.hibernate.model.User", "dummyidthatdoesnotexists");
				Preconditions.checkArgument(null == user);
				return user;
			}
		});
		sessionOps(new Function<Session, User>() {
			@Override
			public User apply(Session input) {
				User user = (User) input.load("edu.xmu.test.hibernate.model.User", "4028dbf64c5ba02d014c5ba02eca0000");
				Map<String, User> map = Maps.newHashMap();
				map.put("user", user);
				user = map.get("user");
				// Only util now, the query is executed
				logger.info(user);
				// user is still persistant state
				user.setPassword("banana1");
				return user;
			}
		});
		sessionOps(new Function<Session, User>() {
			@Override
			public User apply(Session input) {
				// return a proxy user no matter user exists or not
				User user = (User) input.load("edu.xmu.test.hibernate.model.User", "dummyidthatdoesnotexists");
				Preconditions.checkArgument(null != user);
				// ObjectNotFoundException will be thrown
				logger.info(user.getName());
				return user;
			}
		});
	}

	public void updateUser() {
		sessionOps(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {

				User user = new User();
				// user is not transient state, because it has id which has
				// already exists in db
				// it is a detached object that we manually created
				user.setId("4028dbf64c5ba02d014c5ba02eca0000");
				user.setName("AnotherName");
				user.setDateCreated(new Date());
				user.setDateExpired(DateUtils.addDays(new Date(), 30));
				// update successfully
				session.update(user);
				// now user has become persistent object
				user.setPassword("AnotherPWD");
				return null;
			}
		});
		sessionOps(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				// now user has become persistent object
				User user = (User) session
						.load("edu.xmu.test.hibernate.model.User", "4028dbf64c5ba02d014c5ba02eca0000");
				user.setName("AnotherName");
				user.setDateCreated(new Date());
				user.setDateExpired(DateUtils.addDays(new Date(), 30));
				user.setPassword("AnotherPWD");
				// Once tx.commit, user will be update successfully
				return null;
			}
		});
	}

	public void delUser() {
		sessionOps(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				// now user has become persistent object
				User user = (User) session
						.load("edu.xmu.test.hibernate.model.User", "4028dbf64c5ba02d014c5ba02eca0000");
				// entry will be deleted from db, and user will become transient
				// object
				session.delete(user);
				return null;
			}
		});
	}

	public void sessionOps(Function<Session, ?> func) {
		Session session = SessionFactoryUtil.getSession();
		try {
			session.beginTransaction();
			func.apply(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			session.getTransaction().rollback();
		} finally {
			SessionFactoryUtil.closeSession(session);
		}
	}

}
