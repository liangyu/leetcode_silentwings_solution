package LC001_300;
import java.util.*;
public class LC198_HouseRobber {
    /**
     * You are a professional robber planning to rob houses along a street. Each house has a certain amount of money
     * stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems
     * connected and it will automatically contact the police if two adjacent houses were broken into on the same night.
     *
     * Given an integer array nums representing the amount of money of each house, return the maximum amount of money
     * you can rob tonight without alerting the police.
     *
     * Input: nums = [1,2,3,1]
     * Output: 4
     *
     * Constraints:
     *
     * 1 <= nums.length <= 100
     * 0 <= nums[i] <= 400
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public int rob(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int n = nums.length;
        int[] dp = new int[n + 1];
        dp[0] = 0;
        dp[1] = nums[0];

        for (int i = 2; i <= n; i++) {
            dp[i] = Math.max(dp[i - 1], dp[i - 2] + nums[i - 1]);
        }
        return dp[n];
    }

    // S1.2: rolling matrix
    // time = O(n), space = O(1)
    public int rob2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;
        if (nums.length == 1) return nums[0];

        int n = nums.length;
        int old = 0, now = nums[0];

        for (int i = 2; i <= n; i++) {
            int dp = Math.max(now, old + nums[i - 1]);
            old = now;
            now = dp;
        }
        return now;
    }

    // S2
    // time = O(n), space = O(1)
    public int rob3(int[] nums) {
        int preNo = 0, preYes = 0;
        for (int i = 0; i < nums.length; i++) {
            int temp = preNo;
            preNo = Math.max(preNo, preYes);
            preYes = temp + nums[i];
        }
        return Math.max(preNo, preYes);
    }
}

