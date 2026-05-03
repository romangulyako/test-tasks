package io.romangulyako.live_coding_training.counter_service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.LongAdder;

public class CounterServiceImpl implements CounterService {
    private final Map<String, LongAdder> counters = new ConcurrentHashMap<>();
    private final LongAdder totalCount = new LongAdder();


    @Override
    public void increment(String key) {
        counters.computeIfAbsent(key, k -> new LongAdder()).increment();
        totalCount.increment();
    }

    @Override
    public int get(String key) {
        LongAdder counter = counters.get(key);
        return counter == null ? 0 : counter.intValue();
    }

    @Override
    public int getTotalCount() {
        return totalCount.intValue();
    }
}
