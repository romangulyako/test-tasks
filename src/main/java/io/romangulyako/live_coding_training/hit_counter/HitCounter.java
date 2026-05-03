package io.romangulyako.live_coding_training.hit_counter;

public interface HitCounter {
    void hit(int timestamp);
    int getHits(int timestamp);
}
