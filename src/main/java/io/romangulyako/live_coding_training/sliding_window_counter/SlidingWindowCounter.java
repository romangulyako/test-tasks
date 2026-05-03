package io.romangulyako.live_coding_training.sliding_window_counter;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class SlidingWindowCounter {

    private final Map<String, Queue<Long>> requests = new ConcurrentHashMap<>();
    private final long timeWindowMillis = 60 * 1000;

    void recordRequest(String userId) {
        long now = System.currentTimeMillis();
        Queue<Long> queue = requests.computeIfAbsent(userId, k -> new ConcurrentLinkedQueue<>());
        queue.add(now);
    }

    int getRequestsLastMinute(String userId) {
        long now = System.currentTimeMillis();

        Queue<Long> queue = requests.get(userId);

        if (queue == null) {
            return 0;
        }

        while (!queue.isEmpty() && now - queue.peek() > timeWindowMillis) {
            queue.poll();
        }

        return queue.size();
    }
}
