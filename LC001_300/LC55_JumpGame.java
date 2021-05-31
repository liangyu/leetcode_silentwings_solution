package LC001_300;
import java.util.*;
public class LC55_JumpGame {
    /**
     * Given an array of non-negative integers nums, you are initially positioned at the first index of the array.
     *
     * Each element in the array represents your maximum jump length at that position.
     *
     * Determine if you are able to reach the last index.
     *
     * Input: nums = [2,3,1,1,4]
     * Output: true
     *
     * Constraints:
     *
     * 1 <= nums.length <= 3 * 10^4
     * 0 <= nums[i] <= 10^5
     * @param nums
     * @return
     */
    // S1: DP
    // time = O(n^2), space = O(n)
    public boolean canJump(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return true;

        int n = nums.length;
        boolean[] dp = new boolean[n];
        dp[0] = true;

        for (int i = 1; i < n; i++) {
            for (int j = 0; j < i; j++) {
                if (dp[j] && j + nums[j] >= i) {
                    dp[i] = true;
                    break;
                }
            }
        }
        return dp[n - 1];
    }

    // S2: Greedy
    // time = O(n), space = O(1)
    public boolean canJump2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return true;

        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            if (i > max) return false;
            max = Math.max(max, i + nums[i]);
        }
        return true;
    }
}
/**
 * [(x x x) x x | x x x x]
 *  0
 *  1 2
 *  3 4
 *  走到右边界撞墙了，没法继续走了 -> 如果所在位置还没到最后，就return false
 *  => 贪心解
 */
