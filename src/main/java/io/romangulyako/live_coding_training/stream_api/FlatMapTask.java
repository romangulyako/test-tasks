package io.romangulyako.live_coding_training.stream_api;

import java.util.Collection;
import java.util.List;

public class FlatMapTask {
    public record Order(String userId, List<String>products) {}

    public List<String> getAllProducts(List<Order> orders) {
        return orders.stream()
                .map(o -> o.products)
                .flatMap(Collection::stream)
                .toList();
    }
}
