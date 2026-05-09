package io.romangulyako.live_coding_training.merge_intervals;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MergeIntervals {

    private static class Interval {
        private int start;
        private int end;

        public Interval(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static List<int[]> mergeIntervals(List<int[]> intervals) {

        if (intervals == null || intervals.size() <= 1) {
            return intervals;
        }

        List<int[]> intervalsCopy = new ArrayList<>(intervals);

        intervalsCopy.sort(Comparator.comparingInt(a -> a[0]));

        List<int[]> mergedIntervals = new ArrayList<>();

        int[] currentInterval = intervals.get(0);

        for (int i = 1; i < intervalsCopy.size(); i++) {
            int[] next = intervalsCopy.get(i);

            if (currentInterval[1] >= next[0]) {
                currentInterval[1] = Math.max(currentInterval[1], next[1]);
            } else {
                mergedIntervals.add(currentInterval);
                currentInterval = next;
            }
        }

        mergedIntervals.add(currentInterval);

        return mergedIntervals;
    }


    public static void main(String[] args) {
        // [[1,3], [2,6], [8,10], [15,18]]

        List<int[]> intervals = new ArrayList<>();
        intervals.add(new int[]{1, 3});
        intervals.add(new int[]{2, 6});
        intervals.add(new int[]{8, 10});
        intervals.add(new int[]{15, 18});
        intervals.add(new int[]{0, 14});

        List<int[]> intervals2 = List.of(
                new int[]{1, 3},
                new int[]{2, 6},
                new int[]{8, 10}
        );

        List<int[]> mergedIntervals = mergeIntervals(intervals2);

        for (int[] interval : mergedIntervals) {
            System.out.println(interval[0] + " " + interval[1]);
        }
    }
}
