package io.romangulyako.leetcode.longest_unique_substring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestUniqueSubstringMyself {
    public static void main(String[] args) {
        String string = "abcdefg";

        System.out.println(getLengthOfLongestSubstring(string));

        System.out.println(getLengthOfLongestSubstring1(string));
    }

    public static int getLengthOfLongestSubstring(String s) {
        Set<Character> uniqueChars = new HashSet<>();

        int left = 0;
        int right = 0;
        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);

            while (uniqueChars.contains(c)) {
                uniqueChars.remove(s.charAt(left));
                left++;
            }

            uniqueChars.add(c);
            right++;
            maxLength = Math.max(maxLength, right - left);
        }

        return maxLength;
    }

    public static int getLengthOfLongestSubstring1(String s) {
        Map<Character, Integer> uniqueChars = new HashMap<>();

        int left = 0;
        int right = 0;
        int maxLength = 0;

        for (int i = 0; i < s.length(); i++) {
            Character c = s.charAt(i);

            if (uniqueChars.containsKey(c)) {
                left = Math.max(left, uniqueChars.get(c) + 1); // при просто uniqueChars.get(c) + 1 может пойти назад
                uniqueChars.remove(c); // можно удалить в этой реализации
            }

            uniqueChars.put(c, i);
            right++;

            maxLength = Math.max(maxLength, right - left);
        }

        return maxLength;
    }
}
