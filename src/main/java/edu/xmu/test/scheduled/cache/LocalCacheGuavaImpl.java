package edu.xmu.test.scheduled.cache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import edu.xmu.test.scheduled.config.ConfigChangeListener;
import edu.xmu.test.scheduled.config.ConfigService;
import edu.xmu.test.scheduled.executor.ScheduledExecutorServiceFactory;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by davywalker on 16/10/20.
 * 非线程安全对象
 */
public class LocalCacheGuavaImpl<T> implements LocalCache, InitializingBean, ConfigChangeListener {
    private static final Logger logger = LoggerFactory.getLogger(LocalCacheGuavaImpl.class);
    private String cacheName = "defaultLocalCache";

    //    @Resource(name = "scheduledExecutorService")
    private ScheduledExecutorService scheduledExecutorService = ScheduledExecutorServiceFactory.getInstance();
    @Autowired
    private ConfigService configService;

    private LocalCacheStatTask statTask;
    /**
     * cache大小. 按照条目数计算; 默认1024条
     */
    private long maxSize = 1024L;
    /**
     * 过期时间, 单位为秒; 默认5分钟
     */
    private long expireTime = 5 * 60L;

    /**
     * 统计间隔, 单位为秒; 默认一分钟
     */
    private long statInterval = 60L;

    /**
     * 是否开启统计功能; 默认打开
     */
    private boolean enableStat = true;
    /**
     * 真正的guava cache对象
     */
    private Cache<String, T> cache;


    @Override
    public T get(String key) {
        try {
            return cache.getIfPresent(key);
        } catch (Exception e) {
            logger.error("get error. key=" + key, e);
        }
        return null;
    }

    @Override
    public void set(String key, Object value) {
        try {
            cache.put(key, (T) value);
        } catch (Exception e) {
            logger.error("set error. key=" + key, e);
        }
    }

    @Override
    public void remove(String key) {
        try {
            cache.invalidate(key);
        } catch (Exception e) {
            logger.error("remove error. key=" + key, e);
        }
    }

    @Override
    public void clear() {
        if (cache != null) {
            cache.invalidateAll();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null == scheduledExecutorService) {
            throw new RuntimeException("scheduledExecutorService is null");
        }
        initCache();
        configService.registerGlobalConfigChangeListener(cacheName + "." + LocalCachConfigKey.localCacheMaxSize, this);
        configService.registerGlobalConfigChangeListener(cacheName + "." + LocalCachConfigKey.localCacheExpireTime, this);
        configService.registerGlobalConfigChangeListener(cacheName + "." + LocalCachConfigKey.localCacheStatInterval, this);
        configService.registerGlobalConfigChangeListener(cacheName + "." + LocalCachConfigKey.localCacheEnableStat, this);
    }

    private void initCache() {
        clear();
        long currMaxSize = configService.getLongProperty(cacheName + "." + LocalCachConfigKey.localCacheMaxSize, maxSize);
        long currExpireTime = configService.getLongProperty(cacheName + "." + LocalCachConfigKey.localCacheExpireTime, expireTime);
        long currStatInterval = configService.getLongProperty(cacheName + "." + LocalCachConfigKey.localCacheStatInterval, statInterval);
        boolean currEnableStat = configService.getBooleanProperty(cacheName + "." + LocalCachConfigKey.localCacheEnableStat, enableStat);

        boolean needRebuildCache = rebuildCacheIfNeeded(currMaxSize, currExpireTime, currEnableStat);
        restatCacheIfNeeded(currEnableStat, currStatInterval, needRebuildCache);

        logger.info("cache init success. cacheName=" + cacheName + ", maxSize=" + maxSize + ", exipreTime=" + expireTime + ", enableStat=" + enableStat + ", statInterval=" + statInterval);
    }

    private boolean rebuildCacheIfNeeded(long currMaxSize, long currExpireTime, boolean currEnableStat) {
        boolean needRebuildCache = false;
        if (maxSize != currMaxSize || currExpireTime != expireTime || currEnableStat != enableStat || cache == null) {
            this.maxSize = currMaxSize;
            this.expireTime = currExpireTime;
            this.enableStat = currEnableStat;
            needRebuildCache = true;
        }
        if (needRebuildCache) {
            if (enableStat) {
                cache = CacheBuilder.newBuilder().expireAfterAccess(expireTime, TimeUnit.SECONDS).recordStats().maximumSize(maxSize).build();
            } else {
                cache = CacheBuilder.newBuilder().expireAfterAccess(expireTime, TimeUnit.SECONDS).maximumSize(maxSize).build();
            }
        }
        return needRebuildCache;
    }

    private boolean restatCacheIfNeeded(boolean currEnableStat, long currStatInterval, boolean needRebuildCache) {
        boolean needRestatCache = false;
        // 如果cache已经被重新创建, 那么statTask里对应的cache引用也需要更新.
        if (currStatInterval != statInterval || currEnableStat != enableStat || statTask == null || needRebuildCache) {
            this.statInterval = currStatInterval;
            this.enableStat = currEnableStat;
            needRestatCache = true;
        }
        if (needRestatCache) {
            if (statTask == null) {
                statTask = new LocalCacheStatTask(0, statInterval, TimeUnit.SECONDS, cache, cacheName);
                statTask.setScheduledExecutorService(scheduledExecutorService);
                if (enableStat) {
                    statTask.start();
                } else {
                    statTask.stop();
                }
            } else {
                statTask.setCache(cache);
                statTask.setScheduledExecutorService(scheduledExecutorService);
                if (enableStat) {
                    statTask.changeRate(0, statInterval, TimeUnit.SECONDS);
                } else {
                    statTask.stop();
                }
            }
        }
        return needRestatCache;
    }

    @Override
    public void propertyChanged(String name, String oldValue, String newValue) {
        if (!StringUtils.equals(oldValue, newValue)) {
            initCache();
        }
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public void setEnableStat(boolean enableStat) {
        this.enableStat = enableStat;
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public long getMaxSize() {
        return maxSize;
    }

    public long getSize() {
        return this.cache.size();
    }

    public void setStatInterval(long statInterval) {
        this.statInterval = statInterval;
    }

    public void setCacheName(String cacheName) {
        this.cacheName = cacheName;
    }
}
