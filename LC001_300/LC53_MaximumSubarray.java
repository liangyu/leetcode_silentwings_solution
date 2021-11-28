package LC001_300;
import java.util.*;
public class LC53_MaximumSubarray {
    /**
     * Given an integer array nums, find the contiguous subarray (containing at least one number) which has the largest
     * sum and return its sum.
     * <p>
     * Input: nums = [-2,1,-3,4,-1,2,1,-5,4]
     * Output: 6
     * <p>
     * Constraints:
     * <p>
     * 1 <= nums.length <= 3 * 10^4
     * -10^5 <= nums[i] <= 10^5
     * <p>
     * <p>
     * Follow up: If you have figured out the O(n) solution, try coding another solution using the divide and conquer
     * approach, which is more subtle.
     *
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n), space = O(n)
    public int maxSubArray(int[] nums) {
        int[] dp = new int[nums.length];
        dp[0] = nums[0];
        int max = dp[0];

        for (int i = 1; i < nums.length; i++) {
            dp[i] = Math.max(dp[i - 1] + nums[i], nums[i]);
            max = Math.max(max, dp[i]);
        }
        return max;
    }

    // S2: rolling matrix
    // time = O(n), space = O(1)
    public int maxSubArray2(int[] nums) {
        int n = nums.length;
        int old = nums[0], now = 0, res = old;

        for (int i = 1; i < n; i++) {
            now = Math.max(old + nums[i], nums[i]);
            res = Math.max(res, now);
            old = now;
        }
        return res;
    }
}