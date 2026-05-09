package io.romangulyako.live_coding_training.stream_api.order_handler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class OrderHandler {
    public static List<String> topClientsByTotalPrice(List<Order> orders) {

        return orders.stream()
                .collect(Collectors.groupingBy(
                        Order::getCustomer,
                        Collectors.reducing(BigDecimal.ZERO, Order::getTotal, BigDecimal::add)
                ))
                .entrySet().stream()
                .sorted(Map.Entry.<String, BigDecimal>comparingByValue().reversed())
                .limit(3)
                .map(Map.Entry::getKey)
                .toList();
    }

    public static BigDecimal averagePrice(List<Order> orders) {
        if (orders.isEmpty()) {
            return BigDecimal.ZERO;
        }

        BigDecimal sum = orders.stream()
                .map(Order::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return sum.divide(BigDecimal.valueOf(orders.size()), 2, RoundingMode.HALF_UP);
    }

    public static Map<Month, List<Order>> groupByMonth(List<Order> orders) {
        return orders.stream()
                .collect(Collectors.groupingBy(
                        o -> o.getDate().getMonth()
                ));
    }

    public static Item getMustPopularItem(List<Order> orders) {
        return orders.stream()
                .flatMap(o -> o.getItems().stream())
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }


    public static void main(String[] args) {
        Item i1 = new Item(1L, null, null);
        Item i2 = new Item(2L, null, null);
        Item i3 = new Item(3L, null, null);
        Item i4 = new Item(4L, null, null);

        List<Order> orders = Arrays.asList(
                new Order("User1", BigDecimal.valueOf(120), LocalDate.parse("2020-01-01"),
                        List.of(i1, i2, i3, i4)),
                new Order("User1", BigDecimal.valueOf(120), LocalDate.parse("2020-01-01"),
                        List.of(i1, i2, i3, i4)),
                new Order("User2", BigDecimal.valueOf(150), LocalDate.parse("2020-01-01"),
                        List.of(i1, i2, i3)),
                new Order("User3", BigDecimal.valueOf(90), LocalDate.parse("2020-02-01"),
                        List.of(i1, i2)),
                new Order("User4", BigDecimal.valueOf(222), LocalDate.parse("2020-03-01"),
                        List.of(i1))
        );

        List<String> topClients = topClientsByTotalPrice(orders);
        System.out.println(topClients);

        BigDecimal averagePrice = averagePrice(orders);
        System.out.println(averagePrice);

        Map<Month, List<Order>> groupByMonth = groupByMonth(orders);
        System.out.println(groupByMonth);

        Item mustPopularItem = getMustPopularItem(orders);
        System.out.println(mustPopularItem);
    }
}
