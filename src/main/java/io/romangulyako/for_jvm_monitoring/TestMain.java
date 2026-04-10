package io.romangulyako.for_jvm_monitoring;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TestMain {
    record SomeObject (
            int id,
            String name,
            LocalDateTime createDate
    ) {}

    public static void main(String[] args) throws InterruptedException {
        int counter = 0;
        List<SomeObject> list = new ArrayList<>();
        while (true) {
            if (list.size() >= 100) {
                list.clear();
            }

            SomeObject obj = new SomeObject(
                    ++counter,
                    "Some name",
                    LocalDateTime.now()
            );

            list.add(obj);

            System.out.printf("List size: %d, Created objects: %d%n", list.size(), counter);
            Thread.sleep(1000);
        }
    }
}
