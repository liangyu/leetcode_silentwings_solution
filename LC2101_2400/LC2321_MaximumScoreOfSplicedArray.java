package LC2101_2400;

public class LC2321_MaximumScoreOfSplicedArray {
    /**
     * You are given two 0-indexed integer arrays nums1 and nums2, both of length n.
     *
     * You can choose two integers left and right where 0 <= left <= right < n and swap the subarray nums1[left...right]
     * with the subarray nums2[left...right].
     *
     * For example, if nums1 = [1,2,3,4,5] and nums2 = [11,12,13,14,15] and you choose left = 1 and right = 2, nums1
     * becomes [1,12,13,4,5] and nums2 becomes [11,2,3,14,15].
     * You may choose to apply the mentioned operation once or not do anything.
     *
     * The score of the arrays is the maximum of sum(nums1) and sum(nums2), where sum(arr) is the sum of all the
     * elements in the array arr.
     *
     * Return the maximum possible score.
     *
     * A subarray is a contiguous sequence of elements within an array. arr[left...right] denotes the subarray that
     * contains the elements of nums between indices left and right (inclusive).
     *
     * Input: nums1 = [60,60,60], nums2 = [10,90,10]
     * Output: 210
     *
     * Input: nums1 = [20,40,20,70,30], nums2 = [50,20,50,40,20]
     * Output: 220
     *
     * Input: nums1 = [7,11,13], nums2 = [1,1,1]
     * Output: 31
     *
     * Constraints:
     *
     * n == nums1.length == nums2.length
     * 1 <= n <= 10^5
     * 1 <= nums1[i], nums2[i] <= 10^4
     * @param nums1
     * @param nums2
     * @return
     */
    // S1
    // time = O(n), space = O(1)
    public int maximumsSplicedArray(int[] nums1, int[] nums2) {
        return Math.max(helper(nums1, nums2), helper(nums2, nums1));
    }

    private int helper(int[] a, int[] b) {
        int sum = 0;
        for (int x : a) sum += x;

        int diff = 0, delta = 0, n = a.length;
        for (int i = 0; i < n; i++) {
            diff = Math.max(diff, 0) + (b[i] - a[i]);
            delta = Math.max(delta, diff);
        }
        return sum + delta;
    }

    // S2
    // time = O(n), space = O(n)
    public int maximumsSplicedArray2(int[] nums1, int[] nums2) {
        return Math.max(solve(nums1, nums2), solve(nums2, nums1));
    }

    private int solve(int[] nums1, int[] nums2) {
        int n = nums1.length, sum = 0;
        int[] gain = new int[n];
        for (int i = 0; i < n; i++) {
            gain[i] = nums2[i] - nums1[i];
            sum += nums1[i];
        }

        int curMaxSum = 0, res = 0;
        for (int i = 0; i < n; i++) {
            curMaxSum = Math.max(curMaxSum + gain[i], gain[i]);
            res = Math.max(res, curMaxSum);
        }
        return sum + res;
    }
}
/**
 * 让变化的值最大 Bi - Ai
 * Ci = Bi - Ai
 * sum + Cl + Cl+1 + ... + Cr => C数组的区间和 => 最大连续子数组和
 * 希望增量越大越好
 * gain[i] = nums2[i] - nums1[i]
 * nums1: x x x x x x
 * nums2: x x x x x x
 * gain:  x [x x x] x x => maximum subarray  -> kadane algorithm
 * 希望增量越大越好
 * dp[i]: the maximum subarray sum ending at i
 * x [x x i] x x
 * dp[i] = max{dp[i-1]+nums[i], nums[i]}
 * if (dp[i-1] < 0) dp[i] = nums[i];
 * else dp[i] = dp[i-1] + nums[i]
 */