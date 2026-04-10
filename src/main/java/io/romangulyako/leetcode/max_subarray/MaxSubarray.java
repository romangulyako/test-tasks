package io.romangulyako.leetcode.max_subarray;

public class MaxSubarray {
    public static void main(String[] args) {
        int[] nums = {-2,1,-3,4,-1,2,1,-5,4};

        System.out.println(maxSubArray(nums));
    }
    public static int maxSubArray(int[] nums) {
        int currentMaxSum = nums[0];
        int finalMaxSum = nums[0];

        for (int i = 1; i < nums.length; i++) {
            currentMaxSum = Math.max(nums[i], currentMaxSum + nums[i]);
            finalMaxSum = Math.max(finalMaxSum, currentMaxSum);
        }

        return finalMaxSum;
    }

}
