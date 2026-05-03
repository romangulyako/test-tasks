package io.romangulyako.live_coding_training.in_memory_task_scheduler;

public interface InMemoryTaskScheduler {
    void schedule(Runnable task, long delayMillis);
}
