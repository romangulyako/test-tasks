package io.romangulyako.live_coding_training.versioned_key_value_store;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VersionedKVStoreImpl implements VersionedKVStore {

    private static class VersionEntry {
        private final String version;
        private final long timestamp;

        public VersionEntry(String version, long timestamp) {
            this.version = version;
            this.timestamp = timestamp;
        }
    }

    private final Map<String, List<VersionEntry>> store = new HashMap<>();

    @Override
    public void put(String key, String value) {
        store
                .computeIfAbsent(key, k -> new ArrayList<>())
                .add(new VersionEntry(value, System.currentTimeMillis()));
    }

    @Override
    public String get(String key) {
        List<VersionEntry> list = store.get(key);
        if (list == null || list.isEmpty()) return null;
        return list.get(list.size() - 1).version;
    }

    @Override
    public String get(String key, long timestamp) {
        List<VersionEntry> list = store.get(key);
        if (list == null || list.isEmpty()) return null;

        int left = 0, right = list.size() - 1;
        String result = null;

        while (left <= right) {
            int mid = (left + right) / 2;
            if (list.get(mid).timestamp <= timestamp) {
                result = list.get(mid).version;
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return result;
    }
}
