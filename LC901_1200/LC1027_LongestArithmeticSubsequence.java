package LC901_1200;
import java.util.*;
public class LC1027_LongestArithmeticSubsequence {
    /**
     * Given an array nums of integers, return the length of the longest arithmetic subsequence in nums.
     *
     * Recall that a subsequence of an array nums is a list nums[i1], nums[i2], ..., nums[ik] with
     * 0 <= i1 < i2 < ... < ik <= nums.length - 1, and that a sequence seq is arithmetic if seq[i+1] - seq[i] are all
     * the same value (for 0 <= i < seq.length - 1).
     *
     * Input: nums = [3,6,9,12]
     * Output: 4
     *
     * Constraints:
     *
     * 2 <= nums.length <= 1000
     * 0 <= nums[i] <= 500
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int longestArithSeqLength(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        HashMap<Integer, Integer>[] dp = new HashMap[n];
        for (int i = 0; i < n; i++) dp[i] = new HashMap<>();

        int res = 1;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < i; j++) {
                int diff = nums[i] - nums[j];
                dp[i].put(diff, dp[j].getOrDefault(diff, 1) + 1); // 等差数组至少长度 = 1
                res = Math.max(res, dp[i].get(diff));
            }
        }
        return res;
    }
}
/**
 * ref: LC446
 * 确定一个公差
 * 对于任何一个数来说，我们要存一个
 * follow-up:
 * 如果这些数没有顺序，给一个set，从这些数里面抽取数组成等差数列，最长是多少？
 * dp思想都是给你一个数组来考虑，这里的话就没法做。
 * 用双指针来做。
 */