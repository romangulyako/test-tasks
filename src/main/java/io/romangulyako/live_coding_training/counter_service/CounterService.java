package io.romangulyako.live_coding_training.counter_service;

public interface CounterService {
    void increment(String key);
    int get(String key);
    int getTotalCount();
}
