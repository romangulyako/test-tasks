package io.romangulyako.live_coding_training.versioned_key_value_store;

public interface VersionedKVStore {
    void put(String key, String value);
    String get(String key);
    String get(String key, long timestamp);
}
