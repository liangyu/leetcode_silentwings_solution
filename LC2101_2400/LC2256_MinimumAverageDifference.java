package LC2101_2400;

public class LC2256_MinimumAverageDifference {
    /**
     * You are given a 0-indexed integer array nums of length n.
     *
     * The average difference of the index i is the absolute difference between the average of the first i + 1 elements
     * of nums and the average of the last n - i - 1 elements. Both averages should be rounded down to the nearest integer.
     *
     * Return the index with the minimum average difference. If there are multiple such indices, return the smallest one.
     *
     * Note:
     *
     * The absolute difference of two numbers is the absolute value of their difference.
     * The average of n elements is the sum of the n elements divided (integer division) by n.
     * The average of 0 elements is considered to be 0.
     *
     * Input: nums = [2,5,3,9,5,3]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int minimumAverageDifference(int[] nums) {
        int n = nums.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        long min = Long.MAX_VALUE;
        int res = -1;
        for (int i = 0; i < n; i++) {
            long val1 = presum[i + 1] / (i + 1);
            long val2 = n - i - 1 == 0 ? 0 : (presum[n] - presum[i + 1]) / (n - i - 1);
            long diff = Math.abs(val1 - val2);
            if (diff < min) {
                min = diff;
                res = i;
            }
        }
        return res;
    }
}
