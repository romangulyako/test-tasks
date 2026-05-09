package io.romangulyako.live_coding_training.top_frequent_words;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TopFrequentWords {
    public static List<String> topFrequentWords(List<String> words, int count) {
        Map<String, Integer> map = new HashMap<>();
        for (String word : words) {
            map.compute(word, (k, v) -> v == null ? 1 : v + 1);
        }

        /* Можно так:
        Map<String, Long> map = words.stream()
              .collect(Collectors.groupingBy(
                      Function.identity(),
                      Collectors.counting()
              ));
        * */

        PriorityQueue<Map.Entry<String, Integer>> queue = new PriorityQueue<>(
                (o1, o2) -> {
                    int frequent = o1.getValue().compareTo(o2.getValue());
                    if (frequent == 0) {
                        return o2.getKey().compareTo(o1.getKey());
                    }
                    return frequent;
                }
        );

        map.forEach((k, v) -> {
            queue.offer(Map.entry(k, v));

            if (queue.size() > count) {
                queue.poll();
            }
        });

        List<String> result = new ArrayList<>();

        while (!queue.isEmpty()) {
            result.add(queue.poll().getKey());
        }

        Collections.reverse(result);

        return result;
    }

    public static void main(String[] args) {
        List<String> words = List.of(
                "java",
                "sql",
                "java",
                "spring",
                "sql",
                "java");

        System.out.println(topFrequentWords(words, 2));
    }
}
