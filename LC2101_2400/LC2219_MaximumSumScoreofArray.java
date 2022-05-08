package LC2101_2400;

public class LC2219_MaximumSumScoreofArray {
    /**
     * You are given a 0-indexed integer array nums of length n.
     *
     * The sum score of nums at an index i where 0 <= i < n is the maximum of:
     *
     * The sum of the first i + 1 elements of nums.
     * The sum of the last n - i elements of nums.
     * Return the maximum sum score of nums at any index.
     *
     * Input: nums = [4,3,-2,5]
     * Output: 10
     *
     * Constraints:
     *
     * n == nums.length
     * 1 <= n <= 10^5
     * -10^5 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public long maximumSumScore(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        long res = Integer.MIN_VALUE;
        for (int i = 1; i <= n; i++) {
            long max = Math.max(presum[i], presum[n] - presum[i - 1]);
            res = Math.max(res, max);
        }
        return res;
    }
}
