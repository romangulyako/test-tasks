package io.romangulyako.live_coding_training;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryCacheWithTTL<K, V> {

    private static class CacheEntry<V> {
        V value;
        long expiredAt;

        public CacheEntry(V value, long expiredAt) {
            this.value = value;
            this.expiredAt = expiredAt;
        }
    }

    private final Map<K, CacheEntry<V>> cache = new ConcurrentHashMap<>();

    public void put(K key, V value, long ttlMillis) {
        long expiredAt = System.currentTimeMillis() + ttlMillis;
        cache.put(key, new CacheEntry<>(value, expiredAt));
    }

    public Optional<V> get(K key) {
        CacheEntry<V> entry = cache.get(key);

        if (entry == null) {
            return Optional.empty();
        }

        long now = System.currentTimeMillis();

        if (now > entry.expiredAt) {
            cache.remove(key);
            return Optional.empty();
        }

        return Optional.ofNullable(entry.value);

    }

    public static void main(String[] args) throws InterruptedException {
        InMemoryCacheWithTTL<String, String> cache = new InMemoryCacheWithTTL<>();

        cache.put("a", "hello", 1000);

        System.out.println(cache.get("a"));

        Thread.sleep(1000);

        System.out.println(cache.get("a"));
    }
}
