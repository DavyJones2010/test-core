package edu.xmu.test.hibernate.service;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import edu.xmu.test.hibernate.model.UserGenerator;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class Client {
	private static final Logger logger = Logger.getLogger(Client.class);
	private static final int USER_COUNT = 100;

	public static void main(String[] args) {
		Session session = SessionFactoryUtil.getSession();
		try {
			session.beginTransaction();
			for (int i = 0; i < USER_COUNT; i++) {
				session.save(UserGenerator.getUser());
			}
			session.getTransaction().commit();
		} catch (Exception e) {
			logger.error("Exception: ", e);
			session.getTransaction().rollback();
		} finally {
			SessionFactoryUtil.closeSession(session);
		}
		System.exit(0);
	}
}
