package io.romangulyako.live_coding_training.group_anagrams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class GroupAnagrams {
    static List<List<String>> groupAnagrams(String[] words) {

        Map<String, List<String>> map =
                Arrays.stream(words)
                        .collect(Collectors.groupingBy(word -> {
                            char[] chars = word.toCharArray();
                            Arrays.sort(chars);
                            return new String(chars);
                        }));

        return new ArrayList<>(map.values());
    }

    public static void main(String[] args) {
        String[] array = {"eat", "tea", "tan", "ate", "nat", "bat"};

        List<List<String>> result = groupAnagrams(array);

        result.forEach(System.out::println);
    }
}
