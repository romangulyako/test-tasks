package io.romangulyako.live_coding_training.stream_api;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PartitioningByTask {
    public record User(String name, int age) {
    }

    public Map<Boolean, List<User>> partitionByAge(List<User> users, int age) {
        return users.stream()
                .collect(Collectors
                        .partitioningBy(user -> user.age >= age));
    }
}
