package io.romangulyako.live_coding_training.lru_cache;

import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacheImpl<K, V> implements LRUCache<K, V> {

    private final int capacity;

    private final LinkedHashMap<K, V> map;

    public LRUCacheImpl(int capacity) {
        this.capacity = capacity;

        this.map = new LinkedHashMap<>(capacity, 0.75f, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return size() > LRUCacheImpl.this.capacity;
            }
        };
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public String toString() {
        return map.toString();
    }

    public static void main(String[] args) {
        LRUCache<String, String> cache = new LRUCacheImpl<>(3);

        cache.put("key1", "value1");
        cache.put("key2", "value2");
        cache.put("key3", "value3");

        System.out.println(cache);

        cache.put("key4", "value4");

        System.out.println(cache);

        cache.get("key2");

        System.out.println(cache);

        cache.put("key3", "value5");

        System.out.println(cache);
    }
}
