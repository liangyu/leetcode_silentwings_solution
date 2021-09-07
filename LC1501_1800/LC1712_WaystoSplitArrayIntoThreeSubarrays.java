package LC1501_1800;

public class LC1712_WaystoSplitArrayIntoThreeSubarrays {
    /**
     * A split of an integer array is good if:
     *
     * The array is split into three non-empty contiguous subarrays - named left, mid, right respectively from left to
     * right.
     * The sum of the elements in left is less than or equal to the sum of the elements in mid, and the sum of the
     * elements in mid is less than or equal to the sum of the elements in right.
     * Given nums, an array of non-negative integers, return the number of good ways to split nums. As the number may be
     * too large, return it modulo 10^9 + 7.
     *
     * Input: nums = [1,1,1]
     * Output: 1
     *
     * Constraints:
     *
     * 3 <= nums.length <= 10^5
     * 0 <= nums[i] <= 10^4
     * @param nums
     * @return
     */
    // time = O(n), space = O(n)
    public int waysToSplit(int[] nums) {
        // corner case
        if (nums == null || nums.length == 0) return 0;

        long M = (long)(1e9 + 7);
        int n = nums.length;
        long[] presum = new long[n];
        presum[0] = nums[0];
        for (int i = 1; i < n; i++) presum[i] = presum[i - 1] + nums[i];

        int j = 0, k = 0;
        long res = 0;
        for (int i = 0; i < n; i++) {
            while (j <= i || j < n && presum[j] - presum[i] < presum[i]) j++; // j <= i 是不可以的！继续向后找。
            if (j == n) break;
            while (k + 1 < n - 1 && presum[k + 1] * 2 <= presum[i] + presum[n - 1]) k++;
            if (k < j) continue;
            res += k - j + 1;
            res %= M;
        }
        return (int) res;
    }
}
/**
 * 先遍历第一个区间的长度
 * O(nlogn) -> logn -> BS
 * [x x x] {[x x x x] x x}[x x x x]
 *      i         j     k
 * sum[i+1:j] >= sum[0:i]
 * j 是第二个区间的下限
 * res += k - j + 1
 * 如何比较高效的求k
 * sum[i+1:k] <= sum[k+1:n-1]  找到最大的k
 * 区间和用前缀数组来得到
 * presum[k] - presum[i] <= presum[n - 1] - presum[k]
 * presum[k] <= 0.5 * (presum[i] + presum[n - 1]) => constant
 * presum是单调增的 => 二分法
 */