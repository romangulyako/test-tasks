package io.romangulyako.live_coding_training.hit_counter;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class HitCounterImpl implements HitCounter {
    private static final int FIVE_MINUTES_IN_SECONDS = 300;
    private final Queue<Integer> hits = new ConcurrentLinkedQueue<>();

    @Override
    public void hit(int timestamp) {
        if (!hits.isEmpty() && timestamp < hits.peek()) {
            throw new IllegalArgumentException("Timestamp cannot be less");
        }

        cleanQueue(timestamp);

        hits.add(timestamp);
    }

    @Override
    public int getHits(int timestamp) {
        cleanQueue(timestamp);
        return hits.size();
    }

    private void cleanQueue(int timestamp) {
        while (!hits.isEmpty() && hits.peek() < timestamp - FIVE_MINUTES_IN_SECONDS) {
            hits.poll();
        }
    }
}
