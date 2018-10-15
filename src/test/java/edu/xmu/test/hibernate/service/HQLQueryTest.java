package edu.xmu.test.hibernate.service;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class HQLQueryTest {
	HQLQuery query;

	@Before
	public void setUp() {
		query = new HQLQuery();
	}

	@Test
	public void simpleQueryTest() {
		query.simpleQuery();
	}

	@Test
	public void pagingQueryTest() {
		query.pagingQuery();
	}

	@Test
	public void simpleQueryTest2() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				// SELECT
				Query query = session.createQuery("select U.name, U.password, U.dateCreated, U.dateExpired from UserWithNativeID as U where U.id < 2 order by U.dateCreated desc");
				List result = query.list();
				System.out.println(result);
				// UPDATE
				query = session.createQuery("UPDATE UserWithNativeID AS U SET U.name='Kunlun, Yang' WHERE U.id = :user_id");
				query.setParameter("user_id", 1);
				int c = query.executeUpdate();
				System.out.println(c + " rows updated");
				// INSERT
				// In HQL, only the INSERT INTO ... SELECT ... is supported; there is "NO" INSERT INTO ... VALUES ...
				// query = session.createQuery("INSERT INTO UserWithNativeID(name, password, dateCreated, dateExpired) VALUES (:name, :password, :dateCreated, :dateExpired)");
				// query.setParameter("name", "Davy,Jones");
				// query.setParameter("password", "Answer me");
				// query.setParameter("dateCreated", Calendar.getInstance().getTime());
				// query.setParameter("dateExpired", Calendar.getInstance().getTime());
				query = session.createQuery("INSERT INTO UserWithNativeID (name, password, dateCreated, dateExpired) SELECT name, password, dateCreated, dateExpired FROM User");
				c = query.executeUpdate();
				System.out.println(c + " rows updated");
				return null;
			}
		});
	}

	@Test
	public void aggregeteQueryTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				// MAX/MIN
				Query query = session.createQuery("SELECT max(U.dateCreated) FROM UserWithNativeID AS U");
				List res = query.list();
				System.out.println("MAX dateCreated: " + res.get(0));
				query = session.createQuery("SELECT min(U.dateExpired) FROM UserWithNativeID AS U");
				res = query.list();
				System.out.println("MIN dateCreated: " + res.get(0));
				// COUNT
				query = session.createQuery("SELECT COUNT(DISTINCT U.name) FROM UserWithNativeID AS U");
				res = query.list();
				System.out.println("COUNT DISTINCT name: " + res.get(0));
				// SUM
				query = session.createQuery("SELECT SUM(U.id) FROM UserWithNativeID AS U");
				res = query.list();
				System.out.println("SUM id: " + res.get(0));
				return null;
			}
		});
	}
}
