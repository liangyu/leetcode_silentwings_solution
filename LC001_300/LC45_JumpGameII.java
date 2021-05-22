package LC001_300;
import java.util.*;
public class LC45_JumpGameII {
    /**
     * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
     *
     * Each element in the array represents your maximum jump length at that position.
     *
     * Your goal is to reach the last index in the minimum number of jumps.
     *
     * You can assume that you can always reach the last index.
     *
     * Input: nums = [2,3,1,1,4]
     * Output: 2
     *
     * Constraints:
     *
     * 1 <= nums.length <= 1000
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n)
    public int jump(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        int[] dp = new int[n];
        dp[0] = 0;

        for (int i = 1; i < n; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int j = 0; j < i; j++) {
                if (j + nums[j] >= i) {
                    dp[i] = Math.min(dp[i], dp[j]);
                }
            }
            dp[i] += 1;
        }
        return dp[n - 1];
    }

    // S2: Greedy
    // time = O(n), space = O(1)
    public int jump2(int[] nums) {
        // corner case
        if (nums == null || nums.length < 2) return 0;

        int res = 0, curMax = 0, maxNext = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            maxNext = Math.max(maxNext, i + nums[i]);
            if (i == curMax) {
                res++;
                curMax = maxNext;
            }
        }
        return res;
    }
}
