package LC001_300;
import java.util.*;
public class LC213_HouseRobberII {
    /**
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money
     * stashed. All houses at this place are arranged in a circle. That means the first house is the neighbor of the
     * last one. Meanwhile, adjacent houses have a security system connected, and it will automatically contact the
     * police if two adjacent houses were broken into on the same night.
     *
     * Given an integer array nums representing the amount of money of each house, return the maximum amount of money
     * you can rob tonight without alerting the police.
     *
     * Input: nums = [2,3,2]
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 1000
     * @param nums
     * @return
     */
    // S1: dp
    // time = O(n), space = O(1)
    public int rob(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[1];

        int n = nums.length;
        return Math.max(helper(nums, 0, n - 2), helper(nums, 1, n - 1));
    }

    private int helper(int[] nums, int lo, int hi) {
        int preNo = 0, preYes = 0;

        for (int i = lo; i <= hi; i++) {
            int temp = preNo;
            preNo = Math.max(preNo, preYes);
            preYes = temp + nums[i];
        }
        return preYes;
    }

    // S2: 区间型dp
    // time = O(n^2), space = O(n^2)
    public int rob2(int[] nums) {
        int n = nums.length;
        if (n == 0) return 0;
        if (n == 1) return nums[0];

        int[][] dp = new int[n][n];

        for (int len = 1; len <= n; len++) {
            for (int i = 0; i + len - 1 < n; i++) {
                int j = i + len - 1;
                dp[i][j] = Math.max(nums[i] + (i + 2 > j ? 0 : dp[i + 2][j]), (i + 1 > j ? 0 : dp[i + 1][j]));
            }
        }
        return Math.max(nums[0] + (2 > n - 2 ? 0 : dp[2][n - 2]), dp[1][n - 1]);
    }
}
/**
 * 双状态dp
 * N [      ]
 * Y N [   ]N
 *
 * dp[i...j] -> dp[i][j]: the maximum amount of money you can rob from nums[i:j]
 * 大问题转化为小问题
 * max{nums[i] + dp[i+2...j], dp[i+1,...j]}
 * folow-up: 在一个数组里，取 n / 3个不相邻的数，使得它们的和最大
 * 区间型dp[i][j] = Math.max(nums[i] + dp[i+2][j], dp[i+1][j])
 */
