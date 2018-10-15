package edu.xmu.test.hibernate.service;

import java.util.Calendar;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import edu.xmu.test.hibernate.model.UserWithNativeID;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class InterceptorTest {

	@Test
	public void sessionScopeInterceptorSaveTest() {
		Session session = SessionFactoryUtil.getSessionFactory().withOptions()
				.interceptor(new UserWithNativeIDInterceptor()).openSession();
		Transaction tx = session.beginTransaction();
		UserWithNativeID bean = new UserWithNativeID();
		bean.setName("Hallo");
		bean.setPassword("World");
		bean.setDateCreated(Calendar.getInstance().getTime());
		bean.setDateExpired(Calendar.getInstance().getTime());
		session.save(bean);

		tx.commit();
	}

	@Test
	public void sessionScopeInterceptorLoadTest() {
		Session session = SessionFactoryUtil.getSessionFactory().withOptions()
				.interceptor(new UserWithNativeIDInterceptor()).openSession();
		Transaction tx = session.beginTransaction();
		UserWithNativeID user = (UserWithNativeID) session.load(UserWithNativeID.class, 1);
		System.out.println(user);
		tx.commit();
	}

	@Test
	public void sessionScopeInterceptorDeleteTest() {
		Session session = SessionFactoryUtil.getSessionFactory().withOptions()
				.interceptor(new UserWithNativeIDInterceptor()).openSession();
		Transaction tx = session.beginTransaction();
		UserWithNativeID user = (UserWithNativeID) session.load(UserWithNativeID.class, 1);
		System.out.println(user);
		session.delete(user);
		tx.commit();
	}
}
