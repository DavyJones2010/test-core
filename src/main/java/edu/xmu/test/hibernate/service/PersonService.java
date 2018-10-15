package edu.xmu.test.hibernate.service;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class PersonService {
	private static final Logger logger = Logger.getLogger(PersonService.class);

	public void save(final Object o) {
		sessionOps(new Function<Session, Void>() {
			@Override
			public Void apply(Session input) {
				input.save(o);
				return null;
			}
		});
	}

	public Object get(Class<?> classToLoad, Serializable id) {
		return sessionOps(new Function<Session, Object>() {
			@Override
			public Object apply(Session input) {
				return input.get(classToLoad, id);
			}
		});
	}

	public Object sessionOps(Function<Session, ?> func) {
		Object obj = null;
		Session session = SessionFactoryUtil.getSession();
		try {
			session.beginTransaction();
			obj = func.apply(session);
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			session.getTransaction().rollback();
		} finally {
			SessionFactoryUtil.closeSession(session);
		}
		return obj;
	}
}
