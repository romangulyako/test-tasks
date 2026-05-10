package io.romangulyako;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList("a", "b", "c"));

        for (String s : list) {
            if (s.equals("a")) {
                list.remove(s);
            }
        }
        System.out.println(list);

        List<String> strings = new ArrayList<>();
        List<? extends Object> objects = strings;

        
    }
}