package edu.xmu.test.javax.redis;

import static org.junit.Assert.assertEquals;

import org.apache.commons.pool.BaseKeyedPoolableObjectFactory;
import org.apache.commons.pool.BasePoolableObjectFactory;
import org.apache.commons.pool.impl.GenericKeyedObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.junit.Test;

import com.sun.tools.javac.util.StringUtils;

public class CommonsPoolTest {

	@Test
	public void test() throws Exception {
		// 创建一个对象池
		GenericKeyedObjectPool pool = new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
			@Override
			public Object makeObject(Object o) throws Exception {
				return o;
			}
		});
		// 添加对象到池，重复的key对应的值会链接到list后边
		pool.addObject("a");
		pool.addObject("a");
		pool.addObject("b");
		pool.addObject("x");

		// 清除最早的对象
		pool.clearOldest();

		// 获取并输出对象
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("b"));
		System.out.println(pool.borrowObject("c"));
		System.out.println(pool.borrowObject("c"));
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("a"));
		System.out.println(pool.borrowObject("a"));
		// System.out.println(pool.borrowObject("a")); // 因为队列exhausted,
		// 线程会一直pending在这里

		// 输出池状态: 默认对象池的大小为: 8
		System.out.println(pool.getMaxIdle());
		System.out.println(pool.getMaxActive());
	}

	@Test
	public void lifeCycleTest() throws Exception {
		// 1) 综述: pool里维护的是一个Map<Key, LinkedList<Object>>的数据结构,
		// Object实际存的是ObjectTimestampPair对象
		// 2) 生命周期:
		// addObject->makeObject->[validateObject]->passiveObject
		// borrowObject->activeObject->[validateObject]
		// returnObject->[validateObject]->passiveObject
		// 3) 参数说明:
		// pool里默认_maxIdle=8,即LinkedList的大小为8, 当超过
		// 当List里所有的object都被borrow之后,再来borrow,会有三种action:
		// WHEN_EXHAUSTED_FAIL,WHEN_EXHAUSTED_BLOCK,WHEN_EXHAUSTED_GROW
		// 4) max=active+idle
		// 5) 增长:
		// 生产->0->pubObject->pubObject->autoMakeObject->autoMakeObject...->maxIdle->消费->0->setWhenExhaustedAction?如果是block->那么该线程将一直等待
		GenericKeyedObjectPool pool = new GenericKeyedObjectPool(new BaseKeyedPoolableObjectFactory() {
			@Override
			public Object makeObject(Object o) throws Exception {
				String upperCase = StringUtils.toUpperCase((String) o);
				System.out.println("makeObject, key=" + o + " obj=" + upperCase);
				return upperCase;
			}

			@Override
			public void destroyObject(Object key, Object obj) throws Exception {
				System.out.println("destroyObject, key=" + key + " obj=" + obj);
				super.destroyObject(key, obj);
			}

			@Override
			public boolean validateObject(Object key, Object obj) {
				System.out.println("validateObject, key=" + key + " obj=" + obj);
				return super.validateObject(key, obj);
			}

			@Override
			public void activateObject(Object key, Object obj) throws Exception {
				System.out.println("activateObject, key=" + key + " obj=" + obj);
				super.activateObject(key, obj);
			}

			@Override
			public void passivateObject(Object key, Object obj) throws Exception {
				System.out.println("passivateObject, key=" + key + " obj=" + obj);
				super.passivateObject(key, obj);
			}
		});
		pool.setTestOnBorrow(true);
		pool.setTestOnReturn(true);
		pool.setWhenExhaustedAction(GenericKeyedObjectPool.WHEN_EXHAUSTED_BLOCK);
		// pool.setWhenExhaustedAction(GenericKeyedObjectPool.WHEN_EXHAUSTED_FAIL);

		pool.addObject("a");
		pool.addObject("a");

		Object borrowObject = pool.borrowObject("a");
		System.out.println(borrowObject);

		Object borrowObject2 = pool.borrowObject("a");
		System.out.println(borrowObject2);

		Object borrowObject3 = pool.borrowObject("a");
		System.out.println(borrowObject3);

		Object borrowObject4 = pool.borrowObject("a");
		System.out.println(borrowObject4);
		Object borrowObject5 = pool.borrowObject("a");
		System.out.println(borrowObject5);
		Object borrowObject6 = pool.borrowObject("a");
		System.out.println(borrowObject6);
		Object borrowObject7 = pool.borrowObject("a");
		System.out.println(borrowObject7);
		Object borrowObject8 = pool.borrowObject("a");
		System.out.println(borrowObject8);
		Object borrowObject9 = pool.borrowObject("a");
		System.out.println(borrowObject9);

		pool.returnObject("a", borrowObject);
		pool.returnObject("a", borrowObject2);
	}

	@Test
	public void simplePoolTest() throws Exception {
		// 内部实际上维护的是一个CursorableLinkedList
		GenericObjectPool genericObjectPool = new GenericObjectPool(new BasePoolableObjectFactory() {
			@Override
			public Object makeObject() throws Exception {
				return new Object();
			}
		});
		genericObjectPool.addObject();
		Object object = genericObjectPool.borrowObject();
		genericObjectPool.returnObject(object);
		genericObjectPool.returnObject(object); // 同一个对象可以放在池中多次,
												// 队列里维护的其实是ObjectTimestampPair(object)对象
		assertEquals(2, genericObjectPool.getNumIdle());

		genericObjectPool.borrowObject();
		System.out.println(genericObjectPool.getNumIdle());
		System.out.println(genericObjectPool.getNumActive());

		genericObjectPool.borrowObject();
		System.out.println(genericObjectPool.getNumIdle());
		System.out.println(genericObjectPool.getNumActive());

		genericObjectPool.returnObject(new Object());
		System.out.println(genericObjectPool.getNumIdle());
		System.out.println(genericObjectPool.getNumActive());

		genericObjectPool.returnObject(new Object());
		System.out.println(genericObjectPool.getNumIdle());
		System.out.println(genericObjectPool.getNumActive());

		genericObjectPool.borrowObject();
		genericObjectPool.borrowObject();
		genericObjectPool.borrowObject();
		genericObjectPool.borrowObject();
		genericObjectPool.borrowObject();
		Object borrowObject = genericObjectPool.borrowObject();
		System.out.println(borrowObject);
		// System.out.println(genericObjectPool.getMaxActive());
		System.out.println(genericObjectPool.getNumIdle());
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.returnObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		genericObjectPool.invalidateObject(borrowObject);
		System.out.println(genericObjectPool.getNumActive());
		genericObjectPool.invalidateObject(borrowObject);
		System.out.println(genericObjectPool.getNumActive());
		genericObjectPool.invalidateObject(borrowObject);
		System.out.println(borrowObject);
		System.out.println(genericObjectPool.getNumActive());
		System.out.println(genericObjectPool.getNumIdle());
		borrowObject = genericObjectPool.borrowObject();
		System.out.println(borrowObject);
		// genericObjectPool.borrowObject();
	}

}
