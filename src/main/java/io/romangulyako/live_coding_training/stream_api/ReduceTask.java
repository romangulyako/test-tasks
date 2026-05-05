package io.romangulyako.live_coding_training.stream_api;

import java.util.List;

public class ReduceTask {
    public int multiplyAll(List<Integer> list) {
        return list.stream()
                .reduce(1, (a, b) -> a * b);
    }
}
