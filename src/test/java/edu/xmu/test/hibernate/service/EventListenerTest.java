package edu.xmu.test.hibernate.service;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.event.service.spi.EventListenerRegistry;
import org.hibernate.event.spi.EventType;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.model.UserWithNativeID;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class EventListenerTest {

	/**
	 * @see <a href="http://in.relation.to/Bloggers/EventListenerRegistration#H-IntegratorContract">Integrator</a>
	 */
	@Ignore
	@SuppressWarnings("unchecked")
	@Test
	public void registerEventTest() {
		// TODO: Find out how to register event lisetener for Hibernate 4
		Configuration config = new Configuration().configure("hibernate/hibernate.cfg.xml");
		StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(config.getProperties()).build();
		final EventListenerRegistry eventListenerRegistry = ssr.getService(EventListenerRegistry.class);
		eventListenerRegistry.setListeners(EventType.SAVE_UPDATE, MySaveUpdateEventListener.class);
		eventListenerRegistry.setListeners(EventType.LOAD, MyLoadListener.class);

		SessionFactory factory = config.buildSessionFactory(ssr);

		Session session = factory.openSession();
		session.beginTransaction();
		UserWithNativeID user = (UserWithNativeID) session.load(UserWithNativeID.class, 1);
		System.out.println(user);
		session.getTransaction().commit();

		session = factory.openSession();
		session.beginTransaction();
		user = new UserWithNativeID();
		user.setName("Who are u");
		session.save(user);
		session.getTransaction().commit();
	}

	@Test
	public void sessionEventListenerTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				session.addEventListeners(new MySessionEventListener(), new MySessionEventListener());

				UserWithNativeID user = (UserWithNativeID) session.load(UserWithNativeID.class, 1);
				System.out.println(user);
				return null;
			}
		});
	}

}
