package edu.xmu.test.ehcache;

import java.io.Serializable;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheException;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.config.Configuration;
import net.sf.ehcache.config.DiskStoreConfiguration;
import net.sf.ehcache.config.MemoryUnit;
import net.sf.ehcache.config.PersistenceConfiguration;
import net.sf.ehcache.config.PersistenceConfiguration.Strategy;
import net.sf.ehcache.event.CacheEventListener;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

import org.apache.log4j.Logger;
import org.junit.Test;

public class EHCacheTest {
	private static final Logger logger = Logger.getLogger(EHCacheTest.class);

	@Test
	public void createCacheTest() {
		Configuration cacheManagerConfig = new Configuration();
		cacheManagerConfig.setName("TestCacheManager");
		DiskStoreConfiguration diskStoreConfiguration = new DiskStoreConfiguration();
		diskStoreConfiguration.setPath("src/main/resources/cache");
		cacheManagerConfig.addDiskStore(diskStoreConfiguration);
		CacheManager cacheManager = CacheManager.create(cacheManagerConfig);
		logger.info("Hallo");
		Cache cache = new Cache(new CacheConfiguration().name("TestCache")
				.maxBytesLocalHeap(10, MemoryUnit.KILOBYTES)
				.maxBytesLocalDisk(100L, MemoryUnit.KILOBYTES)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				.eternal(false)
				// .timeToLiveSeconds(10L)
				// .timeToIdleSeconds(10L)
				.diskExpiryThreadIntervalSeconds(20L)
				.persistence(
						new PersistenceConfiguration()
								.strategy(Strategy.LOCALTEMPSWAP)));
		Cache cache2 = new Cache(new CacheConfiguration().name("TestCache2")
				.maxBytesLocalHeap(1000L, MemoryUnit.KILOBYTES)
				.maxBytesLocalDisk(100000L, MemoryUnit.KILOBYTES)
				.memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.LFU)
				.eternal(false)
				// .timeToLiveSeconds(10L)
				// .timeToIdleSeconds(10L)
				.diskExpiryThreadIntervalSeconds(20L)
				.persistence(
						new PersistenceConfiguration()
								.strategy(Strategy.LOCALTEMPSWAP)));
		cacheManager.addCache(cache);
		cacheManager.addCache(cache2);
		cache.getCacheEventNotificationService().registerListener(
				new CacheEventListener() {
					@Override
					public void notifyRemoveAll(Ehcache cache) {
						logger.info("remove all");
					}

					@Override
					public void notifyElementUpdated(Ehcache cache,
							Element element) throws CacheException {
						logger.info(element + " updated");
					}

					@Override
					public void notifyElementRemoved(Ehcache cache,
							Element element) throws CacheException {
						logger.info(element + " removed");
					}

					@Override
					public void notifyElementPut(Ehcache cache, Element element)
							throws CacheException {
						logger.info(element + " put");
					}

					@Override
					public void notifyElementExpired(Ehcache cache,
							Element element) {
						logger.info(element + " expired");
					}

					@Override
					public void notifyElementEvicted(Ehcache cache,
							Element element) {
						logger.info(element + " evicted");
					}

					@Override
					public void dispose() {
						logger.info("disposed");
					}

					public Object clone() throws CloneNotSupportedException {
						throw new CloneNotSupportedException();
					}
				});
//		Element e = new Element(0, new Student());
//		cache.put(e);
//		cache2.put(e);
		for (int i = 0; i < 10000; i++) {
			Element tmp = new Element(i, new byte[1024]);
			cache.put(tmp);
			cache2.put(tmp);
		}

		for(int i = 0; i < 10000; i ++){
			Element element = cache.get(i);
			System.out.println(element == null ? null : (byte[])element.getObjectValue());
			element = cache2.get(i);
			System.out.println(element == null ? null : (byte[])element.getObjectValue());
		}
		System.out.println(cache.getSize());
		System.out.println(cache.getMemoryStoreSize());
		System.out.println(cache.getDiskStoreSize());
		System.out.println(cache2.getSize());
		System.out.println(cache2.getMemoryStoreSize());
		System.out.println(cache2.getDiskStoreSize());

		cacheManager.removeCache("TestCache2");
		cacheManager.removeCache("TestCache");
		cacheManager.shutdown();
	}
}
