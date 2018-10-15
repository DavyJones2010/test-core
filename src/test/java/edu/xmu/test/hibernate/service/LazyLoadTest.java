package edu.xmu.test.hibernate.service;

import java.util.Set;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Test;

import com.google.common.base.Function;
import com.google.common.collect.Sets;

import edu.xmu.test.hibernate.model.Many;
import edu.xmu.test.hibernate.model.One;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class LazyLoadTest {
	private static final Logger logger = Logger.getLogger(LazyLoadTest.class);

	@Test
	public void saveTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				Many many = new Many();
				One one = new One();
				one.setName("A");
				One one2 = new One();
				one2.setName("B");
				One one3 = new One();
				one3.setName("C");
				many.setOnes(Sets.newHashSet(one, one2, one3));
				many.setName("Many");
				// <set name="ones" cascade="all">
				// If we set cascade="all" then we don't need to insert ones before insert many
				// session.save(one);
				// session.save(one2);
				// session.save(one3);

				session.save(many);
				return null;
			}
		});
	}

	@Test
	public void loadTest() {
		Many many = SessionFactoryUtil.executeInTransaction(new Function<Session, Many>() {
			@Override
			public Many apply(Session session) {
				Many many = (Many) session.load(Many.class, 2);

				// Only till now, "select from many" sql is executed
				logger.info("Many [" + many.getId() + ": " + many.getName() + "]");

				Set<One> ones = many.getOnes();
				// Returned Set is an instance of org.hibernate.collection.PersistentSet which surpports lazy load
				// Only till now, "select from one" sql is executed
				for (One one : ones) {
					logger.info("One [" + one.getId() + ": " + one.getName() + "]");
				}
				return many;
			}
		}).get();
		logger.info("Many [" + many.getId() + ": " + many.getName() + "]");
	}

	/**
	 * Lazy load test when we set: <br/>
	 * <class name="Many" lazy="true"> <br/>
	 * <set name="ones" lazy="true"> <br/>
	 * ..
	 */
	@Test
	public void loadTest2() {
		Many many = SessionFactoryUtil.executeInTransaction(new Function<Session, Many>() {
			@Override
			public Many apply(Session session) {
				Many many = (Many) session.load(Many.class, 2);
				// Only till now, "select from many" sql is executed
				logger.info("Many [" + many.getId() + ": " + many.getName() + "]");
				// Only till now, "select from one" sql is executed
				// But performance is not good enough coz it will fetch all student with the same many_id. We only need count(*)
				// Solution: <set name="ones" lazy="extra">...</set>, count sql will be executed
				logger.info("One [" + many.getOnes().size() + "]");
				return many;
			}
		}).get();
		logger.info("Many [" + many.getId() + ": " + many.getName() + "]");
	}

	/**
	 * Lazy load test when we set: <br/>
	 * <class name="Many" lazy="false"> <br/>
	 * <set name="ones" lazy="false"> <br/>
	 * ..
	 */
	@Test
	public void loadTest3() {
		Many many = SessionFactoryUtil.executeInTransaction(new Function<Session, Many>() {
			@Override
			public Many apply(Session session) {
				// "select from many" & "select from one" sql are executed
				Many many = (Many) session.load(Many.class, 2);
				logger.info("Many [" + many.getId() + ": " + many.getName() + "]");

				Set<One> ones = many.getOnes();
				for (One one : ones) {
					logger.info("One [" + one.getId() + ": " + one.getName() + "]");
				}
				return many;
			}
		}).get();
		logger.info("Many [" + many.getId() + ": " + many.getName() + "]");
	}
}
