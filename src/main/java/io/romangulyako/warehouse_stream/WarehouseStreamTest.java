package io.romangulyako.warehouse_stream;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class WarehouseStreamTest {
    record Product(
            String id, String name, String category, BigDecimal price, LocalDate expiryDate, boolean isHazardous
    ) {
    }

    record StorageLocation(
            String id,
            String name,
            String zoneType,
            BigDecimal capacity,
            BigDecimal currentQuantity,
            boolean allowsHazardous,
            List<Product> products
    ) {
    }

    record Warehouse(
            String id,
            String name,
            List<StorageLocation> locations
    ) {
    }

    private static final List<Warehouse> WAREHOUSES = List.of(
            new Warehouse(
                    "WH1", "Москва Центральный",
                    List.of(
                            new StorageLocation(
                                    "L1", "Зона приемки", "RECEIVING",
                                    new BigDecimal("1000"), new BigDecimal("150"),
                                    false,
                                    List.of(
                                            new Product("P1", "Ноутбук Lenovo", "Электроника", new BigDecimal("75000"), LocalDate.of(2025, 12, 31), false),
                                            new Product("P2", "Мышь Logitech", "Электроника", new BigDecimal("2500"), LocalDate.of(2026, 6, 30), false)
                                    )
                            ),
                            new StorageLocation(
                                    "L2", "Холодильная камера", "COLD",
                                    new BigDecimal("500"), new BigDecimal("300"),
                                    false,
                                    List.of(
                                            new Product("P3", "Молоко", "Молочные", new BigDecimal("85"), LocalDate.of(2024, 3, 15), false),
                                            new Product("P3", "Молоко", "Молочные", new BigDecimal("85"), LocalDate.of(2024, 3, 20), false)
                                    )
                            )
                    )
            ),
            new Warehouse(
                    "WH2", "Самара Логистик",
                    List.of(
                            new StorageLocation(
                                    "L3", "Стеллаж", "BULK",
                                    new BigDecimal("2000"), new BigDecimal("1800"),
                                    false,
                                    List.of(
                                            new Product("P4", "Кресло", "Мебель", new BigDecimal("15000"), null, false),
                                            new Product("P5", "Стол", "Мебель", new BigDecimal("9500"), null, false),
                                            new Product("P1", "Ноутбук Lenovo", "Электроника", new BigDecimal("75000"), LocalDate.of(2025, 12, 31), false)
                                    )
                            ),
                            new StorageLocation(
                                    "L4", "Хим.хранилище", "HAZARDOUS",
                                    new BigDecimal("300"), new BigDecimal("50"),
                                    true,
                                    List.of(
                                            new Product("P6", "Краска", "Химия", new BigDecimal("520"), LocalDate.of(2025, 8, 1), true),
                                            new Product("P7", "Растворитель", "Химия", new BigDecimal("320"), LocalDate.of(2024, 12, 31), true)
                                    )
                            )
                    )
            ),
            new Warehouse(
                    "WH3", "Новосибирск Северный",
                    List.of(
                            new StorageLocation(
                                    "L5", "Зона комплектации", "PICKING",
                                    new BigDecimal("800"), new BigDecimal("600"),
                                    false,
                                    List.of(
                                            new Product("P8", "Телевизор", "Электроника", new BigDecimal("55000"), LocalDate.of(2027, 5, 1), false),
                                            new Product("P9", "Наушники", "Электроника", new BigDecimal("12000"), LocalDate.of(2026, 10, 15), false),
                                            new Product("P2", "Мышь Logitech", "Электроника", new BigDecimal("2500"), LocalDate.of(2026, 6, 30), false)
                                    )
                            ),
                            new StorageLocation(
                                    "L6", "Огнеопасные", "HAZARDOUS",
                                    new BigDecimal("200"), new BigDecimal("30"),
                                    true,
                                    List.of(
                                            new Product("P10", "Лак для волос", "Косметика", new BigDecimal("450"), LocalDate.of(2025, 3, 1), true),
                                            new Product("P11", "Аэрозоль", "Химия", new BigDecimal("280"), LocalDate.of(2024, 11, 30), true)
                                    )
                            )
                    )
            )
    );

    public static void main(String[] args) {

        // Задание 1: найти все опасные товары
        List<String> hazardousProducts = WAREHOUSES.stream()
                .flatMap(w -> w.locations.stream()
                        .flatMap(l -> l.products.stream()
                                .filter(Product::isHazardous)
                                .map(Product::name)))
                .distinct()
                .toList();

        System.out.println(hazardousProducts);

        // Задание 2: найти склад с наибольшей общей стоимостью товаров
        Optional<Map.Entry<String, BigDecimal>> richestWarehouse = WAREHOUSES.stream()
                .collect(Collectors.toMap(
                        Warehouse::name,
                        warehouse -> warehouse.locations().stream()
                                .flatMap(loc -> loc.products().stream())
                                .map(Product::price)
                                .reduce(BigDecimal.ZERO, BigDecimal::add)
                ))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue());


        richestWarehouse.ifPresent(entry ->
                System.out.println(entry.getKey() + " - " + entry.getValue()));

        // Задание 3: найти товары, которые есть на нескольких складах, с указанием складов
        Map<String, List<String>> productsOnMultipleWarehouses = WAREHOUSES
                .parallelStream()
                .flatMap(warehouse -> warehouse.locations().stream()
                        .flatMap(loc -> loc.products().stream()
                                .map(product -> new AbstractMap.SimpleEntry<>(
                                        product.name(),
                                        warehouse.name()
                                ))
                        )
                )
                .collect(Collectors.groupingByConcurrent(
                        Map.Entry::getKey,
                        ConcurrentHashMap::new,
                        Collectors.mapping(
                                Map.Entry::getValue,
                                Collectors.toList()
                        )
                ))
                .entrySet().stream()
                .filter(entry -> {
                    long uniqueWarehouseCount = entry.getValue().stream().distinct().count();
                    return uniqueWarehouseCount > 1;
                })
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue().stream()
                                .distinct()
                                .toList()));

        System.out.println(productsOnMultipleWarehouses);
    }
}
