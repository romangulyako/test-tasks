package io.romangulyako.live_coding_training.request_deduplicator;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RequestDeduplicatorImpl implements RequestDeduplicator {
    private final Map<String, Long> requests = new ConcurrentHashMap<>();
    private final long ttlMillis = 5 * 60 * 1000;

    @Override
    public boolean isDuplicate(String requestId) {
        long now = System.currentTimeMillis();

        Long expireAt = requests.get(requestId);

        if (expireAt != null) {
            if (now <= expireAt) {
                return true;
            } else {
                requests.remove(requestId);
            }
        }

        requests.put(requestId, now + ttlMillis);
        return false;
    }
}
