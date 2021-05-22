package LC1501_1800;
import java.util.*;
public class LC1749_MaximumAbsoluteSumofAnySubarray {
    /**
     * You are given an integer array nums. The absolute sum of a subarray [numsl, numsl+1, ..., numsr-1, numsr]
     * is abs(numsl + numsl+1 + ... + numsr-1 + numsr).
     *
     * Return the maximum absolute sum of any (possibly empty) subarray of nums.
     *
     * Note that abs(x) is defined as follows:
     *
     * If x is a negative integer, then abs(x) = -x.
     * If x is a non-negative integer, then abs(x) = x.
     *
     * Input: nums = [1,-3,2,3,-4]
     * Output: 5
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -10^4 <= nums[i] <= 10^4
     *
     * @param nums
     * @return
     */
    // S1: max + min
    // time = O(n), space = O(1)
    public int maxAbsoluteSum(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int max = 0, min = 0;
        int res = 0;

        for (int n : nums) {
            max = Math.max(n, max + n); // 截止到n为止的max subarray sum
            min = Math.min(n, min + n);
            res = Math.max(res, Math.abs(max));
            res = Math.max(res, Math.abs(min));
        }
        return res;
    }

    // S2: Prefix
    // time = O(n), space = O(1)
    public int maxAbsoluteSum2(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int prefix_max = 0, prefix_min = 0;
        int prefix = 0;
        int res = 0;

        for (int x : nums) {
            prefix += x;
            prefix_max = Math.max(prefix_max, prefix);
            prefix_min = Math.min(prefix_min, prefix);
        }
        return prefix_max - prefix_min;
    }
}

/**
 * 绝对值 => 可正可负 => 正的找最大值，负的找最小值
 * max |subarray sum| => |max subarray sum|
 *                       |min subarray sum|
 * kadane algorthim
 * dp[i]: the max subarray sum ending at nums[i]
 * X [X X X i] X X
 * dp[i] = max(nums[i], dp[i - 1] + nums[i])
 * dp[i] = min(nums[i], dp[i - 1] + nums[i])
 *
 * S2: Prefix -> 前缀和之差，2种情况取最大化 -> 找一个最大的前缀和 - 最小的前缀和(前面一个最大，后面一个最小)
 * subarray sum of [i + 1 : j] = prefix[j] - prefix[i]
 * abs(subarray sum of [i + 1 : j]) = prefix[j] - prefix[i]
 *                                    prefix[i] - prefix[j]
 */
