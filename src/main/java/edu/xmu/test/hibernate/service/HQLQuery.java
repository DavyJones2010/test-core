package edu.xmu.test.hibernate.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import com.google.common.base.Function;
import com.google.common.base.Preconditions;

import edu.xmu.test.hibernate.model.User;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class HQLQuery {
	private static final Logger logger = Logger.getLogger(HQLQuery.class);

	@SuppressWarnings("unchecked")
	public void simpleQuery() {
		sessionOps((session) -> {
			// "from" is case insensitivity
			// "User" is case sensitivity, it has to be the entity's class name
			Query query = session.createQuery("from User");
			List<User> users = query.list();
			logger.info(users);
			return null;
		});
	}

	@SuppressWarnings("unchecked")
	public void pagingQuery() {
		sessionOps((session) -> {
			// "from" is case insensitivity
			// "User" is case sensitivity, it has to be the entity's class name
			Query query = session.createQuery("from User");
			// the generated sql will append "limit ? ?" at then end
			// the first "?" means the start row number, by default it would be
			// "0"
			// the second "?" means the length
			query.setFirstResult(10);
			query.setMaxResults(5);
			List<User> users = query.list();
			Preconditions.checkArgument(5 >= users.size());
			return null;
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
