package edu.xmu.test.hibernate.service;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.junit.Test;

import com.google.common.base.Function;

import edu.xmu.test.hibernate.model.CachedObject;
import edu.xmu.test.hibernate.util.SessionFactoryUtil;

public class SecondLevelCacheTest {
	@Test
	public void insertTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				CachedObject o = new CachedObject();
				o.setName("A");
				CachedObject o2 = new CachedObject();
				o2.setName("B");
				CachedObject o3 = new CachedObject();
				o3.setName("C");
				CachedObject o4 = new CachedObject();
				o4.setName("D");

				session.save(o);
				session.save(o2);
				session.save(o3);
				session.save(o4);

				return null;
			}
		});
	}

	@Test
	public void secondLevelCacheTest() {
		doInLoop(10);
		assertEquals("cached_object", SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames()[0]);
		assertEquals(9, SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
	}

	@Test
	public void secondLevelQueryCacheTest() {
		doInLoop2(10);
		assertEquals("cached_object", SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheRegionNames()[0]);
		assertEquals(19, SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
	}

	/**
	 * When we update data which are fetched using session.get(), only the modified data will be updated and reloaded.
	 */
	@Test
	public void secondLevelCacheUpdateTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				CachedObject cachedObject = (CachedObject) session.get(CachedObject.class, 1);
				System.out.println(cachedObject);
				return null;
			}
		});
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				CachedObject cachedObject = (CachedObject) session.get(CachedObject.class, 1);
				System.out.println(cachedObject);
				// Once we update cached object, by the time session closed
				// 1> update sql will be executed
				// 2> second level cache will be refreshed(only for the modified object), and getSecondLevelCachePutCount will increase
				cachedObject.setName("NAME_" + Calendar.getInstance().getTimeInMillis());
				return null;
			}
		});
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@Override
			public Void apply(Session session) {
				CachedObject cachedObject = (CachedObject) session.get(CachedObject.class, 1);
				System.out.println(cachedObject);
				return null;
			}
		});
		System.out.println(SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
		System.out.println(SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCachePutCount());
	}

	/**
	 * When we update data which are fetched using hql/criteria, all the secondary cache data will be truncated and reloaded.
	 */
	@Test
	public void secondLevelCacheHQLUpdateTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				List cachedObjects = session.createQuery("from CachedObject").setCacheable(true).list();
				System.out.println(cachedObjects);
				return null;
			}
		});
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				List cachedObjects = session.createQuery("from CachedObject").setCacheable(true).list();
				System.out.println(cachedObjects);
				// Once we update cached object, by the time session closed
				// 1> update sql will be executed
				// 2> second level cache will be refreshed(it will be cleaned first, and then reload all the objects to cache), getSecondLevelCachePutCount will increase
				((CachedObject) cachedObjects.get(0)).setName("NAME_" + Calendar.getInstance().getTimeInMillis());
				return null;
			}
		});
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				List cachedObjects = session.createQuery("from CachedObject").setCacheable(true).list();
				System.out.println(cachedObjects);
				return null;
			}
		});
		System.out.println(SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
		System.out.println(SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCachePutCount());
	}

	@Test
	public void secondLevelCacheCriteriaUpdateTest() {
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				List cachedObjects = session.createCriteria(CachedObject.class).setCacheable(true).list();
				System.out.println(cachedObjects);
				return null;
			}
		});
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				List cachedObjects = session.createCriteria(CachedObject.class).setCacheable(true).list();
				System.out.println(cachedObjects);
				// Once we update cached object, by the time session closed
				// 1> update sql will be executed
				// 2> second level cache will be refreshed(it will be cleaned first, and then reload all the objects to cache), getSecondLevelCachePutCount will increase
				((CachedObject) cachedObjects.get(0)).setName("NAME_" + Calendar.getInstance().getTimeInMillis());
				return null;
			}
		});
		SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
			@SuppressWarnings("rawtypes")
			@Override
			public Void apply(Session session) {
				List cachedObjects = session.createCriteria(CachedObject.class).setCacheable(true).list();
				System.out.println(cachedObjects);
				return null;
			}
		});
		System.out.println(SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCacheHitCount());
		System.out.println(SessionFactoryUtil.getSessionFactory().getStatistics().getSecondLevelCachePutCount());
	}

	private void doInLoop(int loopCount) {
		for (int i = 0; i < loopCount; i++) {
			SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
				@Override
				public Void apply(Session session) {
					CachedObject cachedObject = (CachedObject) session.get(CachedObject.class, 1);
					System.out.println(cachedObject);
					return null;
				}
			});
			SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
				@Override
				public Void apply(Session session) {
					// common cache will not be shared with query cache
					// Code below will not fetch data from second level cache
					Query query = session.createQuery("from CachedObject where id=1");
					query.list();
					return null;
				}
			});
		}
	}

	private void doInLoop2(int loopCount) {
		for (int i = 0; i < loopCount; i++) {
			SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
				@Override
				public Void apply(Session session) {
					Query query = session.createQuery("from CachedObject where id=1");
					query.setCacheable(true).list();
					return null;
				}
			});
			SessionFactoryUtil.executeInTransaction(new Function<Session, Void>() {
				@Override
				public Void apply(Session session) {
					// query cache will be shared with common cache
					// Code below will fetch data from second level query cache
					session.get(CachedObject.class, 1);
					return null;
				}
			});
		}
	}
}
