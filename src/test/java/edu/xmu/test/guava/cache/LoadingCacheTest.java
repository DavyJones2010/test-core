package edu.xmu.test.guava.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.base.Strings;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.CacheStats;
import com.google.common.cache.LoadingCache;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import com.google.common.cache.Weigher;
import com.google.common.collect.Lists;

public class LoadingCacheTest {

	private LoadingCache<String, String> cache;

	@Before
	public void setUp() {
		cache = CacheBuilder.newBuilder().concurrencyLevel(10)
				.expireAfterAccess(10, TimeUnit.MINUTES)
				.build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						System.out.println("Load");
						//
						return key.toUpperCase();
					}
				});
	}

	@Test
	public void getTest() throws ExecutionException {
		// cache.get("aaa");
		cache.getIfPresent("aaa");
		// cache.get("aaa");
		// cache.get("aaa");
		// cache.get("aaa");
		// cache.get("aaa");
	}

	@Test
	public void getNullTest() throws ExecutionException {
		cache = CacheBuilder.newBuilder().build(
				new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return "aaa".equals(key) ? key.toUpperCase() : null;
					}
				});
		System.out.println(cache.get("aaa"));
		/*
		 * get: CacheLoader.load() will be invoked when cannot find
		 * corresponding key
		 */
		// System.out.println(cache.get("bbb"));
		/*
		 * getIfPresent: CacheLoader.load() will not be invoked when cannot find
		 * corresponding key
		 */
		System.out.println(cache.getIfPresent("ccc"));
		System.out.println(cache.size());
		/*
		 * Best practice: DO NOT RETURN NULL IN CacheLoader.load()
		 */
	}

	/*
	 * When several threads load the value for same key concurrently, the first
	 * thread will invoke the CacheLoader.load(), other threads have to wait for
	 * the completion of this load process. When it is done, other threads will
	 * not invoke CacheLoader.load(), and will fetch the value from cache
	 * directly instead.
	 * 
	 * The other way to implement asyncGet is override reload() method in our
	 * own Cache and start a new thread inside reload().
	 * 
	 * oader com.google.common.cache.LocalCache$Segment.lockedGetOrLoad()
	 */
	@Test
	public void asyncGetTest() throws InterruptedException {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep((long) (Math.random() * 1000));
					cache.get("aaa");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		Thread t2 = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep((long) (Math.random() * 1000));
					cache.get("aaa");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();
		t2.start();
		Thread.sleep(1000L);
	}

	/*
	 * Unlike LoadingCache.get, the getAll() does not block multiple requests
	 * for the same key.
	 * 
	 * Dig into the implementation of getAll(): When our own cacheLoader doesn't
	 * override getAll(), it will throw an UnsupportedLoadingOperationException,
	 * and then fall back to Guava's default impl
	 */
	@Test
	public void getAllTest() throws ExecutionException {
		cache.getAll(Lists.newArrayList("aaa", "bbb", "ccc"));
	}

	/*
	 * We can fetch the CheckedException in catch block. UncheckedException will
	 * also be printed and fail-fast
	 */
	@Test
	public void checkedExceptionTest() {
		cache = CacheBuilder.newBuilder().build(
				new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						throw new Exception("Exception");
					}
				});
		try {
			cache.get("aaa");
			cache.get("bbb", new Callable<String>() {

				@Override
				public String call() throws Exception {
					return "ccd";
				}

			});
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
	}

	/*
	 * When using weakKeys, after Key have been collected, cache.size() may not
	 * decrease. An empty entry will in the original weakKeys position. But it
	 * is not a problem, we can never access it, and by making eviction policy,
	 * we can easily evict it automatically.
	 */
	@Test
	public void weakKeysTest() throws Exception {
		LoadingCache<Person, String> cache = CacheBuilder.newBuilder()
				.weakKeys().build(new CacheLoader<Person, String>() {
					@Override
					public String load(Person p) throws Exception {
						return p.name;
					}
				});
		Person p = new Person("Yang", 25);
		cache.get(p);
		p = null;
		System.gc();
		Thread.sleep(1000L);
		System.out.println(cache.size());
		System.out.println(cache.asMap());
		cache.get(new Person("Yang", 25));
		System.out.println(cache.size());
		System.out.println(cache.asMap());
		System.gc();
		Thread.sleep(1000L);
		System.out.println(cache.size());
		System.out.println(cache.asMap().size());
	}

	@Test
	public void weakValuesTest() throws Exception {
		Cache<String, Person> cache = CacheBuilder.newBuilder().weakValues()
				.build();

		cache.put("Yang", new Person("Yang", 25));
		System.gc();
		Thread.sleep(1000L);
		System.out.println(cache.getIfPresent("Yang"));
	}

	/*
	 * After weakValue has been collected, if we use the same key to get it,
	 * CacheLoader.load() will be invoked and a new value would be returned
	 */
	@Test
	public void weakValuesTest2() throws Exception {
		LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
				.weakValues().build(new CacheLoader<String, Person>() {
					@Override
					public Person load(String key) throws Exception {
						System.out.println("Load for key: " + key);
						return new Person(key, 25);
					}
				});

		cache.put("Yang", new Person("Yang", 25));
		System.gc();
		Thread.sleep(1000L);
		System.out.println(cache.get("Yang"));
		// the returned Person is collected
		System.gc();
		Thread.sleep(1000L);
		System.out.println(cache.get("Yang"));
	}

	/*
	 * In most circumstances it is better to set a maximum size instead of using
	 * soft references.
	 * 
	 * Entries with values that have been garbage collected may be counted in
	 * Cache.size, but will never be visible to read or write operations
	 */
	@Test
	@Ignore
	public void softValuesTest() throws Exception {
		LoadingCache<String, Person> cache = CacheBuilder.newBuilder()
				.softValues().build(new CacheLoader<String, Person>() {
					@Override
					public Person load(String key) throws Exception {
						System.out.println("Load for key: " + key);
						return new Person(key, 25);
					}
				});
		cache.put("Yang", new Person("Yang", 25));
		System.gc();
		Thread.sleep(1000L);
		System.out.println(cache.get("Yang"));
		byte[][] byte1 = new byte[1024][];
		for (int i = 0; i < 1024; i++) {
			byte1[i] = new byte[1024 * 512];
			System.gc();
			System.out.println(cache.get("Yang"));
		}
	}

	/*
	 * When size is zero, elements will be evicted immediately after being
	 * loaded into the cache. This can be useful in testing, or to disable
	 * caching temporarily without a code change.
	 */
	@Test
	public void maxSizeTest() {
		Cache<String, String> commonCache = CacheBuilder.newBuilder()
				.maximumSize(100)
				.removalListener(new RemovalListener<String, String>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, String> notification) {
						System.out.println(String.format(
								"Removing entry: [key=%s, value=%s]",
								notification.getKey(), notification.getValue()));
					}
				}).build();
		for (int i = 0; i < 1000; i++) {
			commonCache.put("Key" + i % 50, "Value" + i % 50);
		}
		System.out.println(commonCache.size());
	}

	/*
	 * 1> When weigher is zero, corresponding elements will not be evicted by
	 * weight based eviction policy
	 * 
	 * 2> When maximumWeight is zero, elements will be evicted immediately after
	 * being loaded into cache. This can be useful in testing, or to disable
	 * caching temporarily without a code change.
	 */
	@Test
	public void maxWeightTest() throws Exception {
		cache = CacheBuilder.newBuilder()
				.weigher(new Weigher<String, String>() {
					@Override
					public int weigh(String key, String value) {
						return key.length();
					}
				}).maximumWeight(10000)
				.removalListener(new RemovalListener<String, String>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, String> notification) {
						System.out.println(notification + ": "
								+ notification.getCause());
					}
				}).build(new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return key.toUpperCase();
					}
				});

		cache.get("");
		System.out.println(cache.size());

		cache.get(Strings.repeat("a", 5));
		System.out.println(cache.size());

		cache.get(Strings.repeat("a", 11));
		System.out.println(cache.size());

		cache.get(Strings.repeat("a", 20));
		System.out.println(cache.size());

		cache.get(Strings.repeat("a", 1001));
		System.out.println(cache.size());
	}

	@Test
	public void cacheStatsTest() {
		Cache<String, Person> personCache = CacheBuilder.newBuilder()
				.maximumSize(100).recordStats().build();
		for (int i = 0; i < 1000; i++) {
			String name = "NAME_" + i;
			personCache.put(name, new Person(name, i % 25));
		}
		CacheStats cacheStats = personCache.stats();
		System.out.println(cacheStats);

		for (int i = 0; i < 1000; i++) {
			String name = "NAME_" + i;
			personCache.getIfPresent(name);
		}

		cacheStats = personCache.stats();
		System.out.println(cacheStats);


		for (int i = 0; i < 100000000; i++) {
			String name = "NAME_" + i;
			personCache.getIfPresent(name);
		}
		cacheStats = personCache.stats();
		System.out.println(cacheStats);
		for (int i = 0; i < 100000000; i++) {
			String name = "NAME_" + i;
			personCache.getIfPresent(name);
		}
		cacheStats = personCache.stats();
		System.out.println(cacheStats);
	}

	/*
	 * How did Guava parse spec into CacheBuilder instance?
	 */
	@Test
	public void cacheConfigTest() throws Exception {
		String spec = "concurrencyLevel=10,maximumSize=200,expireAfterWrite=2m,recordStats,weakKeys";
		cache = CacheBuilder.from(spec).build(
				new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						return key.toUpperCase();
					}
				});
		cache.get("aaa");
	}

	@Test
	public void removalListenerTest() {
		Cache<String, String> commonCache = CacheBuilder.newBuilder()
				.removalListener(new RemovalListener<String, String>() {
					@Override
					public void onRemoval(
							RemovalNotification<String, String> notification) {
						System.out.println(String.format(
								"Removing entry: [key=%s, value=%s]",
								notification.getKey(), notification.getValue()));
					}
				}).build();
		commonCache.put("Key_1", "Value_1");
		commonCache.put("Key_2", "Value_2");
		commonCache.put("Key_3", "Value_3");
		commonCache.invalidate("Key_1");
		String value = commonCache.getIfPresent("Key_1");
		System.out.println(String.format("Removing entry: [key=%s, value=%s]",
				"Key_1", value));
	}

	@Ignore
	@Test
	public void snycRefreshTest() throws Exception {
		String spec = "concurrencyLevel=10,maximumSize=200,expireAfterWrite=2m";
		cache = CacheBuilder.from(spec).build(
				new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						System.out.println("Start reload");
						Thread.sleep((long) (Math.random() * 1000));
						String value = key.toUpperCase();
						System.out.println("Finished reload");
						return value;
					}
				});
		cache.put("aaa", "BBB");
		cache.refresh("aaa");
		while (true) {
			System.out.println(cache.get("aaa"));
		}
	}

	/*
	 * LoadingCache.refresh() can be implemented asynchronously. The stale value
	 * will continue to be returned until reload completes.
	 */
	@Test
	@Ignore
	public void asnycRefreshTest() throws Exception {
		String spec = "concurrencyLevel=10,maximumSize=200,expireAfterWrite=2m";
		cache = CacheBuilder.from(spec).build(
				new CacheLoader<String, String>() {
					@Override
					public String load(String key) throws Exception {
						System.out.println("Start reload");
						Thread.sleep((long) (Math.random() * 1000));
						String value = key.toUpperCase();
						System.out.println("Finished reload");
						return value;
					}
				});
		cache.put("aaa", "BBB");
		new Thread(new Runnable() {
			@Override
			public void run() {
				cache.refresh("aaa");
			}
		}).start();
		while (true) {
			System.out.println(cache.get("aaa"));
		}
	}

	private static class Person {
		private String name;
		private int age;

		public Person(String name, int age) {
			super();
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return "Person [name=" + name + ", age=" + age + "]";
		}
	}
}
