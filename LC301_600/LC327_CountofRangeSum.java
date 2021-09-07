package LC301_600;
import java.util.*;
public class LC327_CountofRangeSum {
    /**
     * Given an integer array nums and two integers lower and upper, return the number of range sums that lie in
     * [lower, upper] inclusive.
     *
     * Range sum S(i, j) is defined as the sum of the elements in nums between indices i and j inclusive, where i <= j.
     *
     * Input: nums = [-2,5,-1], lower = -2, upper = 2
     * Output: 3
     *
     * Constraints:
     *
     * 1 <= nums.length <= 10^5
     * -2^31 <= nums[i] <= 2^31 - 1
     * -10^5 <= lower <= upper <= 10^5
     * The answer is guaranteed to fit in a 32-bit integer.
     * @param nums
     * @param lower
     * @param upper
     * @return
     */
    // time = O(n * logn * logn), space = O(n)
    private int res;
    public int countRangeSum(int[] nums, int lower, int upper) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        int n = nums.length;
        long[] presum = new long[n + 1];
        for (int i = 1; i <= n; i++) presum[i] = presum[i - 1] + nums[i - 1];

        helper(presum, 0, n, lower, upper);
        return res;
    }

    private void helper(long[] presum, int a, int b, int lower, int upper) {
        if (a >= b) return;

        int mid = a + (b - a) / 2;
        helper(presum, a, mid, lower, upper);
        helper(presum, mid + 1, b, lower, upper);

        for (int i = a; i <= mid; i++) { // 此时返回来的两半数组已经排过序了， 递归下去的时候是没排过序的
            int x = upperBound(presum, mid + 1, b, presum[i] + lower);
            int y = lowerBound(presum, mid + 1, b, presum[i] + upper);
            res += y - x + 1;
        }
        long[] temp = new long[b - a + 1];
        int i = a, j = mid + 1, p = 0;
        while (i <= mid && j <= b) {
            if (presum[i] < presum[j]) temp[p++] = presum[i++];
            else temp[p++] = presum[j++];
        }
        while (i <= mid) temp[p++] = presum[i++];
        while (j <= b) temp[p++] = presum[j++];

        for (i = 0; i < temp.length; i++) {
            presum[i + a] = temp[i];
        }
    }

    private int upperBound(long[] presum, int left, int right, long target) {
        while (left < right) {
            int mid = left + (right - left) / 2;
            if (presum[mid] < target) left = mid + 1;
            else right = mid;
        }
        return presum[left] >= target ? left : left + 1;
    }

    private int lowerBound(long[] presum, int left, int right, long target) {
        while (left < right) {
            int mid = right - (right - left) / 2;
            if (presum[mid] <= target) left = mid;
            else right = mid - 1;
        }
        return presum[left] <= target ? left : left - 1;
    }
}
/**
 * 凡是涉及区间和的题目，90%的情况就是把区间和转化为两个前缀和之差，几乎是一个必然的想法！
 * nums:      x x x x x || x x x x x
 *                i          j
 * presum:  0 z z z z z z
 * sum[i:j] = presum[j] - presum[i-1]
 * 最大好处是时间复杂度很大提高，否则要loop一遍
 * lower <= presum[j] - presum[i-1] <= upper
 * i => j? s.t. presum[j] >= presum[i-1]+lower => count of larger numbers after self
 *              presum[j] <= presum[i-1]+upper => count of smaller numbers after self
 *
 * 求range => 求pair
 * A: [x x x x x || x x x x x]
 *         i          j
 * B: [x x x x x]  C: [x x x x x]
 */
