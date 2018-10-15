package edu.xmu.test.guava.cache;

import org.junit.Test;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.ForwardingCache;

public class ForwardingCacheTest {
	@Test
	public void forwardingCacheTest() {
		Cache<String, String> cache = CacheBuilder.newBuilder().build();
		Cache<String, String> bufferCache = new LogForwardingCache<String, String>(
				new BufferForwardingCache<String, String>(cache));
		bufferCache.getIfPresent("AAA");
	}

	private class BufferForwardingCache<K, V> extends ForwardingCache<K, V> {

		private final Cache<K, V> delegate;

		public BufferForwardingCache(Cache<K, V> delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		protected Cache<K, V> delegate() {
			return delegate;
		}
	}

	private class LogForwardingCache<K, V> extends ForwardingCache<K, V> {
		private final Cache<K, V> delegate;

		public LogForwardingCache(Cache<K, V> delegate) {
			super();
			this.delegate = delegate;
		}

		@Override
		protected Cache<K, V> delegate() {
			return delegate;
		}
	}
}
