package io.romangulyako.live_coding_training.in_memory_task_scheduler;

import java.util.PriorityQueue;

public class InMemoryTaskSchedulerImpl implements InMemoryTaskScheduler {

    private static class ScheduledTask implements Comparable<ScheduledTask> {
        final Runnable task;
        final long executeAt;

        ScheduledTask(Runnable task, long executeAt) {
            this.task = task;
            this.executeAt = executeAt;
        }

        @Override
        public int compareTo(ScheduledTask other) {
            return Long.compare(this.executeAt, other.executeAt);
        }
    }

    private final PriorityQueue<ScheduledTask> queue = new PriorityQueue<>();
    private final Object lock = new Object();

    public InMemoryTaskSchedulerImpl() {
        Thread worker = new Thread(this::runWorker);
        worker.setDaemon(true);
        worker.start();
    }


    @Override
    public void schedule(Runnable task, long delayMillis) {
        long executeAt = System.currentTimeMillis() + delayMillis;

        synchronized (lock) {
            queue.add(new ScheduledTask(task, executeAt));
            lock.notify(); // разбудить worker
        }
    }

    private void runWorker() {
        while (true) {
            try {
                ScheduledTask nextTask;

                synchronized (lock) {
                    while (queue.isEmpty()) {
                        lock.wait();
                    }

                    nextTask = queue.peek();
                    long now = System.currentTimeMillis();

                    if (nextTask.executeAt > now) {
                        long waitTime = nextTask.executeAt - now;
                        lock.wait(waitTime);
                        continue;
                    }

                    queue.poll(); // убираем задачу
                }

                // выполняем вне synchronized!
                nextTask.task.run();

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
