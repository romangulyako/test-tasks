package io.romangulyako.live_coding_training.lru_cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LruCacheSelf<K, V> {

    private final Map<K, V> cache;

    public LruCacheSelf(int capacity) {
        cache = new LinkedHashMap<K,V>(capacity, 0.75f, true) {
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > capacity;
            }
        };
    }

    public Object get(K key) {
        return cache.get(key);
    }

    public void put(K key, V value) {
        cache.put(key, value);
    }
}
