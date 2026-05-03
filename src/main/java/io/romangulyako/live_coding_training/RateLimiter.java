package io.romangulyako.live_coding_training;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RateLimiter {
    private final int maxRequests;
    private final long timeWindowMillis;

    private final Map<String, Queue<Long>> requests = new ConcurrentHashMap<>();

    public RateLimiter(int maxRequests, long timeWindowMillis) {
        this.maxRequests = maxRequests;
        this.timeWindowMillis = timeWindowMillis;
    }

    public boolean allowRequest(String userId) {
        long now = System.currentTimeMillis();
        Queue<Long> queue = requests.computeIfAbsent(userId, k -> new ConcurrentLinkedQueue<>());

        while (!queue.isEmpty() && now - queue.peek() > timeWindowMillis) {
            queue.poll();
        }

        synchronized (this) {
            if (queue.size() < maxRequests) {
                queue.add(now);
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(3, 1000);

        for (int i = 0; i < 5; i++) {
            System.out.println(rateLimiter.allowRequest("user"));
        }

        Thread.sleep(2000);

        for (int i = 0; i < 4; i++) {
            System.out.println(rateLimiter.allowRequest("user"));
        }
    }
}
