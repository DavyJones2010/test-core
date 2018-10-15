package edu.xmu.test.hibernate.util;

import java.util.Optional;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import com.google.common.base.Function;

public class SessionFactoryUtil {
	private static final Logger logger = Logger.getLogger(SessionFactoryUtil.class);
	private static final SessionFactory factory;
	static {
		Configuration config = new Configuration().configure("hibernate/hibernate.cfg.xml");
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder().applySettings(config.getProperties());
		factory = config.buildSessionFactory(ssrb.build());
	}

	public static SessionFactory getSessionFactory() {
		return factory;
	}

	public static Session getSession() {
		return factory.openSession();
	}

	public static <T> Optional<T> executeInTransaction(Function<Session, T> func) {
		Session session = SessionFactoryUtil.getSession();
		Transaction tx = null;
		T object = null;

		try {
			tx = session.beginTransaction();
			object = func.apply(session);
			tx.commit();
		} catch (Exception e) {
			logger.error("", e);
			tx.rollback();
		}
		return Optional.ofNullable(object);
	}

	public static void closeSession(Session session) {
		if (null != session && session.isOpen()) {
			session.close();
		}
	}
}
