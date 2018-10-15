package edu.xmu.test.hibernate.service;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.junit.Test;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.model.UserWithNativeID;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

/**
 * All the criterias can be used directly with HQL as explained in previous tutorial
 */
public class CriteriaQueryTest {
	@Test
	public void simpleCriteriaTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session input) {
				Criteria cr = input.createCriteria(UserWithNativeID.class);
				// Everytime we invoke list, sql will always be executed
				List list = cr.list();
				list = cr.list();
				list = cr.list();
				System.out.println(list);
				return null;
			}
		});
	}

	@Test
	public void criteriaWithRestrictionTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				// Equals & Greater & Less & Between
				Criteria cr = session.createCriteria(UserWithNativeID.class);
				cr.add(Restrictions.eq("name", "Yang, Kunlun"));
				List list = cr.list();
				System.out.println(list);
				cr = session.createCriteria(UserWithNativeID.class);
				cr.add(Restrictions.gt("id", 5));
				list = cr.list();
				System.out.println(list);
				cr = session.createCriteria(UserWithNativeID.class);
				cr.add(Restrictions.between("id", 5, 7));
				list = cr.list();
				System.out.println(list);
				System.out.println("===============================================================");

				// Null & NotNull & Empty & NotEmpty
				cr = session.createCriteria(UserWithNativeID.class);
				cr.add(Restrictions.isNotNull("dateCreated"));
				list = cr.list();
				System.out.println(list);
				// cr = session.createCriteria(UserWithNativeID.class);
				// cr.add(Restrictions.isNotEmpty("dateCreated"));
				// list = cr.list();
				// System.out.println(list);
				System.out.println("===============================================================");

				// Like & ilike(case insensitive like)
				cr = session.createCriteria(UserWithNativeID.class);
				cr.add(Restrictions.like("name", "Yang%"));
				list = cr.list();
				System.out.println(list);
				cr = session.createCriteria(UserWithNativeID.class);
				cr.add(Restrictions.ilike("name", "yang%"));
				list = cr.list();
				System.out.println(list);
				return null;
			}
		});
	}

	@Test
	public void criteriaWithRestrictionTest2() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				// and
				Criteria cr = session.createCriteria(UserWithNativeID.class);

				LogicalExpression exp = Restrictions.and(Restrictions.between("id", 1, 6), Restrictions.like("name", "Yan%"));
				cr.add(exp);

				List list = cr.list();
				System.out.println(list);
				return null;
			}
		});
	}

	@Test
	public void criteriaPaginationTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				Criteria cr = session.createCriteria(UserWithNativeID.class);
				cr.setFirstResult(3);
				cr.setMaxResults(5);
				List list = cr.list();
				System.out.println(list);
				return null;
			}
		});
	}

	@Test
	public void criteriaSortingTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				Criteria cr = session.createCriteria(UserWithNativeID.class);
				// order (method chain code style)
				cr.setFirstResult(3).setMaxResults(5).addOrder(Order.desc("id"));
				List list = cr.list();
				System.out.println(list);
				return null;
			}
		});
	}

	@Test
	public void criteriaProjectionAndAggregationTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				Criteria cr = session.createCriteria(UserWithNativeID.class);
				// "select count(*) from ..." will be generated
				cr.setProjection(Projections.rowCount());
				List list = cr.list();
				System.out.println(list);
				System.out.println("===============================================================");

				cr = session.createCriteria(UserWithNativeID.class);
				// "select avg(id) from ..." will be generated
				cr.setProjection(Projections.avg("id"));
				list = cr.list();
				System.out.println(list);
				System.out.println("===============================================================");

				cr = session.createCriteria(UserWithNativeID.class);
				// "select max(id) from ..." will be generated
				cr.setProjection(Projections.max("id"));
				list = cr.list();
				System.out.println(list);
				System.out.println("===============================================================");

				cr = session.createCriteria(UserWithNativeID.class);
				// "select distinct(name) from ..." will be generated
				cr.setProjection(Projections.distinct(Projections.property("name")));
				list = cr.list();
				System.out.println(list);
				System.out.println("===============================================================");
				return null;
			}
		});
	}

}
