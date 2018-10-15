package edu.xmu.test.scheduled.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheStats;
import edu.xmu.test.scheduled.AbsScheduledTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * Created by davywalker on 16/10/24.
 * cache统计定时任务
 */
public class LocalCacheStatTask<T> extends AbsScheduledTask {
    private static final Logger logger = LoggerFactory.getLogger(LocalCacheStatTask.class);
    private static final Logger statLogger = LoggerFactory.getLogger("LOCAL_CACHE_STAT_LOGGER");

    private Cache<String, T> cache;
    private String cacheName;

    public LocalCacheStatTask(long initialDelay, long rate, TimeUnit unit, Cache cache, String cacheName) {
        super(initialDelay, rate, unit);
        this.cache = cache;
        this.cacheName = cacheName;
    }

    @Override
    public void run() {
        try {
            CacheStats stats = cache.stats();
            if (null != stats) {
                statLogger.info(new CacheStatsWrapper(stats).toString());
            }
        } catch (Exception e) {
            logger.error("stat error.", e);
        }
    }

    /**
     * cacheStats的包装类, 只是为了能够自定义toString方法.
     */
    class CacheStatsWrapper {
        CacheStats cacheStats;

        public CacheStatsWrapper(CacheStats cacheStats) {
            this.cacheStats = cacheStats;
        }

        @Override
        public String toString() {
            return String.format("cacheName=%s, visitCount=%d, errorCount=%d, hitCount=%d, missCount=%d, hitRate=%.3f, cacheSize=%d", cacheName, cacheStats.requestCount(), 0, cacheStats.hitCount(), cacheStats.missCount(), cacheStats.hitRate(), cache.size());
        }
    }

    public void setCache(Cache<String, T> cache) {
        this.cache = cache;
    }
}
