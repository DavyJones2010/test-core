package edu.xmu.test.scheduled.cache;

/**
 * Created by davywalker on 16/10/24.
 */
public interface LocalCachConfigKey {
    String localCacheMaxSize = "localCacheMaxSize";
    String localCacheExpireTime = "localCacheExpireTime";
    String localCacheEnableStat = "localCacheEnableStat";
    String localCacheStatInterval = "localCacheStatInterval";
}
