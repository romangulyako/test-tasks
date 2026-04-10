package io.romangulyako.leetcode.longest_unique_substring;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class LongestUniqueSubstring {
    public static void main(String[] args) {
        String string = "abcabcbb";

        System.out.println(lengthOfLongestSubstring1(string));
        System.out.println(lengthOfLongestSubstring2(string));

    }

    // O(n)
    public static int lengthOfLongestSubstring1(String s) {
        Set<Character> set = new HashSet<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {

            while (set.contains(s.charAt(right))) {
                set.remove(s.charAt(left));
                left++;
            }

            set.add(s.charAt(right));
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }

    // O(n)
    public static int lengthOfLongestSubstring2(String s) {
        Map<Character, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;

        for (int right = 0; right < s.length(); right++) {
            char c = s.charAt(right);

            if (map.containsKey(c)) {
                left = Math.max(left, map.get(c) + 1);
            }

            map.put(c, right);
            maxLength = Math.max(maxLength, right - left + 1);
        }

        return maxLength;
    }
}
