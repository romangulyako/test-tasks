package io.romangulyako.live_coding_training.request_deduplicator;

public interface RequestDeduplicator {
    boolean isDuplicate(String requestId);
}
