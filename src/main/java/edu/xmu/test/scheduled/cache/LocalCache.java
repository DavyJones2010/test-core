package edu.xmu.test.scheduled.cache;

/**
 * Created by davywalker on 16/10/20.
 */
public interface LocalCache<T> {
    T get(String key);

    <T> void set(String key, T value);

    void remove(String key);

    void clear();
}
